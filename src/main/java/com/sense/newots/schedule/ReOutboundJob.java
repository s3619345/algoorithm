package com.sense.newots.schedule;

import com.sense.newots.object.GetBeanUtil;
import com.sense.newots.object.entity.RosterBatchInfo;
import com.sense.newots.object.service.RosterBatchService;
import com.sense.newots.object.service.RosterService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by 845477519@qq.com on 2017/11/13 0013.
 */
@Slf4j
public class ReOutboundJob extends BaseJob {
    private RosterBatchService batchDao = GetBeanUtil.getBean(RosterBatchService.class);
    private RosterService rosterDao = GetBeanUtil.getBean(RosterService.class);

    @Override
    public void executeBiz(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap map = jobExecutionContext.getJobDetail().getJobDataMap();
        String batchId = map.getString("batchId");
        RosterBatchInfo batchInfo = batchDao.getRosterBatch(batchId);
        if (batchInfo != null) {
            //判断当前有没有呼叫完成
              log.info("## 进入重播。。。。。。。。。。。。。。。。。。。");
              log.info("## batch_info "+batchInfo.toString());
            int status = batchInfo.getStatus();
            if (status == 2) {
                // TODO: 2018/5/28 0028 如果呼叫完成，将未呼叫成功的任务重置为未开始状态
                batchInfo.setStatus(0);

                //查找每一次重播中仍未拨打成功的，再把这些重新插入到t_roster_info表中
                if (rosterDao.addUnCalledRoster(batchInfo.getBatchId())) {
                    //批次重播轮数加1
                    batchInfo.setCurrentFinishedRound(batchInfo.getCurrentFinishedRound() + 1);
                    batchInfo.setCallRound(batchInfo.getCallRound() + 1);
                    batchInfo.setRepeatCount(batchInfo.getRepeatCount()-1);

                      log.info("###################   "+batchInfo.getCurrentFinishedRound());
                    //遍历内存里面的存放的已完成的批次信息
                    /*for (RosterBatchInfo info : MetricUtil.completeBatchs) {
                        if (info.getBatchId().equals(batchId)) {
                            info.setCurrentFinishedRound(batchInfo.getCurrentFinishedRound());
                            info.setStatus(0);
                              log.info("$$$$$$$$$$$$$$$$$$$$$$ "+info.getCurrentFinishedRound());
                        }
                    }*/
                    batchDao.updateRosterBatchInfo(batchInfo);
                }
            }
        }
    }
}
