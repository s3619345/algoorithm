package com.sense.newots.object.controller;

import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import com.sense.newots.commonentity.BaseResource;
import com.sense.newots.object.entity.ConfigParam;
import com.sense.newots.object.service.ConfigParamService;
import com.sense.newots.object.util.ResponseUtil;
import com.sense.newots.request.ConfigParamRequest;
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
 @date 2021-05-31 11:08:51
 @author szz
 */
@RestController
@RequestMapping("/configParam")
@Slf4j
public class ConfigParamController extends BaseResource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    @Autowired
    private ConfigParamService configParamDao;
    static Gson gson = new Gson();

    @RequestMapping("list")
    @Produces({"application/json"})
    public String getConfigParmas(@RequestBody ConfigParamRequest request) {
        ResponseUtil responseUtil = new ResponseUtil();
        try {
            int e = request.getStartPage() - 1;
            PageHelper.startPage(e, request.getPageNum());
            List lists = this.configParamDao.getTConfigParams(request.getDomain(), request.getParamType());
            int count = this.configParamDao.getTConfigParamNum(request.getDomain(), request.getParamType());
            responseUtil = setResponseUtil(1, "getConfigParmas Suc",
                            super.getMergeSumAndList(lists == null ? new ArrayList() : lists, count));
        } catch (Exception e) {
            responseUtil = setResponseUtil(0, e.getMessage(), null);
        }
        return gson.toJson(responseUtil);
    }

    @RequestMapping("add")
    @Produces({"application/json"})
    public ResponseUtil addConfigParam(@RequestBody ConfigParam configParam) {
        ResponseUtil responseUtil = new ResponseUtil();
        try {
            if (configParam.getName() == null)
               throw new IllegalArgumentException("请输入名称");
            this.configParamDao.createConfigParam(configParam);
            responseUtil = setResponseUtil(1, "addConfigParam Suc", null);
        } catch (Exception e) {
            log.error("addConfigParam fail!:{}", e);
            responseUtil = setResponseUtil(0, e.getMessage(), null);
        }
        return responseUtil;
    }

    @RequestMapping("delete")
    @Produces({"application/json"})
    public ResponseUtil deleteConfigParam(@RequestBody ConfigParam configParam) {
        ResponseUtil responseUtil = new ResponseUtil();
        try {
            this.configParamDao.deleteConfigParam(configParam);
            responseUtil = setResponseUtil(1, "deleteConfigParam Suc", null);
        } catch (Exception e) {
            log.error("deleteConfigParam fail!:{}", e);
            responseUtil = setResponseUtil(0, e.getMessage(), null);
        }
        return responseUtil;
    }

    @RequestMapping("update")
    @Produces({"application/json"})
    public ResponseUtil updateConfigParam(@RequestBody ConfigParam configParam) {
        ResponseUtil responseUtil = new ResponseUtil();
        try {
            this.configParamDao.updateConfigParam(configParam);
            responseUtil = setResponseUtil(1, "updateConfigParam Suc", null);
        } catch (Exception e) {
            log.error("updateConfigParam fail!:{}", e);
            responseUtil = setResponseUtil(0, e.getMessage(), null);
        }
        return responseUtil;
    }

    @RequestMapping("check")
    @Produces({"application/json"})
    public ResponseUtil checkConfigParamName(@RequestBody ConfigParam configParam) {
        ResponseUtil responseUtil = new ResponseUtil();
        try {
            boolean ret = this.configParamDao.checkConfigParamName(configParam.getName());
            responseUtil = setResponseUtil(1, ret ? "true" : "false", null);
        } catch (Exception e) {
            log.error("checkConfigParamName fail!:{}", e);
            responseUtil = setResponseUtil(0, e.getMessage(), null);
        }
        return responseUtil;
    }
}
