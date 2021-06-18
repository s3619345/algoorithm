package com.sense.newots.request;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Data
@XmlRootElement
public class UpennyRosterBatchListRequest extends BaseRequest {
    private String batchId;
    private String templateCode;
    private String planStartTime;
    private String userName;
    private String repeatCronExpression;
    private int repeatCount;//重播次数
    private int currentFinishedRound;
    private List<BaseUpennyRosterRequest> jobList;
    private Integer groupTotal;//分组数
    private Integer rosterTotal;//api传过来的电话总数


    public int getCurrentFinishedRound() {
        return currentFinishedRound;
    }

    public void setCurrentFinishedRound(int currentFinishedRound) {
        this.currentFinishedRound = currentFinishedRound;
    }

    public String getBatchId() {
        return this.batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getTemplateCode() {
        return this.templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public List<BaseUpennyRosterRequest> getJobList() {
        return this.jobList;
    }

    public void setJobList(List<BaseUpennyRosterRequest> jobList) {
        this.jobList = jobList;
    }

    public String getPlanStartTime() {
        return planStartTime;
    }

    public void setPlanStartTime(String planStartTime) {
        this.planStartTime = planStartTime;
    }

    public String getRepeatCronExpression() {
        return repeatCronExpression;
    }

    public void setRepeatCronExpression(String repeatCronExpression) {
        this.repeatCronExpression = repeatCronExpression;
    }

    public int getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount(int repeatCount) {
        this.repeatCount = repeatCount;
    }


}