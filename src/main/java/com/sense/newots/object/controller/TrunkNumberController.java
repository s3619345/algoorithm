package com.sense.newots.object.controller;

import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import com.sense.newots.commonentity.BaseResource;
import com.sense.newots.commonentity.CheckRequest;
import com.sense.newots.commonentity.PageRequest;
import com.sense.newots.object.entity.TrunkNumber;
import com.sense.newots.object.service.TrunkNumberService;
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
 @date 2021-05-31 15:32:16
 @author szz
 */
@RestController
@RequestMapping("/trunk")
@Slf4j
public class TrunkNumberController extends BaseResource {

    @Context
    UriInfo uriInfo;

    @Context
    Request request;
    @Autowired
    private TrunkNumberService trunkNumberDao;
    static Gson gson = new Gson();

    @RequestMapping("list")
    @Produces({"application/json"})
    public String getTrunkAniPools(@RequestBody PageRequest request) {
        ResponseUtil responseUtil = new ResponseUtil();
        try {
            int e = request.getStartPage() - 1;
            PageHelper.startPage(e, request.getPageNum());
            List lists = this.trunkNumberDao.getTTrunkNumbers(request.getDomain());
            int count = this.trunkNumberDao.getTTrunkNumberNum(request.getDomain());
            responseUtil = setResponseUtil(1, "getTrunkNumber Suc",
                    super.getMergeSumAndList(lists == null ? new ArrayList() : lists, count));
        } catch (Exception e) {
            responseUtil = setResponseUtil(0, e.getMessage(), null);
            log.error("getTrunkAniPools fail!:{}", e);
        }
        return gson.toJson(responseUtil);
    }

    @RequestMapping("add")
    @Produces({"application/json"})
    public ResponseUtil addAniPool(@RequestBody TrunkNumber trunkNumber) {
        ResponseUtil responseUtil = new ResponseUtil();
        try {
            boolean ret = this.trunkNumberDao.createTrunkNumber(trunkNumber);
            if (ret)
                responseUtil = setResponseUtil(1, "add trunkNumber Suc", null);
            else
                responseUtil = setResponseUtil(0, "add trunkNumber fail", null);
        } catch (Exception e) {
            responseUtil = setResponseUtil(0, e.getMessage(), null);
            log.error("createTrunkAniPool fail!:{}", e);
        }
        return responseUtil;
    }

    @RequestMapping("update")
    @Produces({"application/json"})
    public ResponseUtil updateTrunkAniPool(@RequestBody TrunkNumber number) {
        ResponseUtil responseUtil = new ResponseUtil();
        try {
            boolean ret = this.trunkNumberDao.updateTrunkNumber(number);
            if (ret)
                responseUtil = setResponseUtil(1, "update Trunk Number Suc", null);
            else
                responseUtil = setResponseUtil(0, "update Trunk Number fail", null);
        } catch (Exception e) {
            responseUtil = setResponseUtil(0, e.getMessage(), null);
            log.error("updateTrunkAniPool fail!:{}", e);
        }
        return responseUtil;
    }

    @RequestMapping("delete")
    @Produces({"application/json"})
    public ResponseUtil deleteTrunkAniPool(@RequestBody TrunkNumber number) {
        ResponseUtil responseUtil = new ResponseUtil();
        try {
            boolean ret = this.trunkNumberDao.deleteTrunkNumber(number.getId());
            if (ret)
                responseUtil = setResponseUtil(1, "delete Trunk Number Suc", null);
            else
                responseUtil = setResponseUtil(0, "delete Trunk Number fail", null);
        } catch (Exception e) {
            responseUtil = setResponseUtil(0, e.getMessage(), null);
            log.error("deleteTrunkAniPool fail!:{}", e);
        }
        return responseUtil;
    }

    @RequestMapping("check")
    @Produces({"application/json"})
    public ResponseUtil checkTrunkName(@RequestBody CheckRequest number) {
        ResponseUtil responseUtil = new ResponseUtil();
        try {
            boolean ret = this.trunkNumberDao.checkPoolName(number.getName());
            responseUtil = setResponseUtil(1, ret ? "true" : "false", null);
        } catch (Exception e) {
            log.error("check trunk name fail!:{}", e);
            responseUtil = setResponseUtil(0, e.getMessage(), null);
        }
        return responseUtil;
    }
}
