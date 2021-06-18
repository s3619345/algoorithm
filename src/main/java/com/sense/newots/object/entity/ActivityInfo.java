package com.sense.newots.object.entity;


import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ActivityInfo {
    private int id;
    private String name;
    private String domain;
    private int status;
    private String region;
    private String descb;
    private String policyName;
    private String rosterTemplateName;
    private int maxCapacity;
    private int priority;
    private String localNo;
    private String conditionInfo;
    private String uui;
    private String trunkGrp;
    private String lastUpdateTime;
    private String successRate;
    private String prefix;
    private String beginDatetime;
    private String endDatetime;
    private int activityExecuteType;
    private String activityExecuteTime;
    private String activityBackTime;
    private String activityBackAddr;
    private int activityBackAddrType;
    private String orderType;
    private int completeType;
    private int rosterNum;
    private int batchNum;
    private int completeBatchNum;
    private int outCallNum;
    private int unCallNum;
    private int answerCallNum;
    private int dncNum;
    private List roundList;
    private String currentBatch;
    private int currentRound;
    private String isSMS;
    private String rate;
    private int callRound;
    private String userName;
    private String bid;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public int getCallRound() {
        return callRound;
    }

    public void setCallRound(int callRound) {
        this.callRound = callRound;
    }

    public String getIsSMS() {
        return isSMS;
    }

    public void setIsSMS(String isSMS) {
        this.isSMS = isSMS;
    }

    public String getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(String successRate) {
        this.successRate = successRate;
    }


    public String getName() {
        return this.name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return this.status;
    }


    public void setStatus(int status) {
        this.status = status;
    }

    public String getDomain() {
        return this.domain;
    }


    public void setDomain(String domain) {
        this.domain = domain;
    }


    public int getId() {
        return this.id;
    }


    public void setId(int id) {
        this.id = id;
    }

    public String getRegion() {
        return this.region;
    }

    public void setRegion(String region) {
        this.region = region;
    }


    public String getPolicyName() {
        return this.policyName;
    }


    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }


    public int getMaxCapacity() {
        return this.maxCapacity;
    }


    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }


    public int getPriority() {
        return this.priority;
    }


    public void setPriority(int priority) {
        this.priority = priority;
    }


    public String getLocalNo() {
        return this.localNo;
    }

    public void setLocalNo(String localNo) {
        this.localNo = localNo;
    }


    public String getUui() {
        return this.uui;
    }


    public void setUui(String uui) {
        this.uui = uui;
    }


    public String getTrunkGrp() {
        return this.trunkGrp;
    }

    public void setTrunkGrp(String trunkGrp) {
        this.trunkGrp = trunkGrp;
    }


    public String getLastUpdateTime() {
        return this.lastUpdateTime;
    }


    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }


    public String getPrefix() {
        return this.prefix;
    }


    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getBeginDatetime() {
        return this.beginDatetime;
    }

    public void setBeginDatetime(String beginDatetime) {
        this.beginDatetime = beginDatetime;
    }


    public String getEndDatetime() {
        return this.endDatetime;
    }


    public void setEndDatetime(String endDatetime) {
        this.endDatetime = endDatetime;
    }


    public int getActivityExecuteType() {
        return this.activityExecuteType;
    }

    public void setActivityExecuteType(int activityExecuteType) {
        this.activityExecuteType = activityExecuteType;
    }

    public String getActivityExecuteTime() {
        return this.activityExecuteTime;
    }

    public void setActivityExecuteTime(String activityExecuteTime) {
        this.activityExecuteTime = activityExecuteTime;
    }

    public String getActivityBackTime() {
        return this.activityBackTime;
    }


    public void setActivityBackTime(String activityBackTime) {
        this.activityBackTime = activityBackTime;
    }


    public String getActivityBackAddr() {
        return this.activityBackAddr;
    }


    public void setActivityBackAddr(String activityBackAddr) {
        this.activityBackAddr = activityBackAddr;
    }


    public int getActivityBackAddrType() {
        return this.activityBackAddrType;
    }


    public void setActivityBackAddrType(int activityBackAddrType) {
        this.activityBackAddrType = activityBackAddrType;
    }


    public String getOrderType() {
        return this.orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }


    public int getCompleteType() {
        return this.completeType;
    }


    public void setCompleteType(int completeType) {
        this.completeType = completeType;
    }


    public String getDescb() {
        return this.descb;
    }


    public void setDescb(String descb) {
        this.descb = descb;
    }

    public String getConditionInfo() {
        return this.conditionInfo;
    }


    public void setConditionInfo(String conditionInfo) {
        this.conditionInfo = conditionInfo;
    }


    public String getRosterTemplateName() {
        return this.rosterTemplateName;
    }

    public void setRosterTemplateName(String rosterTemplateName) {
        this.rosterTemplateName = rosterTemplateName;
    }


    public int getRosterNum() {
        return this.rosterNum;
    }

    public void setRosterNum(int roterNum) {
        this.rosterNum = roterNum;
    }


    public void addRosterNum(int roterNum) {
        this.rosterNum += roterNum;
    }


    public int getCompleteBatchNum() {
        return this.completeBatchNum;
    }


    public void setCompleteBatchNum(int completeBatchNum) {
        this.completeBatchNum = completeBatchNum;
    }


    public void addCompleteBatchNum() {
        this.completeBatchNum += 1;
    }


    public int getOutCallNum() {
        return this.outCallNum;
    }


    public void setOutCallNum(int outCallNum) {
        this.outCallNum = outCallNum;
    }


    public void addOutCallNum(int outCallNum) {
        this.outCallNum += outCallNum;
    }


    public int getUnCallNum() {
        return this.unCallNum;
    }


    public void setUnCallNum(int unCallNum) {
        this.unCallNum = unCallNum;
    }

    public void adUnCallNum(int unCallNum) {
        this.unCallNum += unCallNum;
    }


    public int getAnswerCallNum() {
        return this.answerCallNum;
    }


    public void setAnswerCallNum(int answerCallNum) {
        this.answerCallNum = answerCallNum;
    }


    public void addAnswerCallNum(int answerCallNum) {
        this.answerCallNum += answerCallNum;
    }


    public int getDncNum() {
        return this.dncNum;
    }

    public void setDncNum(int dncNum) {
        this.dncNum = dncNum;
    }

    public void addDncNum(int dncNum) {
        this.dncNum += dncNum;
    }

    public int getBatchNum() {
        return this.batchNum;
    }


    public void addBatchNum() {
        this.batchNum += 1;
    }

    public void setBatchNum(int batchNum) {
        this.batchNum = batchNum;
    }

    public String getCurrentBatch() {
        return this.currentBatch;
    }

    public void setCurrentBatch(String currentBatch) {
        this.currentBatch = currentBatch;
    }

    public int getCurrentRound() {
        return this.currentRound;
    }


    public void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }


    public List getRoundList() {
        return this.roundList;
    }

    public void setRoundList(List roundList) {
        this.roundList = roundList;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    @Override
    public String toString() {
        return "ActivityInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", domain='" + domain + '\'' +
                ", status=" + status +
                ", region='" + region + '\'' +
                ", descb='" + descb + '\'' +
                ", policyName='" + policyName + '\'' +
                ", rosterTemplateName='" + rosterTemplateName + '\'' +
                ", maxCapacity=" + maxCapacity +
                ", priority=" + priority +
                ", localNo='" + localNo + '\'' +
                ", conditionInfo='" + conditionInfo + '\'' +
                ", uui='" + uui + '\'' +
                ", trunkGrp='" + trunkGrp + '\'' +
                ", lastUpdateTime='" + lastUpdateTime + '\'' +
                ", successRate='" + successRate + '\'' +
                ", prefix='" + prefix + '\'' +
                ", beginDatetime='" + beginDatetime + '\'' +
                ", endDatetime='" + endDatetime + '\'' +
                ", activityExecuteType=" + activityExecuteType +
                ", activityExecuteTime='" + activityExecuteTime + '\'' +
                ", activityBackTime='" + activityBackTime + '\'' +
                ", activityBackAddr='" + activityBackAddr + '\'' +
                ", activityBackAddrType=" + activityBackAddrType +
                ", orderType='" + orderType + '\'' +
                ", completeType=" + completeType +
                ", rosterNum=" + rosterNum +
                ", batchNum=" + batchNum +
                ", completeBatchNum=" + completeBatchNum +
                ", outCallNum=" + outCallNum +
                ", unCallNum=" + unCallNum +
                ", answerCallNum=" + answerCallNum +
                ", dncNum=" + dncNum +
                ", roundList=" + roundList +
                ", currentBatch='" + currentBatch + '\'' +
                ", currentRound=" + currentRound +
                ", isSMS='" + isSMS + '\'' +
                ", rate='" + rate + '\'' +
                ", callRound=" + callRound +
                ", userName='" + userName + '\'' +
                ", bid='" + bid + '\'' +
                '}';
    }

}
