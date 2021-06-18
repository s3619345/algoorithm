package com.sense.newots.object.controller;

import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import com.sense.newots.commonentity.BaseResource;
import com.sense.newots.commonentity.PageRequest;
import com.sense.newots.object.entity.ActivityInfoHistory;
import com.sense.newots.object.service.ActivityInfoHistoryService;
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
 @desc 历史活动模块
 @date 2021-05-28 10:48:07
 @author szz
 */
@RestController
@Slf4j
@RequestMapping("/activityHistory")
public class ActivityHistoryController extends BaseResource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    static Gson gson = new Gson();
    @Autowired
    private ActivityInfoHistoryService activityDao;

    @RequestMapping("list")
    @Produces({"application/json"})
    public String getActivityInfoHistorys(@RequestBody PageRequest request) {
        ResponseUtil responseUtil = new ResponseUtil();
        try {
            int e = request.getStartPage() - 1;
            PageHelper.startPage(e, request.getPageNum());
            List lists = this.activityDao.getTActivityInfoHistorys(
                    request.getDomain(), request.getActivityName().trim());
            int count = this.activityDao.getTActivityInfoHistoryNum(request.getDomain());
            responseUtil = setResponseUtil(1, "getActivityInfoHistory Suc",
                    super.getMergeSumAndList(lists == null ? new ArrayList() : lists, count));
        } catch (Exception e) {
            responseUtil = setResponseUtil(0, e.getMessage(), null);
            log.error("getActivityInfoHistorys fail!:{}", e);
        }
        return gson.toJson(responseUtil);
    }

    @RequestMapping("add")
    @Produces({"application/json"})
    public ResponseUtil addActivityInfoHistory(@RequestBody ActivityInfoHistory template) {

        ResponseUtil responseUtil = new ResponseUtil();
        try {
            int i = this.activityDao.createActivityInfoHistory(template);
            if (i > 0)
                responseUtil = setResponseUtil(1, "add ActivityInfoHistory Suc", null);
            else
                responseUtil = setResponseUtil(0, "add ActivityInfoHistory fail", null);
        } catch (Exception e) {
            responseUtil = setResponseUtil(0, e.getMessage(), null);
            log.error("createActivityInfoHistory fail!:{}", e);
        }
        return responseUtil;
    }

    @RequestMapping("update")
    @Produces({"application/json"})
    public ResponseUtil updateActivityInfoHistory(@RequestBody ActivityInfoHistory template) {
        ResponseUtil responseUtil = new ResponseUtil();
        try {
            boolean ret = this.activityDao.updateActivityInfoHistory(template);
            if (ret)
                responseUtil = setResponseUtil(1, "update Activity History Suc", null);
            else
                responseUtil = setResponseUtil(0, "update Activity History fail", null);
        } catch (Exception e) {
            responseUtil = setResponseUtil(0, e.getMessage(), null);
            log.error("updateActivityInfoHistory fail!:{}", e);
        }
        return responseUtil;
    }

    @RequestMapping("delete")
    @Produces({"application/json"})
    public ResponseUtil deleteActivityInfoHistory(@RequestBody ActivityInfoHistory template) {
        ResponseUtil responseUtil = new ResponseUtil();
        try {
            boolean ret = this.activityDao.deleteActivityInfoHistory(template);
            if (ret)
                responseUtil = setResponseUtil(1, "delete Activity History Suc", null);
            else
                responseUtil = setResponseUtil(0, "delete Activity History fail", null);
        } catch (Exception e) {
            responseUtil = setResponseUtil(0, e.getMessage(), null);
            log.error("deleteActivityInfoHistory fail!:{}", e);
        }
        return responseUtil;
    }

}

