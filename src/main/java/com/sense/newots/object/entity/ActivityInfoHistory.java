package com.sense.newots.object.entity;


public class ActivityInfoHistory {
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
    private int completeBatchNum;
    private int outCallNum;
    private int unCallNum;
    private int answerCallNum;
    private int dncNum;

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

    public void addRotserNum(int roterNum) {
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

    public int getRosterNum() {
        return this.rosterNum;
    }

    public void setRosterNum(int rosterNum) {
        this.rosterNum = rosterNum;
    }
}
