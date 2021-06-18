package com.sense.newots.object.controller;

import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import com.sense.newots.commonentity.BaseResource;
import com.sense.newots.commonentity.CheckRequest;
import com.sense.newots.commonentity.PageRequest;
import com.sense.newots.impl.metric.MetricUtil;
import com.sense.newots.object.entity.OutboundPolicyInfo;
import com.sense.newots.object.service.OutboundPolicyInfoService;
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
 @date 2021-05-28 10:48:07
 @author szz
 */
@RestController
@Slf4j
@RequestMapping("/outboundPolicy")
public class OutboundPolicyInfoController extends BaseResource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    static Gson gson = new Gson();
    @Autowired
    private OutboundPolicyInfoService outboundPolicyInfoDao;

    @RequestMapping("list")
    @Produces({"application/json"})
    public String getOutboundPolicyInfos(@RequestBody PageRequest request) {
        new ResponseUtil();
        ResponseUtil responseUtil;
        try {
            int e = request.getStartPage() - 1;
            PageHelper.startPage(e, request.getPageNum());
            List lists = this.outboundPolicyInfoDao.getTOutboundPolicyInfo(request.getDomain());
            Iterator var6 = lists.iterator();
            while (var6.hasNext()) {
                OutboundPolicyInfo count = (OutboundPolicyInfo) var6.next();
                List list = (List) gson.fromJson(count.getTimeRangeStr(), MetricUtil.typeList);
                count.setTimeRange(list);
                List list3 = (List) gson.fromJson(count.getResProcessStr(), MetricUtil.typeList);
                count.setCallResultList(list3);
            }

            int var10 = this.outboundPolicyInfoDao.getTOutboundPolicyInfoNum(request.getDomain());
            responseUtil = this.setResponseUtil(1, "getOutboundPolicyInfo Suc", super.getMergeSumAndList(lists == null ? new ArrayList() : lists, var10));
        } catch (Exception var9) {
            responseUtil = this.setResponseUtil(0, var9.getMessage(), (Object) null);
            log.error("getOutboundPolicyInfos fail!:{}", var9);
        }
        return gson.toJson(responseUtil);
    }

    @RequestMapping("dimList")
    @Produces({"application/json"})
    public String dimOutboundPolicyInfos(@RequestBody PageRequest request) {
        new ResponseUtil();

        ResponseUtil responseUtil;
        try {
            int e = request.getStartPage() - 1;
            PageHelper.startPage(e, request.getPageNum());
            List lists = this.outboundPolicyInfoDao.getTOutboundPolicyInfosList(request.getDomain(), request.getPolicyName());
            Iterator var6 = lists.iterator();
            while (var6.hasNext()) {
                OutboundPolicyInfo count = (OutboundPolicyInfo) var6.next();
                List list = (List) gson.fromJson(count.getTimeRangeStr(), MetricUtil.typeList);
                count.setTimeRange(list);
                List list3 = (List) gson.fromJson(count.getResProcessStr(), MetricUtil.typeList);
                count.setCallResultList(list3);
            }

            int var10 = this.outboundPolicyInfoDao.getTOutboundPolicyInfoNum(request.getDomain());
            responseUtil = this.setResponseUtil(1, "getOutboundPolicyInfo Suc", super.getMergeSumAndList(lists == null ? new ArrayList() : lists, var10));
        } catch (Exception var9) {
            responseUtil = this.setResponseUtil(0, var9.getMessage(), (Object) null);
            log.error("getOutboundPolicyInfos fail!:{}", var9);
        }
        return gson.toJson(responseUtil);
    }
    @RequestMapping("add")
    @Produces({"application/json"})
    public ResponseUtil addOutboundPolicyInfo(@RequestBody OutboundPolicyInfo template) {
        new ResponseUtil();

        ResponseUtil responseUtil;
        try {
            template.setTimeRangeStr(gson.toJson(template.getTimeRange()));
            template.setResProcessStr(gson.toJson(template.getCallResultList()));
            boolean e = this.outboundPolicyInfoDao.createOutboundPolicyInfo(template);
            if(e) {
                responseUtil = this.setResponseUtil(1, "add OutboundPolicyInfo Suc", (Object)null);
            } else {
                responseUtil = this.setResponseUtil(0, "add OutboundPolicyInfo fail", (Object)null);
            }
        } catch (Exception var4) {
            responseUtil = this.setResponseUtil(0, var4.getMessage(), (Object)null);
            log.error(  "createOutboundPolicyInfo fail!:{}", var4);
        }

        return responseUtil;
    }
    @RequestMapping("update")
    @Produces({"application/json"})
    public ResponseUtil updateOutboundPolicyInfo(@RequestBody OutboundPolicyInfo template) {
        new ResponseUtil();

        ResponseUtil responseUtil;
        try {
            template.setTimeRangeStr(gson.toJson(template.getTimeRange()));
            template.setResProcessStr(gson.toJson(template.getCallResultList()));
            boolean e = this.outboundPolicyInfoDao.updateOutboundPolicyInfo(template);
            if(e) {
                responseUtil = this.setResponseUtil(1, "update Outbound Policy Suc", (Object)null);
            } else {
                responseUtil = this.setResponseUtil(0, "update Outbound Policy fail", (Object)null);
            }
        } catch (Exception var4) {
            responseUtil = this.setResponseUtil(0, var4.getMessage(), (Object)null);
            log.error( "updateOutboundPolicyInfo fail!:{}", var4);
        }
        return responseUtil;
    }
    @RequestMapping("delete")
    @Produces({"application/json"})
    public ResponseUtil deleteOutboundPolicyInfo(@RequestBody OutboundPolicyInfo template) {
        new ResponseUtil();

        ResponseUtil responseUtil;
        try {
            boolean e = this.outboundPolicyInfoDao.deleteOutboundPolicyInfo(template);
            if(e) {
                responseUtil = this.setResponseUtil(1, "delete Outbound Policy Suc", (Object)null);
            } else {
                responseUtil = this.setResponseUtil(0, "delete Outbound Policy fail", (Object)null);
            }
        } catch (Exception var4) {
            responseUtil = this.setResponseUtil(0, var4.getMessage(), (Object)null);
            log.error( "deleteOutboundPolicyInfo fail!:{}", var4);
        }

        return responseUtil;
    }
    @RequestMapping("check")
    @Produces({"application/json"})
    public ResponseUtil checkName(@RequestBody CheckRequest number) {
        new ResponseUtil();

        ResponseUtil responseUtil;
        try {
            boolean e = this.outboundPolicyInfoDao.checkName(number.getName(), number.getDomain());
            responseUtil = this.setResponseUtil(1, e?"true":"false", (Object)null);
        } catch (Exception var4) {
            log.error(  "check trunk name fail!:{}", var4);
            responseUtil = this.setResponseUtil(0, var4.getMessage(), (Object)null);
        }

        return responseUtil;
    }
}
