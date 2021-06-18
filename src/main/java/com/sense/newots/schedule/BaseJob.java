package com.sense.newots.schedule;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateFormatUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.utils.Key;

/**
 * Created by 845477519@qq.com on 2017/11/13 0013.
 */
@Slf4j
public abstract class BaseJob implements Job {

    private String batchDate;



    public String getBatchDate() {
        return batchDate;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        boolean isSuccess = true;
        batchDate = DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMdd");
        Key triggerKey = jobExecutionContext.getTrigger().getKey();
        String jobDetailName = triggerKey.getName();
        log.info("任务" + jobDetailName + "执行开始");
        try {
            executeBiz(jobExecutionContext);
            log.info("任务" + jobDetailName + "执行结束");

        } catch (Exception e) {
            isSuccess = false;
            log.error("任务" + jobDetailName + "执行失败。。。,异常为:" + e);
        }
        if (!isSuccess) {
            // TODO: 2017/11/14 0014 跑批失败
        }
    }

    public abstract void executeBiz(JobExecutionContext jobExecutionContext) throws Exception;
}
