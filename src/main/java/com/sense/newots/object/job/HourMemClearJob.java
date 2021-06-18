package com.sense.newots.object.job;


import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;


@Slf4j
public class HourMemClearJob implements Job {

    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void execute(JobExecutionContext context)
            throws JobExecutionException {
        long currentTiem = System.currentTimeMillis();
        log.info("#@## Hour men task start at " + new Timestamp(currentTiem).toString());

        long used = gcMemory();
        if (used >= 300L) {
            log.info("### used memory " + used + " start to gc !");
            System.gc();
            log.info("## after gc info ");
            gcMemory();
        }
    }

    public long gcMemory() {
        long freeMemory = Runtime.getRuntime().freeMemory();
        long totalMemory = Runtime.getRuntime().totalMemory();
        long maxsMemory = Runtime.getRuntime().maxMemory();
        freeMemory = freeMemory / 1024L / 1024L;
        totalMemory = totalMemory / 1024L / 1024L;
        maxsMemory = maxsMemory / 1024L / 1024L;

        log.info("### free mem " + freeMemory + "M | " + "total mem " + totalMemory + "M | " + "max mem " +
                    maxsMemory + "M");

        long usedMem = totalMemory - freeMemory;
        log.info("#### used mem " + usedMem + "M");


        ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();
        int noThreads = currentGroup.activeCount();
        Thread[] lstThreads = new Thread[noThreads];
        currentGroup.enumerate(lstThreads);
        for (int i = 0; i < noThreads; i++) {
            log.info("线程号：" + lstThreads[i].getId() + " = " + lstThreads[i].getName());
        }

        return usedMem;
    }
}
