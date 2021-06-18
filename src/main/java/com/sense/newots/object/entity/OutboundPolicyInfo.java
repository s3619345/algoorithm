package com.sense.newots.object.entity;


import org.springframework.stereotype.Component;

import java.util.List;

@Component


public class OutboundPolicyInfo {
    private int id;
    private String name;
    private String domain;
    private String descb;
    private List<String> timeRange;
    private String timeRangeStr;
    private List<String> callResultList;
    private String resProcessStr;
    private int policyType;
    private int workDayType;
    private String callAnswerStep;

    public OutboundPolicyInfo() {
    }


    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDomain() {
        return this.domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getDescb() {
        return this.descb;
    }

    public void setDescb(String descb) {
        this.descb = descb;
    }

    public int getPolicyType() {
        return this.policyType;
    }

    public void setPolicyType(int policyType) {
        this.policyType = policyType;
    }

    public int getWorkDayType() {
        return this.workDayType;
    }

    public void setWorkDayType(int workDayType) {
        this.workDayType = workDayType;
    }

    public String getCallAnswerStep() {
        return this.callAnswerStep;
    }

    public void setCallAnswerStep(String callAnswerStep) {
        this.callAnswerStep = callAnswerStep;
    }

    public String getTimeRangeStr() {
        return this.timeRangeStr;
    }

    public void setTimeRangeStr(String timeRangeStr) {
        this.timeRangeStr = timeRangeStr;
    }

    public String getResProcessStr() {
        return this.resProcessStr;
    }

    public void setResProcessStr(String resProcessStr) {
        this.resProcessStr = resProcessStr;
    }

    public List<String> getTimeRange() {
        return this.timeRange;
    }

    public void setTimeRange(List<String> timeRange) {
        this.timeRange = timeRange;
    }

    public List<String> getCallResultList() {
        return this.callResultList;
    }

    public void setCallResultList(List<String> callResultList) {
        this.callResultList = callResultList;
    }
}

