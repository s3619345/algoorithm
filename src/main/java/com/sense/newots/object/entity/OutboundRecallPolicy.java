package com.sense.newots.object.entity;


public class OutboundRecallPolicy {
    private int id;
    private String activityName;
    private int round;
    private String callResult;
    private String phoneNumCol;
    private int callInterval;
    private String processInfo;
    private String domain;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCallResult() {
        return this.callResult;
    }

    public void setCallResult(String callResult) {
        this.callResult = callResult;
    }

    public int getRound() {
        return this.round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public String getProcessInfo() {
        return this.processInfo;
    }

    public void setProcessInfo(String processInfo) {
        this.processInfo = processInfo;
    }

    public String getDomain() {
        return this.domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public int getCallInterval() {
        return this.callInterval;
    }

    public void setCallInterval(int callInterval) {
        this.callInterval = callInterval;
    }

    public String getActivityName() {
        return this.activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getPhoneNumCol() {
        return this.phoneNumCol;
    }

    public void setPhoneNumCol(String phoneNumCol) {
        this.phoneNumCol = phoneNumCol;
    }
}
