package com.sense.newots.object.job;

import com.sense.newots.object.StopCircuitJob;

public class JobUtil {

    //定时任务
    public static void init() {
        //每小时执行一次垃圾回收
        JobManager.addJob("hour_report_job_0", HourMemClearJob.class, "0 0 * * * ?");
        //每天清理拨打信息
        JobManager.addJob("day_clear_job", DayClearJob.class, "0 0 0 * * ?");
        //批次信息拨打的定时任务  每分钟执行一次
        JobManager.addJob("batch_restat_job_0", BatchRestatJob.class, "0 0/2 * * * ? ");
        //每天晚上23点清理当天未拨打完的重播任务
        JobManager.addJob("redis_clear_job",RedisDayClearJob.class,"0 00 23 * * ? *");
        //每隔十分钟扫描一次circuit_conf表，是否切换线路  开始任务
        JobManager.addJob("change_circuit_job",StartCircuitJob.class,"0 0/10 * * * ? ");
        //每隔十分钟扫描一次circuit_conf表，是否切换线路  停止任务
        JobManager.addJob("stop_circuit_job", StopCircuitJob.class,"0 0/10 * * * ? ");

        //每隔五分钟记录 人工应答数和点击数
        JobManager.addJob("answer_click_job",AnswerClickJob.class,"0 0/5 8,9,10,11,12,13,14,15,16,17,18,19,20 * * ? ");
    }
}





