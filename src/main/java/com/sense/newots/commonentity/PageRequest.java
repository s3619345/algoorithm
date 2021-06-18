package com.sense.newots.commonentity;

import com.sense.newots.request.BaseRequest;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class PageRequest extends BaseRequest {
    private int startPage;
    private int pageNum;
    private String templateName;
    private String activityName;
    private String phoneNum;
    private String batchName;
    private String policyName;
    private String activityBeginTime;
    private String activityEndTime;
    private String poolName;
    private String dncTemplateId;
    private String activityStatus;
    private String domain;
    private String corp;
    private String activityId;
    private String prefix;
    private String isRun;
    private String bid;

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getIsRun() {
        return isRun;
    }

    public void setIsRun(String isRun) {
        this.isRun = isRun;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getCorp() {
        return corp;
    }

    public void setCorp(String corp) {
        this.corp = corp;
    }


    public int getStartPage() {
        return this.startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }


    public int getPageNum() {
        return this.pageNum;
    }


    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }


    public String getDomain() {
        return this.domain;
    }


    public void setDomain(String domain) {
        this.domain = domain;
    }


    public String getActivityName() {
        return this.activityName;
    }


    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }


    public String getActivityBeginTime() {
        return this.activityBeginTime;
    }


    public void setActivityBeginTime(String activityBeginTime) {
        this.activityBeginTime = activityBeginTime;
    }

    public String getActivityEndTime() {
        return this.activityEndTime;
    }


    public void setActivityEndTime(String activityEndTime) {
        this.activityEndTime = activityEndTime;
    }


    public String getPolicyName() {
        return this.policyName;
    }


    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }


    public String getActivityStatus() {
        return this.activityStatus;
    }


    public void setActivityStatus(String activityStatus) {
        this.activityStatus = activityStatus;
    }


    public String getTemplateName() {
        return this.templateName;
    }


    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }


    public String getPoolName() {
        return this.poolName;
    }


    public void setPoolName(String poolName) {
        this.poolName = poolName;
    }


    public String getPhoneNum() {
        return this.phoneNum;
    }


    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }


    public String getBatchName() {
        return this.batchName;
    }


    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }


    public String getDncTemplateId() {
        return this.dncTemplateId;
    }


    public void setDncTemplateId(String dncTemplateId) {
        this.dncTemplateId = dncTemplateId;
    }
}

