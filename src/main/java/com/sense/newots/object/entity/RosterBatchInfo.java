package com.sense.newots.object.entity;

import lombok.Data;


@Data

public class RosterBatchInfo {
    private String id;
    private int status;
    private String batchId;
    private int activityId;
    private String activityName;
    private String templateName;
    private String domain;
    private String createTime;
    private String planStartTime;

    private String repeatCronExpression;
    private Integer repeatCount;
    private Integer currentFinishedRound;
    private Integer cronIdentification;
    private Integer rerunRound;

    private String startTime;
    private String completeTime;
    private int roterNum;
    private int outCallNum;
    private int unCallNum;
    private int answerCallNum;
    private int dncNum;
    private int callRound;
    private int fail1Num;
    private int fail2Num;
    private int fail3Num;
    private int fail4Num;
    private int fail5Num;
    private int failOtherNum;

    @Override
    public String toString() {
        return "RosterBatchInfo{" +
                "id='" + id + '\'' +
                ", status=" + status +
                ", batchId='" + batchId + '\'' +
                ", activityId=" + activityId +
                ", activityName='" + activityName + '\'' +
                ", templateName='" + templateName + '\'' +
                ", domain='" + domain + '\'' +
                ", createTime='" + createTime + '\'' +
                ", planStartTime='" + planStartTime + '\'' +
                ", repeatCronExpression='" + repeatCronExpression + '\'' +
                ", repeatCount=" + repeatCount +
                ", currentFinishedRound=" + currentFinishedRound +
                ", cronIdentification=" + cronIdentification +
                ", rerunRound=" + rerunRound +
                ", startTime='" + startTime + '\'' +
                ", completeTime='" + completeTime + '\'' +
                ", roterNum=" + roterNum +
                ", outCallNum=" + outCallNum +
                ", unCallNum=" + unCallNum +
                ", answerCallNum=" + answerCallNum +
                ", dncNum=" + dncNum +
                ", callRound=" + callRound +
                ", fail1Num=" + fail1Num +
                ", fail2Num=" + fail2Num +
                ", fail3Num=" + fail3Num +
                ", fail4Num=" + fail4Num +
                ", fail5Num=" + fail5Num +
                ", failOtherNum=" + failOtherNum +
                '}';
    }

    public String getBatchId() {
        return this.batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTemplateName() {
        return this.templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getDomain() {
        return this.domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getCompleteTime() {
        return this.completeTime;
    }

    public void setCompleteTime(String completeTime) {
        this.completeTime = completeTime;
    }

    public int getRoterNum() {
        return this.roterNum;
    }

    public void setRoterNum(int roterNum) {
        this.roterNum = roterNum;
    }

    public int getOutCallNum() {
        return this.outCallNum;
    }

    public void setOutCallNum(int outCallNum) {
        this.outCallNum = outCallNum;
    }

    public int getUnCallNum() {
        return this.unCallNum;
    }

    public void setUnCallNum(int unCallNum) {
        this.unCallNum = unCallNum;
    }

    public int getAnswerCallNum() {
        return this.answerCallNum;
    }

    public void setAnswerCallNum(int answerCallNum) {
        this.answerCallNum = answerCallNum;
    }

    public int getDncNum() {
        return this.dncNum;
    }

    public void setDncNum(int dncNum) {
        this.dncNum = dncNum;
    }

    public int getFail1Num() {
        return this.fail1Num;
    }

    public void setFail1Num(int fail1Num) {
        this.fail1Num = fail1Num;
    }

    public int getFail2Num() {
        return this.fail2Num;
    }

    public void setFail2Num(int fail2Num) {
        this.fail2Num = fail2Num;
    }

    public int getFail3Num() {
        return this.fail3Num;
    }

    public void setFail3Num(int fail3Num) {
        this.fail3Num = fail3Num;
    }

    public int getFail4Num() {
        return this.fail4Num;
    }

    public void setFail4Num(int fail4Num) {
        this.fail4Num = fail4Num;
    }

    public int getFailOtherNum() {
        return this.failOtherNum;
    }

    public void setFailOtherNum(int failOtherNum) {
        this.failOtherNum = failOtherNum;
        int fail = this.outCallNum - this.answerCallNum - this.fail1Num - this.fail2Num -
                this.fail3Num - this.fail4Num - this.fail5Num - failOtherNum;
        if (fail > 0)
            {
            setFail4Num(fail);
        }
    }

    public int getFail5Num() {
        return this.fail5Num;
    }

    public void setFail5Num(int fail5Num) {
        this.fail5Num = fail5Num;
    }

    public void addFail5Num() {
        this.fail5Num += 1;
    }

    public String getActivityName() {
        return this.activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public int getActivityId() {
        return this.activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getCallRound() {
        return this.callRound;
    }

    public void setCallRound(int callRound) {
        this.callRound = callRound;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Integer getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount(Integer repeatCount) {
        this.repeatCount = repeatCount;
    }

    public Integer getCurrentFinishedRound() {
        return currentFinishedRound;
    }

    public void setCurrentFinishedRound(Integer currentFinishedRound) {
        this.currentFinishedRound = currentFinishedRound;
    }

    public Integer getCronIdentification() {
        return cronIdentification;
    }

    public void setCronIdentification(Integer cronIdentification) {
        this.cronIdentification = cronIdentification;
    }
}
