package com.sense.newots.object.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import com.sense.newots.commonentity.BaseResource;
import com.sense.newots.commonentity.CheckRequest;
import com.sense.newots.commonentity.PageRequest;
import com.sense.newots.impl.metric.ActivityMetric;
import com.sense.newots.impl.metric.MetricUtil;
import com.sense.newots.impl.util.ReqResponse;
import com.sense.newots.object.AutoCallThread;
import com.sense.newots.object.TaskContainer;
import com.sense.newots.object.ThreadPoolOntime;
import com.sense.newots.object.entity.*;
import com.sense.newots.object.service.*;
import com.sense.newots.object.util.ResponseUtil;
import com.sense.newots.object.util.TimeUtil;
import com.sense.newots.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import java.util.*;

/**
 @desc 活动模块
 @date 2021-05-20 11:11:12
 @author szz
 */
@RestController
@RequestMapping("/activity")
@Slf4j
public class ActivityInfoController extends BaseResource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    @Autowired
    private ActivityInfoService activityDao;
    @Autowired
    private OutboundPolicyInfoService outboundPolicyInfoDao;
    @Autowired
    private RosterBatchService rosterBatchDao;
    @Autowired
    private OutboundRecallPolicyService recallPolicyDao;
    @Autowired
    private TemplateOperationRecordService templateOperationRecordDAO;
    static Gson gson = new Gson();

    @RequestMapping("getActivityInfoList")
    @Produces({"application/json"})
    public List<ActivityInfo> getActivityInfoList() {
        return activityDao.selectActivityInfo();
    }

    @RequestMapping("list")
    @Produces({"application/json"})
    public String getActivityInfos(@RequestBody PageRequest request) {
        new ResponseUtil();
        ResponseUtil responseUtil;
        try {
            int e = request.getStartPage() - 1;
            PageHelper.startPage(e, request.getPageNum());
            List<ActivityInfo> lists = this.activityDao.getTActivityInfosList(request.getActivityStatus(), request.getPrefix(), request.getActivityName());
            int count = this.activityDao.getTActivityInfoNum(request.getDomain());
            if (lists != null) {
                Iterator var7 = lists.iterator();
                while (var7.hasNext()) {
                    ActivityInfo info = (ActivityInfo) var7.next();
                    PageHelper.startPage(0, 10);
                    List policys = this.recallPolicyDao.getTOutboundRecallPolicys(info.getDomain(), info.getName());
                    info.setRoundList(policys);
                }
            }
            responseUtil = this.setResponseUtil(1, "getActivityInfo Suc", super.getMergeSumAndList(lists == null ? new ArrayList() : lists, count));
        } catch (Exception var9) {
            responseUtil = this.setResponseUtil(0, var9.getMessage(), (Object) null);
            log.error("getActivityInfos fail!:{}", var9);
        }
        return gson.toJson(responseUtil);
    }

    @RequestMapping("monitorlist")
    @Produces({"application/json"})
    public ReqResponse montir(@RequestBody PageRequest request) {
        ReqResponse res = new ReqResponse();
        res.setCode(200);
        return res;
    }

    @RequestMapping("add")
    @Produces({"application/json"})
    public ResponseUtil addActivityInfo(@RequestBody ActivityInfo template) {
        new ResponseUtil();

        ResponseUtil responseUtil;
        try {
            PageHelper.startPage(0, 1000);
            List e = this.rosterBatchDao.getUnCallRosterBatchInfos(template.getDomain(), template.getRosterTemplateName(), TimeUtil.getCurrentDateStr());
            if (e != null) {
                template.setBatchNum(e.size());
                for (int i = 0; i < e.size(); i++) {
                    RosterBatchInfo round1 = JSON.parseObject(JSON.toJSONString(e.get(i)), RosterBatchInfo.class);
                    template.addRosterNum(round1.getRoterNum());
                    round1.setActivityName(round1.getActivityName());
                    this.rosterBatchDao.updateRosterBatchInfo(round1);
                }
            }

            boolean ret1 = this.activityDao.createActivityInfo(template);
            List rounds1 = template.getRoundList();
            for (int i = 0; i < rounds1.size(); i++) {
                OutboundRecallPolicy round = JSON.parseObject(JSON.toJSONString(rounds1.get(i)), OutboundRecallPolicy.class);
                round.setDomain(template.getDomain());
                round.setActivityName(template.getName());
                this.recallPolicyDao.createOutboundRecallPolicy(round);
            }
            if (ret1) {
                responseUtil = this.setResponseUtil(1, "add ActivityInfo Suc", (Object) null);
            } else {
                responseUtil = this.setResponseUtil(0, "add ActivityInfo fail", (Object) null);
            }
        } catch (Exception var8) {
            responseUtil = this.setResponseUtil(0, var8.getMessage(), (Object) null);
            log.error("createActivityInfo fail!:{}", var8);
        }

        return responseUtil;
    }

    @RequestMapping("update")
    @Produces({"application/json"})
    public ResponseUtil updateActivityInfo(@RequestBody ActivityInfo template) {
        new ResponseUtil();

        ResponseUtil responseUtil;
        try {

            ActivityInfo e = this.activityDao.findById(template.getId());
            if (e != null) {
                template.setStatus(e.getStatus());
                template.setRosterNum(e.getRosterNum());
                template.setBatchNum(e.getBatchNum());
                template.setCompleteBatchNum(e.getCompleteBatchNum());
            }

            String activityName = template.getName() + ":" + template.getDomain();
            ActivityMetric metric = (ActivityMetric) MetricUtil.actMetrics.get(activityName);
            if (StringUtil.isNotEmpty(metric)) {
                metric.setStatus(e.getStatus());
                metric.setMaxCall(template.getMaxCapacity());
                metric.setPrefix(template.getPrefix());
                metric.setTrunkGrp(template.getTrunkGrp());
            }

            boolean ret = this.activityDao.updateActivityInfo(template);
            if (ret) {
                responseUtil = this.setResponseUtil(1, "update Activity Suc", (Object) null);
            } else {
                responseUtil = this.setResponseUtil(0, "update Activity fail", (Object) null);
            }
            List rounds = template.getRoundList();
            this.recallPolicyDao.deleteOutboundRecallPolicy(template.getDomain(), template.getName());
            for (int i = 0; i < rounds.size(); i++) {
                OutboundRecallPolicy round = JSON.parseObject(JSON.toJSONString(rounds.get(i)), OutboundRecallPolicy.class);
                round.setDomain(template.getDomain());
                round.setActivityName(template.getName());
                this.recallPolicyDao.createOutboundRecallPolicy(round);
            }
        } catch (Exception var8) {
            responseUtil = this.setResponseUtil(0, var8.getMessage(), (Object) null);
            log.error("updateActivityInfo fail!:{}", var8);
        }
        Map map = new HashMap();
        map.put("userName", template.getUserName());
        map.put("templateName", template.getName());
        map.put("state", "编辑");
        map.put("maxCapacity", template.getMaxCapacity());
        map.put("trunkGrp", template.getTrunkGrp());
        map.put("prefix", template.getPrefix());

        map.put("isSMS", template.getIsSMS());
        this.RecordInfo(map);
        return responseUtil;
    }

    @RequestMapping("edit")
    @Produces({"application/json"})
    public ResponseUtil editActivityInfo(@RequestBody String template) {
        log.info("重播编辑模板入参: template-" + template);
        new ResponseUtil();
        ResponseUtil responseUtil;
        try {
            if (StringUtil.isNotEmpty(template)) {
                JSONObject object = JSONObject.fromObject(template);
                ActivityInfo activityInfo = this.activityDao.findActivityInfoByTemplate(object.getString("rosterTemplateName"));
                if (StringUtil.isNotEmpty(activityInfo)) {
                    String activityName = activityInfo.getName() + ":" + activityInfo.getDomain();
                    AutoCallThread aThread = (AutoCallThread) TaskContainer.autoCallActivityMap.get(activityName);
                    if (StringUtil.isNotEmpty(aThread)) {
                        if (aThread.getStatus() == 1) {
                            aThread.pauseTask();
                        }
                        if (StringUtil.isNotEmpty(object.getString("prefix"))) {
                            activityInfo.setPrefix(object.getString("prefix"));
                        }
                        if (StringUtil.isNotEmpty(object.getString("maxCapacity"))) {
                            activityInfo.setMaxCapacity(Integer.parseInt(object.getString("maxCapacity")));
                        }
                        if (StringUtil.isNotEmpty(object.getString("trunkGrp"))) {
                            activityInfo.setTrunkGrp(object.getString("trunkGrp"));
                        }
                        boolean success = activityDao.updateActivityInfo(activityInfo);
                        if (success) {
                            aThread.setActivityInfo(activityInfo);
                            aThread.resumeTask();
                        }
                    }
                }
            }
            responseUtil = this.setResponseUtil(1, "edit Activity Suc", (Object) null);
        } catch (Exception var8) {
            responseUtil = this.setResponseUtil(0, var8.getMessage(), (Object) null);
            log.error("exitActivityInfo fail!:{}", var8);
        }
        return responseUtil;
    }

    @RequestMapping("seats")
    @Produces({"application/json"})
    public ResponseUtil seatsActivityInfo(@RequestBody ActivityInfo template) {
        log.info("坐席修改----" + template.getMaxCapacity());
        new ResponseUtil();
        ResponseUtil responseUtil = null;
        try {
            ActivityInfo activityInfo = this.activityDao.findActivityInfoByTemplate(template.getRosterTemplateName());
            if (StringUtil.isNotEmpty(activityInfo)) {
                String activityName = activityInfo.getName() + ":" + activityInfo.getDomain();
                AutoCallThread aThread = (AutoCallThread) TaskContainer.autoCallActivityMap.get(activityName);
                ActivityMetric metric = (ActivityMetric) MetricUtil.actMetrics.get(activityName);
                if (StringUtil.isNotEmpty(metric)) {
                    metric.setMaxCall(template.getMaxCapacity());
                }
                if (StringUtil.isNotEmpty(aThread)) {
                    if (aThread.getStatus() == 1) {
                        aThread.pauseTask();
                        //activityInfo.setStatus(2);
                        if (StringUtil.isNotEmpty(metric)) {
                            //metric.setMaxCall(template.getMaxCapacity());
                            metric.setStatus(2);
                        }
                    }
                    activityInfo.setMaxCapacity(template.getMaxCapacity());
                    aThread.setActivityInfo(activityInfo);
                    aThread.resumeTask();
                    if (StringUtil.isNotEmpty(metric)) {
                        //metric.setMaxCall(template.getMaxCapacity());
                        metric.setStatus(1);
                    }
                    this.activityDao.updateActivityInfo(activityInfo);
                } else {
                    log.info(activityName + "模板未执行或已删除");
                }
                responseUtil = this.setResponseUtil(1, "update Activity Suc", (Object) null);
            } else {
                responseUtil = this.setResponseUtil(0, "还未创建该模板", (Object) null);
            }
        } catch (Exception var4) {
            responseUtil = this.setResponseUtil(0, var4.getMessage(), (Object) null);
            log.error("updateActivityInfo fail!:{}", var4);
        }
        return responseUtil;
    }

    @RequestMapping("delete")
    @Produces({"application/json"})
    public ResponseUtil deleteActivityInfo(@RequestBody ActivityInfo template) {
        new ResponseUtil();

        ResponseUtil responseUtil;
        try {
            boolean e = this.activityDao.deleteActivityInfo(template);
            if (e) {
                responseUtil = this.setResponseUtil(1, "delete Activity Suc", (Object) null);
            } else {
                responseUtil = this.setResponseUtil(0, "delete Activity fail", (Object) null);
            }
        } catch (Exception var4) {
            responseUtil = this.setResponseUtil(0, var4.getMessage(), (Object) null);
            log.error("deleteActivityInfo fail!:{}", var4);
        }

        return responseUtil;
    }

    //批量模板的删除
    @RequestMapping("deleteList")
    @Produces({"application/json"})
    public ResponseUtil deleteListActivity(@RequestBody String info) {
        System.out.println(info);
        JSONObject jsonObject = JSONObject.fromObject(info);
        JSONArray jsonArray = jsonObject.getJSONArray("ids");
        Map map = new HashMap();
        map.put("userName", jsonObject.get("userName"));
        map.put("state", "删除");
        new ResponseUtil();
        ResponseUtil responseUtil = null;

        for (int i = 0; i < jsonArray.size(); i++) {
            Integer id = (Integer) jsonArray.get(i);
            ActivityInfo template = this.activityDao.findById(id);
            try {
                String name = template.getName() + ":" + template.getDomain();
                MetricUtil.actMetrics.remove(name);
                boolean e = this.activityDao.deleteActivityInfoById(id);
                if (e) {
                    responseUtil = this.setResponseUtil(1, "delete Activity Suc", (Object) null);
                } else {
                    responseUtil = this.setResponseUtil(0, "delete Activity fail", (Object) null);
                }
            } catch (Exception var4) {
                responseUtil = this.setResponseUtil(0, var4.getMessage(), (Object) null);
                log.error("deleteActivityInfo fail!:{}", var4);
            }
            map.put("templateName", template.getName());
            this.RecordInfo(map);
        }
        return responseUtil;

    }

    @RequestMapping("check")
    @Produces({"application/json"})
    public ResponseUtil checkName(@RequestBody CheckRequest number) {
        new ResponseUtil();
        ResponseUtil responseUtil;
        try {
            boolean e = this.activityDao.checkName(number.getName(), number.getDomain());
            responseUtil = this.setResponseUtil(1, e ? "true" : "false", (Object) null);
        } catch (Exception var4) {
            log.error("check activity name fail!:{}", var4);
            responseUtil = this.setResponseUtil(0, var4.getMessage(), (Object) null);
        }
        return responseUtil;
    }

    @RequestMapping("start")
    @Produces({"application/json"})
    public ResponseUtil startActivity(@RequestBody RecordId id) {
        ActivityInfo template = this.activityDao.findById(id.getId());
        if (template == null) {
            ResponseUtil info1 = this.setResponseUtil(0, " Activity not exits", (Object) null);
            return info1;
        } else {
            template.setStatus(1);
            template.setBeginDatetime(TimeUtil.getCurrentTimeStr());
            template.setActivityExecuteTime(TimeUtil.getCurrentTimeStr());
            this.activityDao.updateActivityInfo(template);
            OutboundPolicyInfo info = this.outboundPolicyInfoDao.getTOutboundPolicyInfos(template.getDomain(), template.getPolicyName());
            ThreadPoolOntime.addThread(template, info);
            ResponseUtil responseUtil = this.setResponseUtil(1, "start Activity Suc", (Object) null);
            return responseUtil;
        }
    }

    //新建模板的批量 未执行-启用
    @RequestMapping("startList")
    @Produces({"application/json"})
    public ResponseUtil startListActivity(@RequestBody String info) {
        JSONObject jsonObject = JSONObject.fromObject(info);
        JSONArray jsonArray = jsonObject.getJSONArray("ids");
        Map map = new HashMap();
        map.put("userName", jsonObject.get("userName"));
        map.put("state", "新建模板启用");
        for (int i = 0; i < jsonArray.size(); i++) {
            Integer id = (Integer) jsonArray.get(i);
            ActivityInfo template = this.activityDao.findById(id);
            if (template == null) {
                ResponseUtil info1 = this.setResponseUtil(0, " Activity not exits", (Object) null);
                return info1;
            } else {
                template.setStatus(1);
                template.setBeginDatetime(TimeUtil.getCurrentTimeStr());
                template.setActivityExecuteTime(TimeUtil.getCurrentTimeStr());
                this.activityDao.updateActivityInfo(template);
                OutboundPolicyInfo info1 = this.outboundPolicyInfoDao.getTOutboundPolicyInfos(template.getDomain(), template.getPolicyName());
                ThreadPoolOntime.addThread(template, info1);
                String activityName = template.getName() + ":" + template.getDomain();
                ActivityMetric metric = (ActivityMetric) MetricUtil.actMetrics.get(activityName);
                if (StringUtil.isNotEmpty(metric)) {
                    metric.setStatus(1);
                }
            }
            map.put("templateName", template.getName());
            this.RecordInfo(map);
        }
        ResponseUtil responseUtil = this.setResponseUtil(1, "start Activity Suc", (Object) null);
        return responseUtil;
    }

    @RequestMapping("pause")
    @Produces({"application/json"})
    public ResponseUtil pauseActivity(@RequestBody RecordId id) {
        ActivityInfo template = this.activityDao.findById(id.getId());
        if (template == null) {
            ResponseUtil activityName1 = this.setResponseUtil(0, " Activity not exits", (Object) null);
            return activityName1;
        } else {
            String activityName = template.getName() + ":" + template.getDomain();
            AutoCallThread aThread = (AutoCallThread) TaskContainer.autoCallActivityMap.get(activityName);
            if (aThread != null) {
                aThread.setActivityInfo(template);
                aThread.pauseTask();
            }
            ResponseUtil responseUtil = this.setResponseUtil(1, "pause Activity Suc", (Object) null);
            return responseUtil;
        }
    }

    //批量模板的暂停
    @RequestMapping("pauseList")
    @Produces({"application/json"})
    public ResponseUtil pauseListActivity(@RequestBody String info) {
        JSONObject jsonObject = JSONObject.fromObject(info);
        JSONArray jsonArray = jsonObject.getJSONArray("ids");
        Map map = new HashMap();
        map.put("userName", jsonObject.get("userName"));
        map.put("state", "暂停");
        for (int i = 0; i < jsonArray.size(); i++) {
            Integer id = (Integer) jsonArray.get(i);
            ActivityInfo template = this.activityDao.findById(id);
            if (template == null) {
                ResponseUtil activityName1 = this.setResponseUtil(0, " Activity not exits", (Object) null);
                return activityName1;
            } else {
                String activityName = template.getName() + ":" + template.getDomain();
                ActivityMetric metric = (ActivityMetric) MetricUtil.actMetrics.get(activityName);
                if (StringUtil.isNotEmpty(metric)) {
                    metric.setStatus(2);
                }
                AutoCallThread aThread = (AutoCallThread) TaskContainer.autoCallActivityMap.get(activityName);
                if (aThread != null) {
                    aThread.setActivityInfo(template);
                    aThread.pauseTask();
                }
            }
            map.put("templateName", template.getName());
            this.RecordInfo(map);
        }
        ResponseUtil responseUtil = this.setResponseUtil(1, "pause Activity Suc", (Object) null);
        return responseUtil;
    }

    //批量模板的暂停启动
    @RequestMapping("resumeList")
    @Produces({"application/json"})
    public ResponseUtil resumeListActivity(@RequestBody String info) {
        JSONObject jsonObject = JSONObject.fromObject(info);
        JSONArray jsonArray = jsonObject.getJSONArray("ids");
        Map map = new HashMap();
        map.put("userName", jsonObject.get("userName"));
        map.put("state", "启用");
        ResponseUtil responseUtil1 = null;
        for (int i = 0; i < jsonArray.size(); i++) {
            Integer id = (Integer) jsonArray.get(i);
            ActivityInfo template = this.activityDao.findById(id);
            if (template == null) {
                ResponseUtil activityName1 = this.setResponseUtil(0, " Activity not exits", (Object) null);
                return activityName1;
            } else {
                String activityName = template.getName() + ":" + template.getDomain();
                AutoCallThread aThread = (AutoCallThread) TaskContainer.autoCallActivityMap.get(activityName);
                if (aThread != null) {
                    OutboundPolicyInfo responseUtil = this.outboundPolicyInfoDao.getTOutboundPolicyInfos(template.getDomain(), template.getPolicyName());
                    aThread.setActivityInfo(template);
                    aThread.setPolicyInfo(responseUtil);
                    aThread.resumeTask();
                    responseUtil1 = this.setResponseUtil(1, "resume Activity Suc", (Object) null);
                    ActivityMetric metric = (ActivityMetric) MetricUtil.actMetrics.get(activityName);
                    if (StringUtil.isNotEmpty(metric)) {
                        metric.setStatus(1);
                    }
                } else {
                    responseUtil1 = this.setResponseUtil(0, "resume Activity Error", (Object) null);
                }

            }
            map.put("templateName", template.getName());
            this.RecordInfo(map);
        }
        return responseUtil1;
    }

    @RequestMapping("stopResumeByBatchId")
    @Produces({"application/json"})
    public ResponseUtil stopByBatchId(@RequestBody RecordId batchId) {
        RosterBatchInfo batchInfo = this.rosterBatchDao.findById("" + batchId.getBatchId());
        if (batchInfo == null) {
            ResponseUtil activityName1 = this.setResponseUtil(0, " batchId not exits", (Object) null);
            return activityName1;
        } else {
            if (batchId.getStatus() != 1) {
                batchInfo.setStatus(1); //启用批次
                this.rosterBatchDao.updateRosterBatchInfo(batchInfo);
            } else {
                batchInfo.setStatus(4); //暂停批次
                this.rosterBatchDao.updateRosterBatchInfo(batchInfo);
            }
            ResponseUtil responseUtil = this.setResponseUtil(1, "stop batchId Suc", (Object) null);
            return responseUtil;
        }
    }

    @RequestMapping("stop")
    @Produces({"application/json"})
    public ResponseUtil stopActivity(@RequestBody RecordId id) {
        ActivityInfo template = this.activityDao.findById(id.getId());
        if (template == null) {
            ResponseUtil activityName1 = this.setResponseUtil(0, " Activity not exits", (Object) null);
            return activityName1;
        } else {
            template.setStatus(3);
            template.setEndDatetime(TimeUtil.getCurrentTimeStr());
            this.activityDao.updateActivityInfo(template);
            String activityName = template.getName() + ":" + template.getDomain();
            AutoCallThread aThread = (AutoCallThread) TaskContainer.autoCallActivityMap.get(activityName);
            if (aThread != null) {
                aThread.stopTask();
            }
            ResponseUtil responseUtil = this.setResponseUtil(1, "stop Activity Suc", (Object) null);
            return responseUtil;
        }
    }

    //批量模板停止
    @RequestMapping("stopList")
    @Produces({"application/json"})
    public ResponseUtil stopListActivity(@RequestBody String info) {
        JSONObject jsonObject = JSONObject.fromObject(info);
        JSONArray jsonArray = jsonObject.getJSONArray("ids");
        Map map = new HashMap();
        map.put("userName", jsonObject.get("userName"));
        map.put("state", "停止");
        for (int i = 0; i < jsonArray.size(); i++) {
            Integer id = (Integer) jsonArray.get(i);
            ActivityInfo template = this.activityDao.findById(id);
            if (template == null) {
                ResponseUtil activityName1 = this.setResponseUtil(0, " Activity not exits", (Object) null);
                return activityName1;
            } else {
                template.setStatus(3);
                template.setEndDatetime(TimeUtil.getCurrentTimeStr());
                this.activityDao.updateActivityInfo(template);
                String activityName = template.getName() + ":" + template.getDomain();
                ActivityMetric activityMetric = (ActivityMetric) MetricUtil.actMetrics.get(activityName);
                if (StringUtil.isNotEmpty(activityMetric)) {
                    activityMetric.setStatus(3);
                }
                AutoCallThread aThread = (AutoCallThread) TaskContainer.autoCallActivityMap.get(activityName);
                if (aThread != null) {
                    aThread.stopTask();
                }
            }
            map.put("templateName", template.getName());
            this.RecordInfo(map);
        }
        ResponseUtil responseUtil = this.setResponseUtil(1, "stop Activity Suc", (Object) null);
        return responseUtil;
    }

    /**
     * 模板操作记录表
     * @param map
     */
    public void RecordInfo(Map map) {
        TemplateOperationRecord record = new TemplateOperationRecord();
        try {
            record.setTemplate(map.get("templateName").toString());
            record.setUserName(map.get("userName").toString());
            record.setStatus(map.get("state").toString());

            if (map.get("state").equals("编辑")) {
                record.setPrefix(map.get("prefix") + "");
                record.setMaxCapacity(map.get("maxCapacity") + "");
                record.setTrunkGrp(map.get("trunkGrp") + "");
                record.setIsSMS(map.get("isSMS") + "");
            }
            boolean ret1 = this.templateOperationRecordDAO.createTemplateOperationRecordInfo(record);
            if (ret1) {
                log.info("addTemplateOperationRecordInfo success!:{}", record);
            } else {
                log.error("addTemplateOperationRecordInfo fail!:{}", record);
            }
        } catch (Exception var8) {
            log.error("addTemplateOperationRecordInfo fail!:{}", var8);
        }
    }
}
