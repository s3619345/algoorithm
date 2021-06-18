package com.sense.newots.request;

public class RosterResultW {
    private String callId;
    private String callTime;
    private String answerTime;
    private String hangupTime;
    private String resultType;
    private String domain;
    private String result;
    private int resultCode;
    private String rosterinfo_id;
    private String batch_id;
    private String activity_id;
    private int round;
    private String email;
    private String ivrResult;

    @Override
    public String toString() {
        return "RosterResultW{" +
                "callId='" + callId + '\'' +
                ", callTime='" + callTime + '\'' +
                ", answerTime='" + answerTime + '\'' +
                ", hangupTime='" + hangupTime + '\'' +
                ", resultType='" + resultType + '\'' +
                ", domain='" + domain + '\'' +
                ", result='" + result + '\'' +
                ", resultCode=" + resultCode +
                ", rosterinfo_id='" + rosterinfo_id + '\'' +
                ", batch_id='" + batch_id + '\'' +
                ", activity_id='" + activity_id + '\'' +
                ", round=" + round +
                ", email='" + email + '\'' +
                ", ivrResult='" + ivrResult + '\'' +
                '}';
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCallId() {
        return callId;
    }

    public void setCallId(String callId) {
        this.callId = callId;
    }

    public String getCallTime() {
        return callTime;
    }

    public void setCallTime(String callTime) {
        this.callTime = callTime;
    }

    public String getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(String answerTime) {
        this.answerTime = answerTime;
    }

    public String getHangupTime() {
        return hangupTime;
    }

    public void setHangupTime(String hangupTime) {
        this.hangupTime = hangupTime;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getRosterinfo_id() {
        return rosterinfo_id;
    }

    public void setRosterinfo_id(String rosterinfo_id) {
        this.rosterinfo_id = rosterinfo_id;
    }

    public String getBatch_id() {
        return batch_id;
    }

    public void setBatch_id(String batch_id) {
        this.batch_id = batch_id;
    }

    public String getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(String activity_id) {
        this.activity_id = activity_id;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public String getIvrResult() {
        return ivrResult;
    }

    public void setIvrResult(String ivrResult) {
        this.ivrResult = ivrResult;
    }

}