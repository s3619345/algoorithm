package com.sense.newots.object.service.impl;

import com.google.gson.Gson;
import com.sense.newots.impl.metric.MetricUtil;
import com.sense.newots.object.dao.OutboundPolicyInfoMapper;
import com.sense.newots.object.entity.OutboundPolicyInfo;
import com.sense.newots.object.service.OutboundPolicyInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 @desc ...
 @date 2021-05-20 17:23:21
 @author szz
 */
@Service
@Slf4j
public class OutboundPolicyServiceImpl implements OutboundPolicyInfoService {
    @Autowired
    private OutboundPolicyInfoMapper outboundPolicyInfoMapper;
    static Gson gson = new Gson();

    @Override
    public OutboundPolicyInfo getTOutboundPolicyInfos(String domain, String policyName)  throws DataAccessException{
        log.info("## getOutboundPolicyInfos [" + policyName + "]");
        OutboundPolicyInfo tOutboundPolicyInfos = null;
        try {
            tOutboundPolicyInfos = outboundPolicyInfoMapper.getTOutboundPolicyInfos(domain, policyName);
        } catch (Exception e) {
            log.error("Exception:{}", e);
            return null;
        }
        return tOutboundPolicyInfos;
    }

    @Override
    public List<OutboundPolicyInfo> getTOutboundPolicyInfo(String domain) {
        log.info("## getOutboundPolicyInfos [" + domain + "]");
        List<OutboundPolicyInfo> list = outboundPolicyInfoMapper.getTOutboundPolicyInfo(domain);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list;
    }

    @Override
    public int getTOutboundPolicyInfoNum(String domain) {
        log.info("## getTOutboundPolicyInfoNum [" + domain + "]");
        return outboundPolicyInfoMapper.getTOutboundPolicyInfoNum(domain);
    }

    @Override
    public List<OutboundPolicyInfo> getTOutboundPolicyInfosList(String domain, String policyName) {
        log.info("## getOutboundPolicyInfos [" + policyName + "] ");
        List<OutboundPolicyInfo> list = outboundPolicyInfoMapper.getTOutboundPolicyInfosList(domain, policyName);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list;
    }

    @Override
    public boolean createOutboundPolicyInfo(OutboundPolicyInfo template) throws DataAccessException {
        log.info("## OutboundPolicyInfo create " + gson.toJson(template));
        try {
            outboundPolicyInfoMapper.createOutboundPolicyInfo(template);
        } catch (Exception e) {
            log.error("Exception:{}", e);
            return false;
        }
        return true;
    }

    @Override
    public boolean updateOutboundPolicyInfo(OutboundPolicyInfo template) throws DataAccessException {
        log.info("## OutboundPolicyInfo update " + gson.toJson(template));
        try {
            outboundPolicyInfoMapper.updateOutboundPolicyInfo(template);
        } catch (Exception e) {
            log.error("Exception:{}", e);
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteOutboundPolicyInfo(OutboundPolicyInfo template) throws DataAccessException {
        log.info("## OutboundPolicyInfo delete " + gson.toJson(template));
        try {
            outboundPolicyInfoMapper.deleteOutboundPolicyInfo(template);
        } catch (Exception e) {
            log.error("Exception:{}", e);
            return false;
        }
        return true;
    }

    @Override
    public boolean checkName(String name, String domain) {
        log.info("## getOutboundPolicyInfos [" + name + "][" + domain + "]");
        int i = outboundPolicyInfoMapper.checkName(name, domain);
        if (i == 0L)
            return true;
        return false;
    }

    @Override
    public String getCallAnswerStep(String activityName) {

        try {
            return MetricUtil.callee.get(activityName, new Callable<String>() {
                @Override
                public String call() throws Exception {
                    List<OutboundPolicyInfo> list = outboundPolicyInfoMapper.getCallAnswerStep(activityName);
                     OutboundPolicyInfo policyInfo=list.get(0);
                    return policyInfo.getCallAnswerStep().split("R")[1];
                }
            });
        } catch (ExecutionException e) {
            log.info("getCallAnswerStep is error --"+e);
            return null;
        }
    }

}
