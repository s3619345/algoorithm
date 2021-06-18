package com.sense.newots.object.job;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableMap;
import com.sense.newots.amq.producer.queue.ProducerService;
import com.sense.newots.commonentity.CronEntity;
import com.sense.newots.object.GetBeanUtil;
import com.sense.newots.object.entity.RosterBatchInfo;
import com.sense.newots.object.service.RosterBatchService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.jms.Destination;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * Created by senseinfo on 2018/9/28.
 */
@Slf4j
public class RedisDayClearJob implements Job{
    private static Destination apiQueue =(Destination)GetBeanUtil.getBean("apiQueue");
    private RosterBatchService batchDao = GetBeanUtil.getBean(RosterBatchService.class);
    private ProducerService producerService = GetBeanUtil.getBean(ProducerService.class);
    public RedisDayClearJob() {

    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        long currentTiem = System.currentTimeMillis();
        log.info("#@## 开始停止重播 " + (new Timestamp(currentTiem)).toString());
        List<CronEntity> list = JobManager.getFixTimeJobs();
        log.info("#@## JobManager size :"+list.size());
        for (int i = 0; i < list.size(); i++) {

            String batchId = list.get(i).getBatchId();
            String jobName = list.get(i).getJobName();
            String[] jobList = jobName.split(":");
            String name = jobList[0];
            if (name.equals("定时重拨")) {

                JobManager.removeJob(jobName);
                RosterBatchInfo myBatchInfo = batchDao.getRosterBatch(batchId);

                String roundList = jobList[2];
                String[] rount = roundList.split("-");
                Integer rountInt = Integer.parseInt(rount[1]);
                if (rountInt.equals(myBatchInfo.getRepeatCount())) {
                    myBatchInfo.setCurrentFinishedRound(myBatchInfo.getRepeatCount());
                    batchDao.updateRosterBatchInfo(myBatchInfo);

                    Map postMap  = ImmutableMap.<String, String>builder()
                            .put("type","batch").put("batchId",myBatchInfo.getBatchId())
                            .build();
                    String jsonStr = JSONObject.toJSONString(postMap);
                    producerService.sendMessage(apiQueue, jsonStr);
                    log.info("推送api---"+jsonStr);

//                    BatchReq var20 = new BatchReq();
//                    var20.setType("batch");-----------
//                    var20.setBatchId(myBatchInfo.getBatchId());
//                    BatchUtil.postBatchResult(var20);
                }
            }

        }
    }
}
