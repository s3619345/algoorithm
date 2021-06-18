package com.sense.newots.object.controller;

import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import com.sense.newots.commonentity.BaseResource;
import com.sense.newots.commonentity.CheckRequest;
import com.sense.newots.commonentity.CronEntity;
import com.sense.newots.commonentity.PageRequest;
import com.sense.newots.object.entity.ChangeBatchInfo;
import com.sense.newots.object.entity.RosterBatchInfo;
import com.sense.newots.object.job.JobManager;
import com.sense.newots.object.service.RosterBatchService;
import com.sense.newots.object.util.ResponseUtil;
import com.sense.newots.object.util.TimeUtil;
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
 @date 2021-06-15 15:55:23
 @author szz
 */
@RestController
@RequestMapping("/rosterBatch")
@Slf4j
public class RosterBatchController extends BaseResource {
    @Context
    UriInfo uriInfo;

    @Context
    Request request;
    static Gson gson = new Gson();
    @Autowired
    private RosterBatchService rosterBatchDao;

    @RequestMapping("list")
    @Produces({"application/json"})
    public String getRosterBatchs(@RequestBody PageRequest request) {
        ResponseUtil responseUtil = new ResponseUtil();
        try {
            int e = request.getStartPage() - 1;
            PageHelper.startPage(e, request.getPageNum());
            List<RosterBatchInfo> lists = this.rosterBatchDao.getTRosterBatchInfos(request.getDomain(), request.getTemplateName());
            int count = this.rosterBatchDao.getTRosterBatchInfoNum(request.getDomain(), request.getTemplateName());
            responseUtil = setResponseUtil(1, "getRosterBatch Suc", super.getMergeSumAndList(lists == null ? new ArrayList() : lists, count));
        } catch (Exception e) {
            responseUtil = setResponseUtil(0, e.getMessage(), null);
            log.error("getRosterBatchs fail!:{}", e);
        }
        return gson.toJson(responseUtil);
    }

    @RequestMapping("listUncall")
    @Produces({"application/json"})
    public String getUncallRosterBatchs(@RequestBody PageRequest request) {
        ResponseUtil responseUtil = new ResponseUtil();
        try {
            int e = request.getStartPage() - 1;
            PageHelper.startPage(e, request.getPageNum());
            List lists = this.rosterBatchDao.getUnCallRosterBatchInfos(request.getDomain(), request.getTemplateName(), TimeUtil.getCurrentDateStr());
            int count = this.rosterBatchDao.getUncallRosterBatchInfoNum(request.getDomain(),
                    request.getTemplateName(), TimeUtil.getCurrentDateStr());
            responseUtil = setResponseUtil(1, "getRosterBatch Suc", super.getMergeSumAndList(lists == null ? new ArrayList() : lists, count));
        } catch (Exception e) {
            responseUtil = setResponseUtil(0, e.getMessage(), null);
            log.error("getRosterBatchs fail!:{}", e);
        }
        return gson.toJson(responseUtil);
    }


    @RequestMapping("check")
    @Produces({"application/json"})
    public ResponseUtil checkBatchName(@RequestBody CheckRequest number) {
        ResponseUtil responseUtil = new ResponseUtil();
        try {
            boolean ret = this.rosterBatchDao.checkName(number.getName(), number.getDomain());
            responseUtil = setResponseUtil(1, ret ? "true" : "false", null);
        } catch (Exception e) {
            log.error("check trunk name fail!:{}", e);
            responseUtil = setResponseUtil(0, e.getMessage(), null);
        }
        return responseUtil;
    }

    @RequestMapping("update")
    @Produces({"application/json"})
    public ResponseUtil updateBatch(@RequestBody ChangeBatchInfo batchInfo) {
        ResponseUtil responseUtil = new ResponseUtil();
        try {
            int ret = this.rosterBatchDao.updateRosterBatchIdo(batchInfo);
            responseUtil = setResponseUtil(1, ret == 1 ? "true" : "false", null);
        } catch (Exception e) {
            log.error("check trunk name fail!:{}", e);
            responseUtil = setResponseUtil(0, e.getMessage(), null);
        }
        return responseUtil;
    }

    @RequestMapping("delete")
    @Produces({"application/json"})
    public ResponseUtil deleteBatch(@RequestBody RosterBatchInfo batchInfo) {
        ResponseUtil responseUtil = new ResponseUtil();
        try {
            boolean ret = this.rosterBatchDao.deleteRosterBatchInfo(batchInfo.getId());
            responseUtil = setResponseUtil(1, ret ? "true" : "false", null);
        } catch (Exception e) {
            log.error("check trunk name fail!:{}", e);
            responseUtil = setResponseUtil(0, e.getMessage(), null);
        }
        return responseUtil;
    }

    @RequestMapping("stop")
    @Produces({"application/json"})
    public ResponseUtil stopBatch() {
        ResponseUtil responseUtil = new ResponseUtil();
        try {
            ChangeBatchInfo batchInfo = new ChangeBatchInfo();
            int ret = this.rosterBatchDao.updateRosterBatchIdo(batchInfo);
            responseUtil = setResponseUtil(1, ret > 0 ? "true" : "false", null);
        } catch (Exception e) {
            log.error("check trunk name fail!:{}", e);
            responseUtil = setResponseUtil(0, e.getMessage(), null);
        }
        return responseUtil;
    }

    @RequestMapping("pause")
    @Produces({"application/json"})
    public ResponseUtil pauseBatch(@RequestBody RosterBatchInfo batchInfo) {
        ResponseUtil responseUtil = new ResponseUtil();
        try {
            boolean ret = this.rosterBatchDao.deleteRosterBatchInfo(batchInfo.getId());
            responseUtil = setResponseUtil(1, ret ? "true" : "false", null);
        } catch (Exception e) {
            log.error("check trunk name fail!:{}", e);
            responseUtil = setResponseUtil(0, e.getMessage(), null);
        }
        return responseUtil;
    }

    @RequestMapping("removeBatchReCall")
    @Produces({"application/json"})
    public ResponseUtil removeBatchReCall(@RequestBody CronEntity cronEntity) {
        ResponseUtil responseUtil = new ResponseUtil();
        try {
            JobManager.removeJob(cronEntity.getJobName(), JobManager.JOB_GROUP_NAME, cronEntity.getGroupName(), JobManager.TRIGGER_GROUP_NAME);
            responseUtil = setResponseUtil(1, "true", null);
        } catch (Exception e) {
            log.error("check trunk name fail!:{}", e);
            responseUtil = setResponseUtil(0, e.getMessage(), null);
        }
        return responseUtil;
    }

    @RequestMapping("resume")
    @Produces({"application/json"})
    public ResponseUtil resumeBatch(@RequestBody RosterBatchInfo batchInfo) {
        ResponseUtil responseUtil = new ResponseUtil();
        try {

            //batchInfo.getPlanCallTime < ;

            boolean ret = this.rosterBatchDao.deleteRosterBatchInfo(batchInfo.getId());
            responseUtil = setResponseUtil(1, ret ? "true" : "false", null);
        } catch (Exception e) {
            log.error("check trunk name fail!:{}", e);
            responseUtil = setResponseUtil(0, e.getMessage(), null);
        }
        return responseUtil;
    }
}
