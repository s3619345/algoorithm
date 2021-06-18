package com.sense.newots.amq.consumer.queue;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.sense.newots.impl.metric.MetricUtil;
import com.sense.newots.object.GetBeanUtil;
import com.sense.newots.object.entity.ActivityInfo;
import com.sense.newots.object.entity.BatchRecord;
import com.sense.newots.object.entity.RosterBatchInfo;
import com.sense.newots.object.entity.RosterInfo;
import com.sense.newots.object.job.JobManager;
import com.sense.newots.object.service.ActivityInfoService;
import com.sense.newots.object.service.BatchRecordService;
import com.sense.newots.object.service.RosterBatchService;
import com.sense.newots.object.service.RosterService;
import com.sense.newots.object.util.TimeUtil;
import com.sense.newots.request.BaseUpennyRosterRequest;
import com.sense.newots.request.UpennyRosterBatchListRequest;
import com.sense.newots.schedule.FixTimeOutboundJob;
import com.sense.newots.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.commons.lang.StringUtils;
import org.quartz.JobDataMap;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.*;

/**
 * Created by Administrator on 2018/11/6 0006.
 */
@Slf4j
public class MyMessageListener implements MessageListener {

    static Gson gson = new Gson();
    private RosterService rosterDao = GetBeanUtil.getBean(RosterService.class);
    private ActivityInfoService activityDao = GetBeanUtil.getBean(ActivityInfoService.class);
    private RosterBatchService rosterBatchDao = GetBeanUtil.getBean(RosterBatchService.class);
    private BatchRecordService batchRecordDao = GetBeanUtil.getBean(BatchRecordService.class);


    @Override
    public void onMessage(Message message) {
            try {
                if(message instanceof ActiveMQObjectMessage){
                    ActiveMQObjectMessage mq = (ActiveMQObjectMessage) message;
                    UpennyRosterBatchListRequest upennyRosterBatchListRequest = JSONObject.parseObject((String) mq.getObject(), UpennyRosterBatchListRequest.class);
                    log.info("ObjectQueueMessageListener监听到队列"+"批次ID："+upennyRosterBatchListRequest.getBatchId()+"商户名称："+upennyRosterBatchListRequest.getUserName()+ "模板名称："+upennyRosterBatchListRequest.getTemplateCode()+
                            "批次总数："+upennyRosterBatchListRequest.getRosterTotal()+"重播策略："+upennyRosterBatchListRequest.getRepeatCronExpression()+"重播轮数："+upennyRosterBatchListRequest.getRepeatCount()+"定时拨打时间"+upennyRosterBatchListRequest.getPlanStartTime()+" 第 [" +upennyRosterBatchListRequest.getGroupTotal() + "]接收");

                    //String batchId1 = upennyRosterBatchListRequest.getBatchId().toString();
                    String templateName1 = upennyRosterBatchListRequest.getTemplateCode();
                    ActivityInfo aInfo1 = this.activityDao.findActivityInfoByTemplate(templateName1);

                    if (aInfo1 != null) {
                        String domain = aInfo1.getDomain();
                        try {
                            if (upennyRosterBatchListRequest.getJobList() != null) {
                                List<RosterInfo> rosterInfoList = new ArrayList<>();
                                //把api传过来的数据遍历
                                Iterator resCount = upennyRosterBatchListRequest.getJobList().iterator();

                                while (resCount.hasNext()) {
                                    BaseUpennyRosterRequest templateName = (BaseUpennyRosterRequest) resCount.next();
                                    if (templateName.getJobData() != null) {
                                        HashMap aInfo = new HashMap();

                                        aInfo.put("loanChannel", templateName.getJobData().getLoanChannel());
                                        aInfo.put("loanOverdueDay", templateName.getJobData().getLoanOverdueDay());
                                        aInfo.put("loanRepayAmount", templateName.getJobData().getLoanRepayAmount() == null ? "0" : templateName.getJobData().getLoanRepayAmount().toString());
                                        if (StringUtils.isNotBlank(templateName.getJobData().getLoanExpireDate())) {
                                            aInfo.put("loanExpireDate", templateName.getJobData().getLoanExpireDate());
                                        }

                                        if (templateName.getJobData().getLoanAmount() != null) {
                                            aInfo.put("loanAmount", templateName.getJobData().getLoanAmount());
                                        }

                                        if (templateName.getJobData().getLoanDays() != null) {
                                            aInfo.put("loanDays", templateName.getJobData().getLoanDays());
                                        }

                                        if (templateName.getJobData().getLoanUserIdNums() != null) {
                                            aInfo.put("loanUserIdNums", templateName.getJobData().getLoanUserIdNums());
                                        }

                                        if (templateName.getJobData().getLoanUserWages() != null) {
                                            aInfo.put("loanUserWages", templateName.getJobData().getLoanUserWages());
                                        }

                                        if (templateName.getJobData().getCompanyFullName() != null) {
                                            aInfo.put("companyFullName", templateName.getJobData().getCompanyFullName());
                                        }

                                        if (templateName.getJobData().getCompanyShortName() != null) {
                                            aInfo.put("companyShortName", templateName.getJobData().getCompanyShortName());
                                        }

                                        if (templateName.getJobData().getExtra1() != null) {
                                            aInfo.put("extra1", templateName.getJobData().getExtra1());
                                        }

                                        if (templateName.getJobData().getExtra2() != null) {
                                            aInfo.put("extra2", templateName.getJobData().getExtra2());
                                        }

                                        if (templateName.getJobData().getExtra3() != null) {
                                            aInfo.put("extra3", templateName.getJobData().getExtra3());
                                        }

                                        if (templateName.getJobData().getExtra4() != null) {
                                            aInfo.put("extra4", templateName.getJobData().getExtra4());
                                        }

                                        if (templateName.getJobData().getExtra5() != null) {
                                            aInfo.put("extra5", templateName.getJobData().getExtra5());
                                        }

                                        if (templateName.getJobData().getExtra6() != null) {
                                            aInfo.put("extra6", templateName.getJobData().getExtra6());
                                        }

                                        if (templateName.getJobData().getExtra7() != null) {
                                            aInfo.put("extra7", templateName.getJobData().getExtra7());
                                        }

                                        if (templateName.getJobData().getExtra8() != null) {
                                            aInfo.put("extra8", templateName.getJobData().getExtra8());
                                        }

                                        if (templateName.getJobData().getExtra9() != null) {
                                            aInfo.put("extra9", templateName.getJobData().getExtra9());
                                        }

                                        if (templateName.getJobData().getExtra10() != null) {
                                            aInfo.put("extra10", templateName.getJobData().getExtra10());
                                        }

                                        RosterInfo rosterInfo = new RosterInfo();
                                        rosterInfo.setDomain(domain);
                                        rosterInfo.setCallRound(0);
                                        rosterInfo.setActivityName(aInfo1.getName());
                                        rosterInfo.setTemplateName(templateName1);
                                        rosterInfo.setBatchName(upennyRosterBatchListRequest.getBatchId());
                                        rosterInfo.setJobId(templateName.getJobId());
                                        rosterInfo.setPhoneNum1(templateName.getPhone().toString());
                                        rosterInfo.setFirstname(templateName.getJobData().getLoanUsername());
                                        rosterInfo.setCallee(templateName.getCallee());
                                        rosterInfo.setPrefix(templateName.getPrefix());
                                        rosterInfo.setOperators(templateName.getOperators());
                                        //黑名单
                                        if (StringUtil.isNotEmpty(rosterInfo.getPrefix()) && rosterInfo.getPrefix().contains("Black")){
                                            rosterInfo.setStatus(3);
                                        }
                                        if ("男".equals(templateName.getJobData().getLoanUserGender())) {
                                            rosterInfo.setSex("0");
                                        } else if ("女".equals(templateName.getJobData().getLoanUserGender())) {
                                            rosterInfo.setSex("1");
                                        } else {
                                            rosterInfo.setSex("null");
                                        }
                                        rosterInfo.setEmail("");
                                        rosterInfo.setAddress("");
                                        rosterInfo.setCustomerId("");
                                        rosterInfo.setCreateTime(TimeUtil.getCurrentTimeStr());
                                        if (!aInfo.isEmpty()) {
                                            rosterInfo.setCustomFields(gson.toJson(aInfo));
                                        }
                                        rosterInfoList.add(rosterInfo);
                                    }
                                }
                                rosterDao.createJdbcRosterInfo(rosterInfoList);
                                //rosterDao.createJdbcRosterInfoList(upennyRosterBatchListRequest.getJobList(),batchId1,domain,templateName1,aInfo1.getName());
                                rosterInfoList.clear();

                            }
                            //入批次表
                            if (upennyRosterBatchListRequest.getRosterTotal() != null) {
                                RosterBatchInfo batchInfo1 = new RosterBatchInfo();
                                //以下是组装表 t_roster_batch_info所需的字段 包括重播所需的参数
                                if (upennyRosterBatchListRequest.getBatchId() != null) {
                                    String batchId = upennyRosterBatchListRequest.getBatchId();
                                    batchInfo1.setBatchId(batchId);
                                    batchInfo1.setTemplateName(templateName1);
                                    batchInfo1.setCreateTime(TimeUtil.getCurrentTimeStr());
                                    batchInfo1.setRoterNum(upennyRosterBatchListRequest.getRosterTotal());

                                    batchInfo1.setDomain(domain);
                                    batchInfo1.setCallRound(1);
                                    batchInfo1.setRepeatCronExpression(upennyRosterBatchListRequest.getRepeatCronExpression());//重播的参数
                                    batchInfo1.setRepeatCount(upennyRosterBatchListRequest.getRepeatCount());//重播的参数
                                    batchInfo1.setCurrentFinishedRound(0);
                                    batchInfo1.setCronIdentification(0);
                                    batchInfo1.setRerunRound(0);

                                    if (upennyRosterBatchListRequest.getPlanStartTime() != null) {
                                        batchInfo1.setPlanStartTime(upennyRosterBatchListRequest.getPlanStartTime());
                                        batchInfo1.setStatus(8);
                                    }
                                    batchInfo1.setActivityId(aInfo1.getId());
                                    batchInfo1.setActivityName(aInfo1.getName());
//                                    aInfo1.addRosterNum(upennyRosterBatchListRequest.getRosterTotal());
//                                    aInfo1.addBatchNum();
                                    this.activityDao.updateRosterNum(upennyRosterBatchListRequest.getRosterTotal(),aInfo1.getName());
                                    MetricUtil.addRostersDay(aInfo1.getName(),aInfo1, domain, upennyRosterBatchListRequest.getRosterTotal(),batchId);
                                    this.rosterBatchDao.createRosterBatchInfo(batchInfo1);
                                    BatchRecord batchRecord = new BatchRecord();
                                    batchRecord.setBatchId(batchId);
                                    batchRecord.setStatus(0);
                                    batchRecord.setPrefix(aInfo1.getPrefix());
                                    batchRecord.setMaxCapacity(aInfo1.getMaxCapacity());
                                    batchRecord.setTrunkGrp(aInfo1.getTrunkGrp());
                                    batchRecord.setAddTime(TimeUtil.getCurrentTimeStr());
                                    batchRecord.setUserName(upennyRosterBatchListRequest.getUserName());
                                    this.batchRecordDao.saveBatchRecord(batchRecord);

                                    // TODO: 2018/6/3 0003 定时外呼
                                    if (upennyRosterBatchListRequest.getPlanStartTime() != null) {
                                        Date planStartDate = new Date(Long.parseLong(batchInfo1.getPlanStartTime()));
                                        String jobName = "定时外呼:" + batchId+"---" + batchInfo1.getActivityName();
                                        JobDataMap jobDataMap = new JobDataMap();
                                        jobDataMap.put("batchId", batchId);
                                        JobManager.addJobWithFixTime(jobName, JobManager.JOB_GROUP_NAME, jobName, JobManager.TRIGGER_GROUP_NAME, FixTimeOutboundJob.class, planStartDate, jobDataMap, 0);
                                    }
                                }
                            }

                        } catch (IllegalArgumentException var11) {
                            log.error("批量导入失败:{}", var11);
                        } catch (Exception var12) {
                            log.error("mq 执行批次和数据入库失败 execute Fail!:{}", var12);
                        }
                    } else {
                        log.error("activity表没有对应模板信息");
                    }
                }else if(message instanceof MapMessage){

                    log.info("ObjectQueueMessageListener监听到了失败重发消息：\t"
                            + message);

                    //处理失败重发的信息
                    final MapMessage mapMessage = (MapMessage) message;
                    log.info("失败重发信息"+mapMessage.toString());
                    String data = mapMessage.getString("data");
                    JSONObject jsonObject=JSONObject.parseObject(data);
                    UpennyRosterBatchListRequest failRosterBatchListRequest = (UpennyRosterBatchListRequest) JSONObject.toJavaObject(jsonObject,UpennyRosterBatchListRequest.class);
                    String templateName = failRosterBatchListRequest.getTemplateCode();
                    ActivityInfo aInfo1 = this.activityDao.findActivityInfoByTemplate(templateName);
                    if (aInfo1!=null) {
                        RosterInfoAssemble.rosterInfoPackage(failRosterBatchListRequest);
                        String batchId = failRosterBatchListRequest.getBatchId();
                        String domain = aInfo1.getDomain();
                        RosterBatchInfo rosterBatchInfo = rosterBatchDao.getRosterBatch(batchId);
                        if (rosterBatchInfo != null) {
                            rosterBatchInfo.setStatus(0);
                            rosterBatchDao.updateRosterBatchInfo(rosterBatchInfo);
                        } else {
                            if (failRosterBatchListRequest.getRosterTotal() != null) {
                                RosterBatchInfo batchInfo1 = new RosterBatchInfo();
                                //以下是组装表 t_roster_batch_info所需的字段 包括重播所需的参数
                                if (failRosterBatchListRequest.getBatchId() != null) {
                                    batchInfo1.setBatchId(batchId);
                                    batchInfo1.setTemplateName(templateName);
                                    batchInfo1.setCreateTime(TimeUtil.getCurrentTimeStr());
                                    batchInfo1.setRoterNum(failRosterBatchListRequest.getRosterTotal());
                                    batchInfo1.setDomain(domain);
                                    batchInfo1.setCallRound(1);
                                    batchInfo1.setRepeatCronExpression(failRosterBatchListRequest.getRepeatCronExpression());//重播的参数
                                    batchInfo1.setRepeatCount(failRosterBatchListRequest.getRepeatCount());//重播的参数
                                    batchInfo1.setCurrentFinishedRound(0);
                                    batchInfo1.setCronIdentification(0);
                                    batchInfo1.setRerunRound(0);

                                    if (failRosterBatchListRequest.getPlanStartTime() != null) {
                                        batchInfo1.setPlanStartTime(failRosterBatchListRequest.getPlanStartTime());
                                        batchInfo1.setStatus(8);
                                    }
                                    batchInfo1.setActivityId(aInfo1.getId());
                                    batchInfo1.setActivityName(aInfo1.getName());
//                                    aInfo1.addRosterNum(failRosterBatchListRequest.getRosterTotal());
//                                    aInfo1.addBatchNum();
                                    this.activityDao.updateRosterNum(failRosterBatchListRequest.getRosterTotal(),aInfo1.getName());
                                    MetricUtil.addRostersDay(aInfo1.getName(),aInfo1, domain, failRosterBatchListRequest.getRosterTotal(),batchId);
                                    this.rosterBatchDao.createRosterBatchInfo(batchInfo1);

                                    // TODO: 2018/6/3 0003 定时外呼
                                    if (failRosterBatchListRequest.getPlanStartTime() != null) {
                                        Date planStartDate = new Date(Long.parseLong(batchInfo1.getPlanStartTime()));
                                        String jobName = "定时外呼:" + batchId;
                                        JobDataMap jobDataMap = new JobDataMap();
                                        jobDataMap.put("batchId", batchId);
                                        JobManager.addJobWithFixTime(jobName, JobManager.JOB_GROUP_NAME, jobName, JobManager.TRIGGER_GROUP_NAME, FixTimeOutboundJob.class, planStartDate, jobDataMap, 0);
                                    }
                                }

                            }
                        }
                    }

                }  else if(message instanceof TextMessage) {
                    String receiveMessage = ((TextMessage) message).getText();

                    log.info("QueueMessageListener监听到了心跳消息：\t"

                            + receiveMessage);
                }
            } catch (Exception e) {
                log.info("Listener Failed 监听队列失败",e);
            }

    }
}
