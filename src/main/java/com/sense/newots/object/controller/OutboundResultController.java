package com.sense.newots.object.controller;

import com.mzlion.easyokhttp.response.callback.CallbackAdaptor;
import com.mzlion.easyokhttp.response.handle.DataHandler;
import com.mzlion.easyokhttp.response.handle.StringDataHandler;
import com.sense.newots.httpClient.OkHttpClientUtils;
import com.sense.newots.impl.metric.MetricUtil;
import com.sense.newots.object.conf.InitConfig;
import com.sense.newots.object.service.RosterService;
import com.sense.newots.request.RosterResultW;
import com.sense.newots.util.RedisUtil;
import com.sense.newots.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

/**
 @desc ...
 @date 2021-06-02 09:39:11
 @author szz
 */
@RestController
@RequestMapping("/outbound")
@Slf4j
public class OutboundResultController {
    @Context
    UriInfo uriInfo;

    @Context
    Request request;
    @Autowired
    private RosterService rosterDao;

    @RequestMapping("callResult")
    @Produces({"application/json"})
    public String addCallResult(@RequestBody RosterResultW callresult) {
        log.info("##[" + callresult.getResultType() + "][" + callresult.getResult() + "] receive result " + callresult.toString());
        if (StringUtil.isEmpty(callresult)) {
            if (callresult.getRosterinfo_id() == null) {
                log.info(" fail rosterinfo callId : " + callresult.getCallId());
                return "fail";
            }
            callresult = rosterDao.findById(Integer.parseInt(callresult.getRosterinfo_id()));
        }

        if (callresult != null) {
            if (callresult.getResultType().equals("outboundEnd")) {
                MetricUtil.addCallResults(callresult);
            } else if (callresult.getResultType().equals("callend")) {
                MetricUtil.addCallResults(callresult);
            }
            RedisUtil.decr(callresult.getActivity_id(), callresult.getBatch_id());
            callresult.setRound(callresult.getRound() + 1);
            this.rosterDao.updateRosterCallResult(callresult);
        }
        try {
            OkHttpClientUtils.easyPostCallback(InitConfig.MONGODB_URL, callresult.getCallId(), callresult.getResult(), new CallbackAdaptor<String>() {
                @Override
                public DataHandler<String> getDataHandler() {
                    return StringDataHandler.create();
                }

                @Override
                public void onSuccess(String data) {
                    //data就是经过处理后的数据，直接在这里写自己的业务逻辑
                    log.info("请求成功--" + data);
                }
            });
        } catch (Exception e) {
            log.info("请求发生错误--" + e);
        }
        return "success";
    }

}
