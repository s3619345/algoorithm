package com.sense.newots.object.controller;

import com.google.gson.Gson;
import com.sense.newots.impl.metric.MetricUtil;
import com.sense.newots.object.entity.ActivityInfo;
import com.sense.newots.object.entity.RosterBatchInfo;
import com.sense.newots.object.entity.RosterInfo;
import com.sense.newots.object.job.JobManager;
import com.sense.newots.object.service.ActivityInfoService;
import com.sense.newots.object.service.RosterBatchService;
import com.sense.newots.object.service.RosterService;
import com.sense.newots.object.util.TimeUtil;
import com.sense.newots.request.BaseUpennyRosterRequest;
import com.sense.newots.request.UpennyRosterBatchListRequest;
import com.sense.newots.request.UpennyRosterResponse;
import com.sense.newots.schedule.FixTimeOutboundJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.quartz.JobDataMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Produces;
import java.util.*;

/**
 @desc 回调函数模块
 @date 2021-06-02 10:17:49
 @author szz
 */
@RestController
@RequestMapping("/upennyRoster")
@Slf4j
public class UpennyRosterController {
    @Autowired
    private ActivityInfoService activityDao;
    @Autowired
    private RosterService rosterDao;
    @Autowired
    private RosterBatchService rosterBatchDao;
    static Gson gson = new Gson();

    @RequestMapping("/job/batch/jobList")
    @Produces({"application/json"})
    public String addSingleTaskListInterface(@RequestBody UpennyRosterBatchListRequest upennyRosterBatchListRequest) {
        log.info("receive request " + gson.toJson(upennyRosterBatchListRequest));
        UpennyRosterResponse response = null;

        try {
            //使用断言进行判断
            Assert.notNull(upennyRosterBatchListRequest.getBatchId(), "批次id不能为空");
            Assert.hasText(upennyRosterBatchListRequest.getTemplateCode(), "流程模板编号不能为空");
            Assert.notEmpty(upennyRosterBatchListRequest.getJobList(), "任务列表不能为空");
            ArrayList<Map> e = new ArrayList();

            //把api传过来的数据遍历
            Iterator resCount = upennyRosterBatchListRequest.getJobList().iterator();

            while (resCount.hasNext()) {
                BaseUpennyRosterRequest templateName = (BaseUpennyRosterRequest) resCount.next();
                if (templateName.getJobData() != null) {
                    HashMap aInfo = new HashMap();
                    aInfo.put("callee", templateName.getCallee());
                    aInfo.put("prefix", templateName.getPrefix());
                    aInfo.put("operators", templateName.getOperators());
                    aInfo.put("id", templateName.getJobId());
                    aInfo.put("phoneNum1", templateName.getPhone().toString());
                    aInfo.put("batchId", upennyRosterBatchListRequest.getBatchId().toString());
                    aInfo.put("callbackUrl", "");
                    aInfo.put("firstname", templateName.getJobData().getLoanUsername());
                    aInfo.put("lastName", "");
                    if ("男".equals(templateName.getJobData().getLoanUserGender())) {
                        aInfo.put("sex", "0");
                    } else if ("女".equals(templateName.getJobData().getLoanUserGender())) {
                        aInfo.put("sex", "1");
                    } else {
                        aInfo.put("sex", "null");
                    }
                    aInfo.put("email", "");
                    aInfo.put("address", "");
                    aInfo.put("customerId", "");
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

                    e.add(aInfo);
                }
            }

            List<RosterInfo> rosterInfoList = new ArrayList<>();
            if (e != null && e.size() > 0) {
                //获取模板名称
                String templateName1 = upennyRosterBatchListRequest.getTemplateCode();
                int resCount1 = e.size();
                ActivityInfo aInfo1 = this.activityDao.findActivityInfoByTemplate(templateName1);
                if (aInfo1 != null) {
                    String domain = aInfo1.getDomain();

                    RosterInfo rosterInfo;
                    for (Map batchInfo : e) {
                        rosterInfo = new RosterInfo();
                        rosterInfo.setDomain(domain);
                        rosterInfo.setCallRound(1);
                        if (aInfo1 != null) {
                            rosterInfo.setActivityName(aInfo1.getName());
                        }
                        rosterInfo.setTemplateName(templateName1);
                        if (batchInfo.containsKey("batchId")) {
                            rosterInfo.setBatchName((String) batchInfo.get("batchId"));
                            batchInfo.remove("batchId");
                        }

                        if (batchInfo.containsKey("id")) {
                            rosterInfo.setJobId((String) batchInfo.get("id"));
                            batchInfo.remove("id");
                        }

                        if (batchInfo.containsKey("phoneNum1")) {
                            rosterInfo.setPhoneNum1((String) batchInfo.get("phoneNum1"));
                            batchInfo.remove("phoneNum1");
                        }

                        if (batchInfo.containsKey("phoneNum2")) {
                            rosterInfo.setPhoneNum2((String) batchInfo.get("phoneNum2"));
                            batchInfo.remove("phoneNum2");
                        }

                        if (batchInfo.containsKey("phoneNum3")) {
                            rosterInfo.setPhoneNum3((String) batchInfo.get("phoneNum3"));
                            batchInfo.remove("phoneNum3");
                        }

                        if (batchInfo.containsKey("phoneNum4")) {
                            rosterInfo.setPhoneNum4((String) batchInfo.get("phoneNum4"));
                            batchInfo.remove("phoneNum4");
                        }

                        if (batchInfo.containsKey("phoneNum5")) {
                            rosterInfo.setPhoneNum5((String) batchInfo.get("phoneNum5"));
                            batchInfo.remove("phoneNum5");
                        }

                        if (batchInfo.containsKey("lastname")) {
                            rosterInfo.setLastname((String) batchInfo.get("lastname"));
                            batchInfo.remove("lastname");
                        }

                        if (batchInfo.containsKey("firstname")) {
                            rosterInfo.setFirstname((String) batchInfo.get("firstname"));
                            batchInfo.remove("firstname");
                        }

                        if (batchInfo.containsKey("age")) {
                            rosterInfo.setAge(Integer.parseInt((String) batchInfo.get("age")));
                            batchInfo.remove("age");
                        }

                        if (batchInfo.containsKey("sex")) {
                            rosterInfo.setSex((String) batchInfo.get("sex"));
                            batchInfo.remove("sex");
                        }

                        if (batchInfo.containsKey("customerId")) {
                            rosterInfo.setCustomerId((String) batchInfo.get("customerId"));
                            batchInfo.remove("customerId");
                        }

                        if (batchInfo.containsKey("address")) {
                            rosterInfo.setAddress((String) batchInfo.get("address"));
                            batchInfo.remove("address");
                        }

                        if (batchInfo.containsKey("email")) {
                            rosterInfo.setEmail((String) batchInfo.get("email"));
                            batchInfo.remove("email");
                        }

                        if (batchInfo.containsKey("cardType")) {
                            rosterInfo.setCardType((String) batchInfo.get("cardType"));
                            batchInfo.remove("cardType");
                        }

                        if (batchInfo.containsKey("cardNum")) {
                            rosterInfo.setCardNum((String) batchInfo.get("cardNum"));
                            batchInfo.remove("cardNum");
                        }
                        rosterInfo.setCallee((String) batchInfo.get("callee"));
                        rosterInfo.setPrefix((String) batchInfo.get("prefix"));
                        rosterInfo.setOperators((String) batchInfo.get("operators"));
                        rosterInfo.setCreateTime(TimeUtil.getCurrentTimeStr());
                        if (!batchInfo.isEmpty()) {
                            rosterInfo.setCustomFields(gson.toJson(batchInfo));
                        }
                        rosterInfoList.add(rosterInfo);
                    }
                    //jdbc批量插入到t_roster_info表中
                    rosterDao.createJdbcRosterInfo(rosterInfoList);

                    RosterBatchInfo batchInfo1 = new RosterBatchInfo();

                    //以下是组装表 t_roster_batch_info所需的字段 包括重播所需的参数
                    if (upennyRosterBatchListRequest.getBatchId() != null) {
                        String batchId = "" + upennyRosterBatchListRequest.getBatchId();
                        batchInfo1.setBatchId(batchId);
                        batchInfo1.setTemplateName(templateName1);
                        batchInfo1.setCreateTime(TimeUtil.getCurrentTimeStr());
                        batchInfo1.setRoterNum(resCount1);
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
                        aInfo1.addRosterNum(resCount1);
                        aInfo1.addBatchNum();
                        this.activityDao.updateActivityInfo(aInfo1);
                        MetricUtil.addRostersDay(aInfo1.getName(), aInfo1, domain, resCount1, batchId);
                        this.rosterBatchDao.createRosterBatchInfo(batchInfo1);

                        // TODO: 2018/6/3 0003 定时外呼
                        if (upennyRosterBatchListRequest.getPlanStartTime() != null) {
                            Date planStartDate = new Date(Long.parseLong(batchInfo1.getPlanStartTime()));
                            //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //格式化数据
                            String jobName = "定时外呼:" + batchId;
                            JobDataMap jobDataMap = new JobDataMap();
                            jobDataMap.put("batchId", batchId);
                            JobManager.addJobWithFixTime(jobName, JobManager.JOB_GROUP_NAME, jobName, JobManager.TRIGGER_GROUP_NAME, FixTimeOutboundJob.class, planStartDate, jobDataMap, 0);
                        }
                    }
                } else {
                    log.error("activity表没有对应模板信息");
                }

                response = this.getCommonResponse(resCount1);
            } else {
                response = new UpennyRosterResponse(0, "批量导入失败");
            }
        } catch (IllegalArgumentException var11) {
            response = new UpennyRosterResponse(0, var11.getMessage());
        } catch (Exception var12) {
            response = new UpennyRosterResponse(0, "fail");
            log.error("UpennyRosterResource.addSingleTaskListInterface execute Fail!:{}", var12);
        }

        //将结果返回给web-api
        return gson.toJson(response);
    }

    public UpennyRosterResponse getCommonResponse(int resCount) {
        UpennyRosterResponse response = null;
        if (resCount == -1) {
            response = new UpennyRosterResponse(0, "not found rostername");
        } else if (resCount == -2) {
            response = new UpennyRosterResponse(0, "expire time format wrong");
        } else if (resCount > 0) {
            response = new UpennyRosterResponse(1, "success [" + resCount + "]");
        } else {
            response = new UpennyRosterResponse(0, "fail");
        }

        return response;
    }

}
