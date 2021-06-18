package com.sense.newots.request;

public class ActivityRequest extends BaseRequest {
    private String domain;
    private String activityName;
    private String activityBeginTime;
    private String activityEndTime;
    private int offset = 0;

    private int pageSize = 10;

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

    public int getOffset() {
        return this.offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}

