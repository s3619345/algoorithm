package com.sense.newots.object.controller;

import com.google.gson.Gson;
import com.sense.newots.commonentity.BaseResource;
import com.sense.newots.commonentity.PageRequest;
import com.sense.newots.impl.metric.ActivityMetric;
import com.sense.newots.impl.metric.BatchMetric;
import com.sense.newots.impl.metric.MetricUtil;
import com.sense.newots.impl.metric.SmsMetric;
import com.sense.newots.object.job.AnswerClickJob;
import com.sense.newots.object.service.ActivityInfoService;
import com.sense.newots.object.service.OutboundPolicyInfoService;
import com.sense.newots.object.util.ResponseUtil;
import com.sense.newots.util.RedisUtil;
import com.sense.newots.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Produces;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 @desc ...
 @date 2021-06-03 16:26:18
 @author szz
 */
@RestController
@RequestMapping("/metric")
@Slf4j
public class OutboundMetricController extends BaseResource {
    static Gson gson = new Gson();
    @Autowired
    private OutboundPolicyInfoService outboundPolicyInfoDAO;
    @Autowired
    private ActivityInfoService activityDao;

    @RequestMapping("activitys")
    @Produces({"application/json"})
    public String getActivityMetrics(@RequestBody PageRequest request) {
        new ResponseUtil();

        ResponseUtil responseUtil;
        try {
            List<ActivityMetric> metrics = new ArrayList();
            if (StringUtil.isNotEmpty(request.getActivityName())) {
                for (Map.Entry<String, ActivityMetric> entry : MetricUtil.actMetrics.entrySet()) {
                    if (entry.getValue().getActivityName().indexOf(request.getActivityName()) > -1) {
                        ActivityMetric metric = MetricUtil.getActMetrics(entry.getValue().getActivityName(), request.getPrefix(), entry.getValue().getDomain());
                        metrics.add(metric);
                    }
                }
            } else {
                for (Map.Entry<String, ActivityMetric> entry : MetricUtil.actMetrics.entrySet()) {
                    if (StringUtil.isNotEmpty(request.getPrefix()) && entry.getValue().getPrefix().indexOf(request.getPrefix()) > -1) {
                        ActivityMetric metric = MetricUtil.getActMetrics(entry.getValue().getActivityName(), request.getPrefix(), entry.getValue().getDomain());
                        metrics.add(metric);
                    }
                    if (StringUtil.isEmpty(request.getPrefix())) {
                        ActivityMetric metric = MetricUtil.getActMetrics(entry.getValue().getActivityName(), request.getPrefix(), entry.getValue().getDomain());
                        metrics.add(metric);
                    }
                }
            }
            Collections.sort(metrics, new Comparator<ActivityMetric>() {
                @Override
                public int compare(ActivityMetric o1, ActivityMetric o2) {
                    if (o1.getCallrate() > o2.getCallrate()) {
                        return -1;
                    }
                    if (o1.getCallrate() == o2.getCallrate()) {
                        return 0;
                    }
                    return 1;
                }
            });
            int startIndex = (request.getStartPage() - 1) * request.getPageNum();
            int lastIndex = request.getStartPage() * request.getPageNum();
            int count1 = metrics.size();
            if (lastIndex >= count1) {
                lastIndex = count1;
            }
            List<ActivityMetric> submetrics = metrics.subList(startIndex, lastIndex);
            responseUtil = this.setResponseUtil(1, "getActivityInfo Suc", super.getMergeSumAndList(submetrics == null ? new ArrayList() : submetrics, count1));
        } catch (Exception var11) {
            responseUtil = this.setResponseUtil(0, var11.getMessage(), (Object) null);
            log.error("getActivityInfos fail!:{}", var11);
        }
        return gson.toJson(responseUtil);
    }

    @RequestMapping("batch")
    @Produces({"application/json"})
    public String getBatchDetail(@RequestBody PageRequest request) {

        ResponseUtil responseUtil = null;
        try {
            ArrayList<BatchMetric> metrics = new ArrayList();

            for (Map.Entry<String, BatchMetric> entry : MetricUtil.batchMetrics.entrySet()) {

                if (entry.getKey().concat(":").indexOf(":" + request.getActivityName() + ":") > -1) {
                    BatchMetric metric = entry.getValue();
                    String key = entry.getKey();
                    if (StringUtil.isEmpty(MetricUtil.batOutCallNum.get(key))) {
                        entry.getValue().setOutCallNum(0);
                    } else {
                        metric.setOutCallNum(MetricUtil.batOutCallNum.get(key).intValue());
                    }
                    metric.setDomain(entry.getValue().getDomain());
                    metric.setStatus(entry.getValue().getStatus());
                    metric.setAnswerCallNum((int) MetricUtil.batAnswerCallNum.get(key));
                    metric.setFail1Num((int) MetricUtil.fail1Num8.get(key));
                    metric.setFail2Num((int) MetricUtil.fail2Num6.get(key));
                    metric.setFail3Num((int) MetricUtil.fail3Num1_9.get(key));
                    metric.setFail4Num((int) MetricUtil.fail4Num11.get(key));
                    metric.setFail5Num((int) MetricUtil.fail5Num7.get(key));
                    metric.setFailOtherNum((int) MetricUtil.failOtherNum.get(key));
                    metrics.add(metric);
                }
            }
            Collections.sort(metrics, new Comparator<BatchMetric>() {
                @Override
                public int compare(BatchMetric o1, BatchMetric o2) {
                    if (Long.parseLong(o1.getBatchName()) > Long.parseLong(o2.getBatchName())) {
                        return 1;
                    }
                    if (Long.parseLong(o1.getBatchName()) == Long.parseLong(o2.getBatchName())) {
                        return 0;
                    }
                    return -1;
                }
            });

            if (StringUtil.isNotEmpty(metrics)) {
                String callAnswerStep = outboundPolicyInfoDAO.getCallAnswerStep(request.getActivityName());
                Map<String, String> map = RedisUtil.hgetAll(callAnswerStep);
                metrics.get(0).setIvrResultNum(map);
            }

            responseUtil = this.setResponseUtil(1, "getActivityInfo Suc", super.getMergeSumAndList(metrics == null ? new ArrayList() : metrics, metrics.size()));
        } catch (Exception var12) {
            responseUtil = this.setResponseUtil(0, var12.getMessage(), (Object) null);
            log.error("getActivityInfos fail!:{}", var12);
        }
        return gson.toJson(responseUtil);
    }

    @RequestMapping("bxbatch")
    @Produces({"application/json"})
    public String getBatchDetails(@RequestBody PageRequest request) {

        ResponseUtil responseUtil = null;
        try {

            SmsMetric metric = new SmsMetric();
            LocalDateTime today = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd 09:00:00");
            String todayStartTime = formatter.format(today);
            String todayEndTime = todayStartTime.replace("09:00:00", "23:59:59");
            String bid = request.getBid();
            if (StringUtil.isEmpty(bid)) {
                bid = "360QMBX";
            }
            //发送总数
            int count = activityDao.sendCount(bid, todayStartTime, todayEndTime);
            if (count != 0) {
                metric.setSendCount(count);
                //统计每十分钟的点击数
                List<Map<Object, Object>> hourClickList = activityDao.findClick(bid, todayStartTime, todayEndTime);
                //统计每十分钟的爬虫数
                List<Map<Object, Object>> reptileCountList = activityDao.findReptile(bid, todayStartTime, todayEndTime);
                metric = StringUtil.list(metric, hourClickList, reptileCountList);
                metric.setAnswerMap(AnswerClickJob.answerMap.get(bid));
                metric.setClickMap(AnswerClickJob.clickMap.get(bid));
            }
            responseUtil = this.setResponseUtil(1, "getBxDetail Suc", super.getMergeSumAndList(metric == null ? new SmsMetric() : metric, 1));
        } catch (Exception var12) {
            responseUtil = this.setResponseUtil(0, var12.getMessage(), (Object) null);
            log.error("getActivityInfos fail!:{}", var12);
        }

        return gson.toJson(responseUtil);
    }
}
