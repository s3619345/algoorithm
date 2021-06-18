package com.sense.newots.amq.consumer.queue;

import com.google.gson.Gson;
import com.sense.newots.object.GetBeanUtil;
import com.sense.newots.object.entity.ActivityInfo;
import com.sense.newots.object.entity.RosterInfo;
import com.sense.newots.object.service.ActivityInfoService;
import com.sense.newots.object.service.RosterService;
import com.sense.newots.object.util.TimeUtil;
import com.sense.newots.request.BaseUpennyRosterRequest;
import com.sense.newots.request.UpennyRosterBatchListRequest;
import com.sense.newots.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by senseinfo on 2018/11/12.
 */
@Slf4j
public class RosterInfoAssemble {
    static Gson gson = new Gson();
    private static RosterService rosterDao = GetBeanUtil.getBean(RosterService.class);
    private static ActivityInfoService activityDao = GetBeanUtil.getBean(ActivityInfoService.class);
    public RosterInfoAssemble(){

    }
    public static void rosterInfoPackage(UpennyRosterBatchListRequest failRosterBatchListRequest) {
       log.info("##  upennyRosterBatchListRequest :"+failRosterBatchListRequest.toString());

        List<RosterInfo> rosterInfoList = new ArrayList<>();
        //把api传过来的数据遍历
        String templateName1 = failRosterBatchListRequest.getTemplateCode();
        ActivityInfo aInfo1 = activityDao.findActivityInfoByTemplate(templateName1);
        Iterator resCount = failRosterBatchListRequest.getJobList().iterator();

        if (aInfo1 != null) {
            String domain = aInfo1.getDomain();
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
                    rosterInfo.setCallRound(1);
                    rosterInfo.setActivityName(aInfo1.getName());
                    rosterInfo.setTemplateName(templateName1);
                    rosterInfo.setBatchName(failRosterBatchListRequest.getBatchId().toString());
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
            rosterInfoList.clear();

        }else {
            log.info("模板表没有对应的模板信息");
        }
    }
}