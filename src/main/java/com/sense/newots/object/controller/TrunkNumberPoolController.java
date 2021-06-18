package com.sense.newots.object.controller;

import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import com.sense.newots.commonentity.BaseResource;
import com.sense.newots.commonentity.CheckRequest;
import com.sense.newots.commonentity.PageRequest;
import com.sense.newots.object.entity.TrunkNumberPool;
import com.sense.newots.object.service.TrunkNumberPoolService;
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
import java.util.Iterator;
import java.util.List;

/**
 @desc ...
 @date 2021-05-31 14:30:11
 @author szz
 */
@RestController
@RequestMapping("/trunkpool")
@Slf4j
public class TrunkNumberPoolController extends BaseResource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    @Autowired
    private TrunkNumberPoolService trunkNumberPoolDao;
    @Autowired
    private TrunkPoolCorrService trunkPoolCorrDao;
    static Gson gson = new Gson();

    @RequestMapping("list")
    @Produces({"application/json"})
    public String getTrunkNumberPools(@RequestBody PageRequest request) {
        new ResponseUtil();

        ResponseUtil responseUtil;
        try {
            int e = request.getStartPage() - 1;
            PageHelper.startPage(e, request.getPageNum());
            List lists = this.trunkNumberPoolDao.getTTrunkNumberPools(request.getDomain());
            int count = this.trunkNumberPoolDao.getTTrunkNumberPoolNum(request.getDomain());
            if (count > 0) {
                Iterator var7 = lists.iterator();

                while (var7.hasNext()) {
                    TrunkNumberPool pool = (TrunkNumberPool) var7.next();
                    int phonenum = this.trunkPoolCorrDao.getTTrunkPoolNum(request.getDomain(), pool.getName());
                    pool.setPhonenumCount(phonenum);
                }
            }

            responseUtil = this.setResponseUtil(1, "getTrunkNumber Suc", super.getMergeSumAndList(lists == null ? new ArrayList() : lists, count));
        } catch (Exception var9) {
            responseUtil = this.setResponseUtil(0, var9.getMessage(), (Object) null);
            log.error("getTrunkNumberPools fail!:{}", var9);
        }

        return gson.toJson(responseUtil);
    }

    @RequestMapping("add")
    @Produces({"application/json"})
    public ResponseUtil addTrunkPool(@RequestBody TrunkNumberPool trunkPool) {
        new ResponseUtil();

        ResponseUtil responseUtil;
        try {
            boolean e = this.trunkNumberPoolDao.createTrunkNumberPool(trunkPool);
            if (e) {
                responseUtil = this.setResponseUtil(1, "add trunkNumberPool Suc", (Object) null);
            } else {
                responseUtil = this.setResponseUtil(0, "add trunkNumberPool fail", (Object) null);
            }
        } catch (Exception var4) {
            responseUtil = this.setResponseUtil(0, var4.getMessage(), (Object) null);
            log.error("createTrunkNumberPool fail!:{}", var4);
        }

        return responseUtil;
    }

    @RequestMapping("update")
    @Produces({"application/json"})
    public ResponseUtil updateTrunkNumberPool(@RequestBody TrunkNumberPool pool) {
        new ResponseUtil();

        ResponseUtil responseUtil;
        try {
            boolean e = this.trunkNumberPoolDao.updateTrunkNumberPool(pool);
            if (e) {
                responseUtil = this.setResponseUtil(1, "update Trunk Number Pool Suc", (Object) null);
            } else {
                responseUtil = this.setResponseUtil(0, "update Trunk Number Pool fail", (Object) null);
            }
        } catch (Exception var4) {
            responseUtil = this.setResponseUtil(0, var4.getMessage(), (Object) null);
            log.error("updateTrunkNumberPool fail!:{}", var4);
        }

        return responseUtil;
    }

    @RequestMapping("delete")
    @Produces({"application/json"})
    public ResponseUtil deleteTrunkNumberPool(@RequestBody TrunkNumberPool pool) {
        new ResponseUtil();

        ResponseUtil responseUtil;
        try {
            boolean e = this.trunkNumberPoolDao.deleteTrunkNumberPool(pool.getId());
            if (e) {
                responseUtil = this.setResponseUtil(1, "delete Trunk Number Pool Suc", (Object) null);
            } else {
                responseUtil = this.setResponseUtil(0, "delete Trunk Number Pool fail", (Object) null);
            }
        } catch (Exception var4) {
            responseUtil = this.setResponseUtil(0, var4.getMessage(), (Object) null);
            log.error("deleteTrunkNumberPool fail!:{}", var4);
        }

        return responseUtil;
    }

    @RequestMapping("check")
    @Produces({"application/json"})
    public ResponseUtil checkTrunkName(@RequestBody CheckRequest number) {
        new ResponseUtil();

        ResponseUtil responseUtil;
        try {
            boolean e = this.trunkNumberPoolDao.checkPoolName(number.getName());
            responseUtil = this.setResponseUtil(1, e ? "true" : "false", (Object) null);
        } catch (Exception var4) {
            log.error("check trunk name fail!:{}", var4);
            responseUtil = this.setResponseUtil(0, var4.getMessage(), (Object) null);
        }

        return responseUtil;
    }
}
