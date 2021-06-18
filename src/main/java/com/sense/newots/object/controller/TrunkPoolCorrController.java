package com.sense.newots.object.controller;

import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import com.sense.newots.commonentity.BaseResource;
import com.sense.newots.commonentity.PageRequest;
import com.sense.newots.object.entity.TrunkPoolCorr;
import com.sense.newots.object.service.TrunkPoolCorrService;
import com.sense.newots.object.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

/**
 @desc ...
 @date 2021-06-16 13:37:32
 @author szz
 */
@RestController
@Slf4j
@RequestMapping("/trunkcorr")
public class TrunkPoolCorrController extends BaseResource {
    @Context
    UriInfo uriInfo;

    @Context
    Request request;
    static Gson gson = new Gson();
    @Autowired
    private TrunkPoolCorrService TrunkPoolCorrDao;

    @RequestMapping("list")
    @Produces({"application/json"})
    public String getTrunkPoolCorrs(@RequestBody PageRequest request) {
        ResponseUtil responseUtil = new ResponseUtil();
        try {

            int e = request.getStartPage() - 1;
            PageHelper.startPage(e, request.getPageNum());
            List lists = this.TrunkPoolCorrDao.getTTrunkPoolCorr(request.getDomain(), request.getPoolName());
            int count = this.TrunkPoolCorrDao.getTTrunkPoolNum(request.getDomain(), request.getPoolName());
            responseUtil = setResponseUtil(1, "getTrunkNumber Suc",
                    super.getMergeSumAndList(lists == null ? new ArrayList() : lists, count));
        } catch (Exception e) {
            responseUtil = setResponseUtil(0, e.getMessage(), null);
            log.error("getTrunkPoolCorrs fail!:{}", e);
        }
        return gson.toJson(responseUtil);
    }

    @RequestMapping("add")
    @Produces({"application/json"})
    public ResponseUtil addTrunkPool(@RequestBody TrunkPoolCorr trunkPool) {
        ResponseUtil responseUtil = new ResponseUtil();
        try {
            boolean ret = this.TrunkPoolCorrDao.createTrunkPoolCorr(trunkPool);
            if (ret)
                responseUtil = setResponseUtil(1, "add TrunkPoolCorr Suc", null);
            else
                responseUtil = setResponseUtil(0, "add TrunkPoolCorr fail", null);
        } catch (Exception e) {
            responseUtil = setResponseUtil(0, e.getMessage(), null);
            log.error("createTrunkPoolCorr fail!:{}", e);
        }
        return responseUtil;
    }

    @RequestMapping("update")
    @Produces({"application/json"})
    public ResponseUtil updateTrunkPoolCorr(@RequestBody TrunkPoolCorr pool) {
        ResponseUtil responseUtil = new ResponseUtil();
        try {
            boolean ret = this.TrunkPoolCorrDao.updateTrunkPoolCorr(pool);
            if (ret)
                responseUtil = setResponseUtil(1, "update Trunk Pool Corr Suc", null);
            else
                responseUtil = setResponseUtil(0, "update Trunk Pool Corr fail", null);
        } catch (Exception e) {
            responseUtil = setResponseUtil(0, e.getMessage(), null);
            log.error("updateTrunkPoolCorr fail!:{}", e);
        }
        return responseUtil;
    }

    @RequestMapping("delete")
    @Produces({"application/json"})
    public ResponseUtil deleteTrunkPoolCorr(@RequestBody TrunkPoolCorr pool) {
        ResponseUtil responseUtil = new ResponseUtil();
        try {
            boolean ret = this.TrunkPoolCorrDao.deleteTrunkPoolCorr(pool.getId());
            if (ret)
                responseUtil = setResponseUtil(1, "delete Trunk Pool Corr Suc", null);
            else
                responseUtil = setResponseUtil(0, "delete Trunk Pool Corr fail", null);
        } catch (Exception e) {
            responseUtil = setResponseUtil(0, e.getMessage(), null);
            log.error("deleteTrunkPoolCorr fail!:{}", e);
        }
        return responseUtil;
    }
}
