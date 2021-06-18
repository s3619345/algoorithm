//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sense.newots.object.job;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableMap;
import com.sense.newots.amq.producer.queue.ProducerService;
import com.sense.newots.impl.metric.BatchMetric;
import com.sense.newots.impl.metric.MetricUtil;
import com.sense.newots.object.GetBeanUtil;
import com.sense.newots.object.conf.InitConfig;
import com.sense.newots.object.entity.ActivityInfo;
import com.sense.newots.object.entity.RosterBatchInfo;
import com.sense.newots.object.service.ActivityInfoService;
import com.sense.newots.object.service.BatchRecordService;
import com.sense.newots.object.service.RosterBatchService;
import com.sense.newots.object.service.RosterService;
import com.sense.newots.object.util.TimeUtil;
import com.sense.newots.schedule.ReOutboundJob;
import com.sense.newots.service.UseTransactionService;
import com.sense.newots.util.HttpUtil;
import com.sense.newots.util.MathUtils;
import com.sense.newots.util.RedisUtil;
import com.sense.newots.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.jms.Destination;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

@Slf4j
public class BatchRestatJob implements Job {


    private static Destination apiQueue = (Destination) GetBeanUtil.getBean("apiQueue");
    private RosterBatchService batchDao = GetBeanUtil.getBean(RosterBatchService.class);
    private UseTransactionService useTransactionService = GetBeanUtil.getBean(UseTransactionService.class);

    private ActivityInfoService activityDao = GetBeanUtil.getBean(ActivityInfoService.class);
    private BatchRecordService batchRecordDao = GetBeanUtil.getBean(BatchRecordService.class);
    private RosterService rosterDao = GetBeanUtil.getBean(RosterService.class);
    private ProducerService producerService = GetBeanUtil.getBean(ProducerService.class);
    private ExecutorService executorService = GetBeanUtil.getBean(ExecutorService.class);

    public BatchRestatJob() {
    }

    //批次信息的工作 每分钟进行扫描的工作
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        long currentTiem = System.currentTimeMillis();
        log.info("#@## batch restat job start at " + (new Timestamp(currentTiem)).toString());
        Iterator iterator = MetricUtil.completeBatchs.iterator();
        while (true) {
            while (iterator.hasNext()) {
                //循环遍历rosterBatchinfo的批次数据
                RosterBatchInfo batchInfo = (RosterBatchInfo) iterator.next();
                String batchName = batchInfo.getBatchId();

                //把数据库里要拨打的批次数据和redis里的数据进行对比，如果==0,就代表全部拨打完成
                if (batchInfo.getStatus() == 2 && RedisUtil.getKeys(batchInfo.getActivityName(), batchName) == 0) {
                    log.info("#@## " + batchName + ":" + batchInfo.getDomain() + " round " + batchInfo.getCallRound() + " completed start to restat ");
                    String bname = batchInfo.getBatchId() + ":" + batchInfo.getActivityName();
                    //得到拨打电话的信息
                    BatchMetric metric = MetricUtil.batchMetrics.get(bname);
                    ActivityInfo activityInfo = null;

                    //查询对应批次是否有重播（corn 表达式）
                    RosterBatchInfo myBatchInfo = null;

                    if (metric == null) {
                        log.info("#@## " + bname + " not exist ! ");

                    } else {
                       /* batchInfo.setOutCallNum(metric.getOutCallNum());
                        batchInfo.setAnswerCallNum(metric.getAnswerCallNum());
                        batchInfo.setUnCallNum(metric.getUnCallNum());
                        batchInfo.setDncNum(metric.getDncNum());
                        batchInfo.setFail1Num(metric.getFail1Num());
                        batchInfo.setFail2Num(metric.getFail2Num());
                        batchInfo.setFail3Num(metric.getFail3Num());
                        batchInfo.setFail4Num(metric.getFail4Num());
                        batchInfo.setFail5Num(metric.getFail5Num());
                        batchInfo.setFailOtherNum(metric.getFailOtherNum());*/
                        if (StringUtil.isEmpty(MetricUtil.batOutCallNum.get(bname))) {
                            batchInfo.setOutCallNum(0);
                        } else {
                            batchInfo.setOutCallNum(MetricUtil.batOutCallNum.get(bname).intValue());
                        }
                        batchInfo.setAnswerCallNum((int) MetricUtil.batAnswerCallNum.get(bname));
                        //batchInfo.setUnCallNum(metric.getUnCallNum());
                        //batchInfo.setDncNum(metric.getDncNum());
                        batchInfo.setFail1Num((int) MetricUtil.fail1Num8.get(bname));
                        batchInfo.setFail2Num((int) MetricUtil.fail2Num6.get(bname));
                        batchInfo.setFail3Num((int) MetricUtil.fail3Num1_9.get(bname));
                        batchInfo.setFail4Num((int) MetricUtil.fail4Num11.get(bname));
                        batchInfo.setFail5Num((int) MetricUtil.fail5Num7.get(bname));
                        batchInfo.setFailOtherNum((int) MetricUtil.failOtherNum.get(bname));
                        batchInfo.setCompleteTime(TimeUtil.getCurrentTimeStr());
                        batchInfo.setStatus(2);

                        // 调用了重播接口
                        //if (batchInfo.getCronIdentification() != 1){
                        this.batchRecordDao.updateRecordStatus(TimeUtil.getCurrentTimeStr(), batchInfo.getBatchId());
                        //}

                        //把拨打完成的数据添加到roster_batch_info表中，把状态改为2
                        this.batchDao.updateRosterBatchInfo(batchInfo);
                        this.useTransactionService.updateRosterResult(batchName);


                        activityInfo = this.activityDao.getTActivityInfos(batchInfo.getDomain(), batchInfo.getTemplateName());
                        if (activityInfo != null) {
                            activityInfo.addOutCallNum(batchInfo.getOutCallNum());
                            activityInfo.addAnswerCallNum(batchInfo.getAnswerCallNum());
                            activityInfo.addDncNum(batchInfo.getDncNum());
                            if (batchInfo.getCallRound() == 1) {
                                activityInfo.addCompleteBatchNum();
                            }
                            this.activityDao.updateActivityInfo(activityInfo);
                        }

                        //查询对应批次是否有重播（corn 表达式）
                        myBatchInfo = batchDao.getRosterBatch(batchInfo.getBatchId());

                        //redis 中获取 当前批次成功数量
                        long redisCount = Long.parseLong(RedisUtil.get(batchInfo.getBatchId()));

                        //当前接通率
                        BigDecimal nowSucRate = MathUtils.div(new BigDecimal(redisCount),
                                new BigDecimal(batchInfo.getRoterNum()), 2);

                        //按比例的逻辑
                        if (StringUtil.isNotEmpty(activityInfo.getRate())) {
                            double bRate = 0.0;
                            String batchKey = batchInfo.getBatchId() + ":rate";
                            //redis 中获取按比例增加后应达到接通率
                            //为空 ：代表当前第一次
                            double arate = Double.parseDouble(activityInfo.getRate());
                            if ("0".equalsIgnoreCase(RedisUtil.get(batchKey))) {
                                //按比例增加后应达到接通率
                                bRate = MathUtils.mul(nowSucRate.doubleValue(), MathUtils.add(1.00, arate));
                                if (nowSucRate.compareTo(BigDecimal.ZERO) == 0) {
                                    bRate += arate;
                                }
                                //按比例增加后应达到接通率 放入redis
                                String key = batchInfo.getBatchId() + ":rate";
                                RedisUtil.set(key, String.valueOf(bRate));
                                log.info(batchInfo.getBatchId() + ":now 接通率---" + nowSucRate);
                                log.info(batchInfo.getBatchId() + ":应达到的比率---" + bRate);
                            }

                            bRate = Double.parseDouble(RedisUtil.get(batchKey));
                            if ((nowSucRate.compareTo(BigDecimal.ONE) == 0)) {
                                if (StringUtil.isEmpty(myBatchInfo.getRepeatCronExpression())) {
                                    postApi(batchInfo, activityInfo);
                                    RedisUtil.del(batchKey);
                                    continue;
                                }
                            } else if (nowSucRate.compareTo(BigDecimal.valueOf(bRate)) == -1 && MetricUtil.isbreak.get(batchInfo.getBatchId()) < 3) {
                                MetricUtil.isbreak.incrementAndGet(batchInfo.getBatchId());
                                double needNum = Math.ceil(MathUtils.mul(redisCount, arate));
                                if (needNum != 0) {
                                    replay(batchInfo, (int) needNum);
                                    MetricUtil.completeBatchs.remove(batchInfo);

                                } else {
                                    //为0 数据或者线路异常，直接结束
                                    MetricUtil.isbreak.remove(batchInfo.getBatchId());
                                    if (StringUtil.isEmpty(myBatchInfo.getRepeatCronExpression())) {
                                        postApi(batchInfo, activityInfo);
                                    }
                                    RedisUtil.del(batchKey);
                                }
                                continue;
                            } else {
                                //按比例： 一定触发
                                MetricUtil.isbreak.remove(batchInfo.getBatchId());
                                if (StringUtil.isEmpty(myBatchInfo.getRepeatCronExpression())) {
                                    postApi(batchInfo, activityInfo);
                                }

                                //redis 中删除 批次：成功数量及应达到的比例
                                RedisUtil.del(batchKey);
                            }

                        }
                        //按成功接通率
                        else if (StringUtil.isNotEmpty(activityInfo.getSuccessRate())) {

                            BigDecimal planSucRate = new BigDecimal(activityInfo.getSuccessRate());
                            log.info("now 接通率---" + nowSucRate);
                            log.info("plan 接通率---" + planSucRate);
                            //模板中
                            if (nowSucRate.compareTo(planSucRate) == -1 && batchInfo.getCallRound() < activityInfo.getCallRound()) {
                                double planNum = MathUtils.mul(batchInfo.getRoterNum(), Double.parseDouble(activityInfo.getSuccessRate()));
                                double needNum = Math.ceil(MathUtils.reduce(planNum, redisCount));
                                replay(batchInfo, (int) needNum);
                                MetricUtil.completeBatchs.remove(batchInfo);

                            } else {
                                postApi(batchInfo, activityInfo);
                            }

                            continue;
                        }

                        /*int nextRound = batchInfo.getCallRound() + 1;
                        OutboundRecallPolicy policy = this.recallDao.findRecallPolicy(batchInfo.getDomain(), batchInfo.getActivityName(), nextRound);
                        if (policy != null) {
                            log.info(" # find policy for round " + nextRound);
                            RosterBatchInfo newBatchInfo = new RosterBatchInfo();
                            newBatchInfo.setCallRound(nextRound);
                            newBatchInfo.setStatus(0);
                            newBatchInfo.setDomain(batchInfo.getDomain());
                            newBatchInfo.setActivityId(batchInfo.getActivityId());
                            newBatchInfo.setActivityName(batchInfo.getActivityName());
                            newBatchInfo.setTemplateName(batchInfo.getTemplateName());
                            newBatchInfo.setCreateTime(TimeUtil.getCurrentTimeStr());
                            newBatchInfo.setBatchId(batchInfo.getBatchId());
                            String[] failReasons = policy.getCallResult().split(",");
                            int new_rosterNum = 0;
                            List rInfos = this.rosterDao.findFinishBatchRosters(batchInfo.getDomain(), batchName);
                            if (rInfos == null) {
                                log.info("## has no rosters !");
                            } else {
                                Iterator var17 = rInfos.iterator();

                                while (true) {
                                    while (var17.hasNext()) {
                                        RosterInfo rInfo = (RosterInfo) var17.next();
                                        RosterInfo newRinfo = new RosterInfo();

                                        for (int i = 0; i < failReasons.length; ++i) {
                                            if (rInfo.getCallResult().equals(failReasons[i])) {
                                                BeanUtils.copyProperties(rInfo, newRinfo);
                                                newRinfo.setId(0);
                                                newRinfo.setStatus(0);
                                                newRinfo.setCallRound(nextRound);
                                                newRinfo.setMakeCallTime((String) null);
                                                newRinfo.setCallAnswerTime((String) null);
                                                newRinfo.setCallEndTime((String) null);
                                                newRinfo.setCallId((String) null);
                                                newRinfo.setCallResult((String) null);
                                                newRinfo.setResultCode(-1);
                                                newRinfo.setBatchName(batchInfo.getBatchId());
                                                ++new_rosterNum;
                                                this.rosterDao.createRosterInfo(newRinfo);
                                                break;
                                            }
                                        }
                                    }

                                    if (new_rosterNum > 0) {
                                        newBatchInfo.setRoterNum(new_rosterNum);
                                        this.batchDao.createRosterBatchInfo(newBatchInfo);
                                    }
                                    break;
                                }
                            }
                        }*/
                    }

                    if (activityInfo != null) {
                        // TODO: 2018/6/3 0003 定时重拨
                        if (myBatchInfo.getRepeatCronExpression() != null && myBatchInfo.getRepeatCount() != 0) {
                            String cron = myBatchInfo.getRepeatCronExpression();
                            int repeatCount = myBatchInfo.getRepeatCount();
                            String jobName = "定时重拨:" + myBatchInfo.getBatchId() + "---" + myBatchInfo.getActivityName();
                            JobDataMap jobDataMap = new JobDataMap();
                            jobDataMap.put("batchId", myBatchInfo.getBatchId());
                            String[] jobCron = cron.split(" ");
                            if (jobCron[1].contains("*/")) {
                                try {

                                    //  ReOutboundFinishedListener listener = new ReOutboundFinishedListener();监听器
                                    JobManager.addJobWithFixTime(jobName, JobManager.JOB_GROUP_NAME, jobName, JobManager.TRIGGER_GROUP_NAME, ReOutboundJob.class, cron, jobDataMap, 1);
                                } catch (Exception e) {
                                    log.error("添加间隔重播任务异常" + e);
                                }
                            } else {
                                if (myBatchInfo.getRerunRound() == 0) {
                                    try {
                                        //  ReOutboundFinishedListener listener = new ReOutboundFinishedListener();监听器
                                        JobManager.addJobWithFixTime2(jobName, JobManager.JOB_GROUP_NAME, jobName, JobManager.TRIGGER_GROUP_NAME, ReOutboundJob.class, cron, jobDataMap, repeatCount);
                                        myBatchInfo.setRerunRound(1);
                                          /*  for (RosterBatchInfo info : MetricUtil.completeBatchs) {
                                                if (info.getBatchId().equals(myBatchInfo.getBatchId())) {
                                                    info.setRerunRound(1);
                                                }
                                            }*/
                                    } catch (Exception e) {
                                        log.error("添加定时重播任务异常" + e);
                                    }
                                }
                            }

                            batchDao.updateRosterBatchInfo(myBatchInfo);
                            MetricUtil.completeBatchs.remove(batchInfo);
                        }

                        //getRepeatCount==0 代表打完
                        if (StringUtil.isNotEmpty(myBatchInfo.getRepeatCronExpression()) && myBatchInfo.getRepeatCount() == 0) {
                            log.info("## 最后拨打轮数: " + myBatchInfo.getCurrentFinishedRound() + "  ## 重播轮数 :" + myBatchInfo.getRepeatCount() + "  ## 批次id :" + myBatchInfo.getBatchId());
                            //推送api
                            postApi(batchInfo, activityInfo);

                        } else if (StringUtil.isEmpty(myBatchInfo.getRepeatCronExpression()) /*&& myBatchInfo.getCallRound() == 1*/) {

                            postApi(batchInfo, activityInfo);
                        }
                    }

                } else {
                    log.info("#@## " + batchName + ":" + batchInfo.getDomain() + " not complete ! ");
                }

            }

            return;
        }
    }

    public void replay(RosterBatchInfo batchInfo, int needNum) {
        batchInfo.setStatus(0);
        batchInfo.setCurrentFinishedRound(batchInfo.getCurrentFinishedRound() + 1);
        batchInfo.setCallRound(batchInfo.getCallRound() + 1);
        rosterDao.updateInfoByStatus(batchInfo.getBatchId(), needNum);
        batchDao.updateRosterBatchInfo(batchInfo);
    }

    public void postApi(RosterBatchInfo batchInfo, ActivityInfo activityInfo) {
        Map postMap = ImmutableMap.<String, String>builder()
                .put("type", "batch").put("batchId", batchInfo.getBatchId())
                .build();
        String jsonStr = JSONObject.toJSONString(postMap);

        if ("fail".equals(activityInfo.getIsSMS())) {
            //异步调短信
            executorService.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    Map smsMap = new HashMap();
                    smsMap.put("templateCodeFail", activityInfo.getName() + "_fail");
                    smsMap.put("batchId", batchInfo.getBatchId());
                    smsMap.put("dataEnvironment", InitConfig.ENVIRONMENT);

                    try {
                        String respon = HttpUtil.post(InitConfig.SMS_FAIL, smsMap);
                        log.info("请求发短信成功---" + respon);
                    } catch (Exception e) {
                        log.info("请求发短信失败---" + smsMap, "failed cause" + e);
                    }
                    return null;
                }
            });
        }

        producerService.sendMessage(apiQueue, jsonStr);
        log.info("推送api---" + jsonStr);

        //删除
        for (RosterBatchInfo info : MetricUtil.completeBatchs) {
            if (info.getBatchId().equals(batchInfo.getBatchId())) {
                MetricUtil.completeBatchs.remove(info);
            }
        }
        //当前批次的成功数量redis
        RedisUtil.del(batchInfo.getBatchId());
        //删除该模版和批次在redis的数量
        String key = batchInfo.getActivityName() + "_" + batchInfo.getBatchId();
        RedisUtil.del(key);
    }
}
