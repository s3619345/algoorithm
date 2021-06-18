package com.sense.newots.schedule;

import com.sense.newots.object.GetBeanUtil;
import com.sense.newots.object.entity.RosterBatchInfo;
import com.sense.newots.object.service.RosterBatchService;
import com.sense.newots.object.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by 845477519@qq.com on 2017/11/13 0013.
 */

//定时任务  把status设为8（自定义的）   当status=0时，就会自动扫描到，并起一个线程开始进行拨打
@Slf4j
public class FixTimeOutboundJob extends BaseJob {


    private static RosterBatchService batchDao = GetBeanUtil.getBean(RosterBatchService.class);
    @Override
    public void executeBiz(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap map = jobExecutionContext.getJobDetail().getJobDataMap();
        String batchId = map.getString("batchId");
        // TODO: 2018/5/17 0017 更新roster batch status为未拨打状态
        RosterBatchInfo batchInfo = batchDao.getRosterBatch(batchId);
        if (batchInfo != null) {
            if (batchInfo.getStatus() == 8) {
                batchInfo.setCreateTime(TimeUtil.getCurrentTimeStr());
                batchInfo.setStatus(0);
                batchDao.updateRosterBatchInfo(batchInfo);
            }
        }
    }
}
