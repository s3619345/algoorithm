package com.sense.newots.object.job;

import com.sense.newots.impl.metric.ActivityMetric;
import com.sense.newots.impl.metric.MetricUtil;
import com.sense.newots.object.GetBeanUtil;
import com.sense.newots.object.entity.ActivityInfo;
import com.sense.newots.object.service.ActivityInfoService;
import com.sense.newots.object.util.TimeUtil;
import com.sense.newots.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * Created by senseinfo on 2019/3/12.
 */
@Slf4j
public class AnswerClickJob implements Job {

    public static Map<String, Map<String, Integer>> answerMap = new ConcurrentSkipListMap();
    public static Map<String, Map<String, Integer>> clickMap = new ConcurrentSkipListMap();


    private ActivityInfoService activityDao = GetBeanUtil.getBean(ActivityInfoService.class);

    public static void clear() {
        answerMap.clear();
        clickMap.clear();
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        LocalDateTime today = LocalDateTime.now();
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd 09:00:00");
        String clickStartTime = formatter1.format(today);
        String clickEndTime = clickStartTime.replace("09:00:00", "23:59:59");
        String nowTime = TimeUtil.getCurrentTimeStr().split(" ")[1].substring(0, 5);
        for (Map.Entry<String, ActivityMetric> entry : MetricUtil.actMetrics.entrySet()) {
            String name = entry.getValue().getActivityName();
            ActivityInfo af = activityDao.findActivityInfoByTemplate(name);
            String bid = af.getBid();
            if (StringUtil.isNotEmpty(bid)) {
                ActivityMetric metric = MetricUtil.getActMetrics(name, null, "qiancai.com");
                log.info(bid + "--answerCallNum size() :" + metric.getAnswerCallNum());
                if (StringUtil.isEmpty(answerMap.get(bid))) {
                    Map<String, Integer> map = new HashedMap();
                    map.put(nowTime, metric.getAnswerCallNum());
                    answerMap.put(bid, map);
                } else {
                    answerMap.get(bid).put(nowTime, metric.getAnswerCallNum());
                }

                int clickCount = activityDao.clickCount(bid, clickStartTime, clickEndTime);
                log.info(bid + "--click size() :" + clickCount);
                if (StringUtil.isEmpty(clickMap.get(bid))) {
                    Map<String, Integer> map = new HashedMap();
                    map.put(nowTime, clickCount);
                    clickMap.put(bid, map);
                } else {
                    clickMap.get(bid).put(nowTime, clickCount);
                }
            }

        }



        /*List<RosterBatchInfo> batchInfos = batchDao.findByName(todayStartTime,todayEndTime);
        //log.info("##### 查出来的批次信息--"+batchInfos);
        //String nowTime = TimeUtil.getCurrentTimeStr().split(" ")[1].substring(0,5);
        int sucCount = 0;
        if (StringUtil.isNotEmpty(batchInfos)){
            for (int i = 0; i < batchInfos.size(); i++) {
                RosterBatchInfo batchInfo = batchInfos.get(i);
                String bname = batchInfo.getBatchId() + ":" + batchInfo.getActivityName();
                //sucCount += (int) MetricUtil.batAnswerCallNum.get(bname);
                int answerNum = (int) MetricUtil.batAnswerCallNum.get(bname);
                if (answerNum == 0){
                    sucCount += rosterDao.getSuccessNum(batchInfo.getBatchId());
                }else {
                    sucCount += answerNum;
                }
                //sucCount += 30;
                log.info(bname+" redis size() :"+sucCount);
            }
            answerMap.put(nowTime,sucCount);*/

        //}

    }


    public static void main(String[] args) {
        String nowTime = TimeUtil.getCurrentTimeStr().split(" ")[1].substring(0, 5);
        System.out.println(nowTime);
    }
}
