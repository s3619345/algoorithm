package com.sense.newots.object.job;


import com.sense.newots.commonentity.CronEntity;
import com.sense.newots.object.service.RosterBatchService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Slf4j
public class JobManager {


    private static SchedulerFactory gSchedulerFactory = new StdSchedulerFactory();
    public static String JOB_GROUP_NAME = "EXTJWEB_JOBGROUP_NAME";
    public static String TRIGGER_GROUP_NAME = "EXTJWEB_TRIGGERGROUP_NAME";

    public static String OUTBOUND_GROUP_NAME = "OUTBOUND_GROUP_NAME";
    public static String OUTBOUND_TRIGGER_GROUP_NAME = "OUTBOUND_GROUP_NAME";

    public static String RE_OUTBOUND_GROUP_NAME = "RE_OUTBOUND_GROUP_NAME";
    public static String RE_OUTBOUND_TRIGGER_GROUP_NAME = "RE_OUTBOUND_TRIGGER_GROUP_NAME";


    private static RosterBatchService batchDao;
    @Autowired
    private RosterBatchService rosterBatchService;

    @PostConstruct
    public void init() {
        batchDao=rosterBatchService;
    }
    public static void addJob(String jobName, Class cls, String time) {
        try {
            Scheduler sched = gSchedulerFactory.getScheduler();
            JobDetail jobDetail = new JobDetail(jobName, JOB_GROUP_NAME, cls);
            CronTrigger trigger = new CronTrigger(jobName, TRIGGER_GROUP_NAME);
            trigger.setCronExpression(time);
            sched.scheduleJob(jobDetail, trigger);
            if (!sched.isShutdown()) {
                sched.start();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean getCurrentlyExecutingJobs() {
        try {
            Scheduler scheduler = gSchedulerFactory.getScheduler();
            List<JobExecutionContext> jobContexts = scheduler.getCurrentlyExecutingJobs();
//            for (JobExecutionContext jobContext: jobContexts) {
//                if("batch_restat_job_0".equals(jobContext.getJobDetail().getName()))
//            }
            if(jobContexts.size()==1) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public static List<CronEntity> getFixTimeJobs() {
        List<CronEntity> list = new ArrayList<>();
        try {
            Scheduler scheduler = gSchedulerFactory.getScheduler();
            for (String groupName : scheduler.getJobGroupNames()) {
                for (String jobName : scheduler.getJobNames(groupName)) {
                    CronEntity cronEntity = new CronEntity();
                    Trigger[] triggers = scheduler.getTriggersOfJob(jobName, groupName);
                    Date nextFireTime = triggers[0].getNextFireTime();
                    if (jobName.contains("定时")) {
                        cronEntity.setBatchId(scheduler.getJobDetail(jobName, JOB_GROUP_NAME).getJobDataMap().getString("batchId"));
                        cronEntity.setJobName(jobName);
                        cronEntity.setGroupName(groupName);
                        cronEntity.setNextFireTime(DateFormatUtils.format(nextFireTime, "yyyy-MM-dd HH:mm:ss"));
                        cronEntity.setState(scheduler.getTriggerState(jobName, TRIGGER_GROUP_NAME));
                        list.add(cronEntity);
                    }
                }
            }
            return list;
        } catch (SchedulerException e) {

        }
        return Collections.emptyList();
    }

    public static void addJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName, Class jobClass, String time) {
        try {
            Scheduler sched = gSchedulerFactory.getScheduler();
            JobDetail jobDetail = new JobDetail(jobName, jobGroupName, jobClass);
            CronTrigger trigger = new CronTrigger(triggerName, triggerGroupName);
            trigger.setCronExpression(time);
            sched.scheduleJob(jobDetail, trigger);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void addJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName, Class jobClass, String time, JobDataMap dataMap) {
        try {
            Scheduler sched = gSchedulerFactory.getScheduler();
            JobDetail jobDetail = new JobDetail(jobName, jobGroupName, jobClass);
            jobDetail.setJobDataMap(dataMap);
            CronTrigger trigger = new CronTrigger(triggerName, triggerGroupName);
            trigger.setCronExpression(time);
            sched.scheduleJob(jobDetail, trigger);
            if (!sched.isShutdown()) {
                sched.start();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void addJobWithFixTime(String jobName, String jobGroupName, String triggerName, String triggerGroupName, Class jobClass, Date date, JobDataMap dataMap, int repeatCount) {
        log.info("## addJobWithFixTime 添加定时任务："+jobName);
        try {
            Scheduler sched = gSchedulerFactory.getScheduler();
            JobDetail jobDetail = new JobDetail(jobName, jobGroupName, jobClass);
            jobDetail.setJobDataMap(dataMap);
            SimpleTrigger trigger = new SimpleTrigger(triggerName, triggerGroupName);
            trigger.setStartTime(date);
            trigger.setRepeatCount(repeatCount);
            sched.scheduleJob(jobDetail, trigger);
            if (!sched.isShutdown()) {
                sched.start();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //监听器
    public static void addJobWithFixTime(String jobName, String jobGroupName, String triggerName, String triggerGroupName, Class jobClass, String cron, JobDataMap dataMap, int repeatCount, JobListener reOutboundFinishedListener) {
        log.info("addJobWithFixTime","7777777777777777777777777777");
        try {
            Scheduler sched = gSchedulerFactory.getScheduler();
            sched.addJobListener(reOutboundFinishedListener);
            CronTrigger trigger = new CronTrigger(triggerName, triggerGroupName);
            trigger.setCronExpression(cron);
            List<Date> dates = TriggerUtils.computeFireTimes(trigger, null, repeatCount);
            for (int i = 0; i < repeatCount; i++) {
                String subJobName = jobName + ":round-" + (i+1);
                JobDetail jobDetail = new JobDetail(subJobName, jobGroupName, jobClass);
                jobDetail.setJobDataMap(dataMap);
                SimpleTrigger simpleTrigger = new SimpleTrigger(subJobName, triggerGroupName);
                simpleTrigger.setStartTime(dates.get(i));
                simpleTrigger.setRepeatCount(0);
                simpleTrigger.setPriority(100);//优先级
                jobDetail.addJobListener(reOutboundFinishedListener.getName());
                sched.scheduleJob(jobDetail, simpleTrigger);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //间隔时间任务
    public static void addJobWithFixTime(String jobName, String jobGroupName, String triggerName, String triggerGroupName, Class jobClass, String cron, JobDataMap dataMap, int repeatCount) {
        log.info("addJobWithFixTime","33333333333333333333333333");
        try {
            Scheduler sched = gSchedulerFactory.getScheduler();
            CronTrigger trigger = new CronTrigger(triggerName, triggerGroupName);
            //当前启动时间（分钟）-1：为了当前时间不运行，getNextFireTime运行
            //String crontab = cron.replaceFirst("\\*\\/",  minute-1+"/");
            trigger.setCronExpression(cron);
            String[] str = cron.split(" ");
            List<Date> dates =null;
            if(str[1].contains("*/")){
                //*/  代表时间间隔
                int intvel = Integer.parseInt(str[1].replaceFirst("\\*\\/",""));
                dates = com.sense.newots.util.Collections.computeFireTimes(trigger, null, repeatCount, intvel);

            }else{
                dates = TriggerUtils.computeFireTimes(trigger, null, repeatCount);
            }

            for (int i = 0; i < repeatCount; i++) {
                String subJobName = jobName + ":round-" + (i+1);
                JobDetail jobDetail = new JobDetail(subJobName, jobGroupName, jobClass);
                jobDetail.setJobDataMap(dataMap);
                SimpleTrigger simpleTrigger = new SimpleTrigger(subJobName, triggerGroupName);
                simpleTrigger.setStartTime(dates.get(i));
                simpleTrigger.setRepeatCount(0);
                sched.scheduleJob(jobDetail, simpleTrigger);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //定时重播任务
    public static void addJobWithFixTime2(String jobName, String jobGroupName, String triggerName, String triggerGroupName, Class jobClass, String cron, JobDataMap dataMap, int repeatCount) {
        log.info(jobName);
        try {
            Scheduler sched = gSchedulerFactory.getScheduler();
            CronTrigger trigger = new CronTrigger(triggerName, triggerGroupName);
            trigger.setCronExpression(cron);
            List<Date> dates = TriggerUtils.computeFireTimes(trigger, null, repeatCount);
            for (int i = 0; i < repeatCount; i++) {
                String subJobName = jobName + ":round-" + (i+1);
                JobDetail jobDetail = new JobDetail(subJobName, jobGroupName, jobClass);
                jobDetail.setJobDataMap(dataMap);
                SimpleTrigger simpleTrigger = new SimpleTrigger(subJobName, triggerGroupName);
                simpleTrigger.setStartTime(dates.get(i));
                simpleTrigger.setRepeatCount(0);
                sched.scheduleJob(jobDetail, simpleTrigger);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void modifyJobTime(String jobName, String time) {
        log.info("修改工作时间,jobName:" + jobName + time);
        try {
            Scheduler sched = gSchedulerFactory.getScheduler();
            SimpleTrigger trigger = (SimpleTrigger) sched.getTrigger(jobName, TRIGGER_GROUP_NAME);
            if (trigger == null) {
                return;
            }
            String oldTime = DateFormatUtils.format(trigger.getNextFireTime(), "yyyy-MM-dd hh:mm:ss");
            if (!oldTime.equalsIgnoreCase(time)) {
                JobDetail jobDetail = sched.getJobDetail(jobName, JOB_GROUP_NAME);
                Class objJobClass = jobDetail.getJobClass();
                removeJob(jobName);
                addJobWithFixTime(jobName, JOB_GROUP_NAME, jobName, TRIGGER_GROUP_NAME, objJobClass, (new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")).parse(time), jobDetail.getJobDataMap(), 0);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void modifyJobTime(String triggerName, String triggerGroupName, String time) {
        try {
            Scheduler sched = gSchedulerFactory.getScheduler();
            CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerName, triggerGroupName);
            if (trigger == null) {
                return;
            }
            String oldTime = trigger.getCronExpression();
            if (!oldTime.equalsIgnoreCase(time)) {
                CronTrigger ct = trigger;
                ct.setCronExpression(time);
                sched.resumeTrigger(triggerName, triggerGroupName);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void removeJob(String jobName) {
        try {
            log.info("删除定时任务："+jobName);
            if (jobName.contains("定时重拨")){
                String[] name = jobName.split("---");
                String[] batch = name[0].split(":");
                String batchId = batch[1];
                batchDao.updateRepeatCount(batchId);
            }
            Scheduler sched = gSchedulerFactory.getScheduler();
            sched.pauseTrigger(jobName, TRIGGER_GROUP_NAME);
            sched.unscheduleJob(jobName, TRIGGER_GROUP_NAME);
            sched.deleteJob(jobName, JOB_GROUP_NAME);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //移除定时任务
    public static void removeJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName) {
        try {
            Scheduler sched = gSchedulerFactory.getScheduler();
            sched.pauseTrigger(triggerName, triggerGroupName);
            sched.unscheduleJob(triggerName, triggerGroupName);
            sched.deleteJob(jobName, jobGroupName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void resumeJob(String jobName, String jobGroupName) {
        try {
            Scheduler sched = gSchedulerFactory.getScheduler();
            sched.resumeJob(jobName, jobGroupName);
        } catch (Exception e) {

        }
    }

    public static void pauseJob(String jobName, String jobGroupName) {
        log.info("暂停任务,jobName:" + jobName);
        Scheduler sched = null;
        try {
            sched = gSchedulerFactory.getScheduler();
            sched.pauseJob(jobName, jobGroupName);
        } catch (SchedulerException e) {

        }
    }

    public static void runJobNow(String jobName, String jobGroupName) {
        Scheduler sched = null;
        try {
            sched = gSchedulerFactory.getScheduler();
            log.info("手动触发任务,jobName:" + jobName);
            List<CronEntity> list = JobManager.getFixTimeJobs();

            sched.triggerJob(jobName, jobGroupName);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //手动触发该任务后删除
            removeJob(jobName, JOB_GROUP_NAME, jobName, TRIGGER_GROUP_NAME);
        } catch (SchedulerException e) {
            log.error("手动触发任务失败:{}", e);
        }
    }

    public static void startJobs() {
        try {
            Scheduler sched = gSchedulerFactory.getScheduler();
            sched.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void shutdownJobs() {
        try {
            Scheduler sched = gSchedulerFactory.getScheduler();
            if (!sched.isShutdown()) {
                sched.shutdown();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}