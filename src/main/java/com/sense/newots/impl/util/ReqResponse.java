package com.sense.newots.impl.util;


public class ReqResponse {
    private int code;
    private String errMsg;
    private String callId;

    public String toString() {
        return "code:" + this.code + " | " + "errMsg:" + this.errMsg + " | " + "callId:" + this.callId;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrMsg() {
        return this.errMsg;
    }


    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }


    public String getCallId() {
        return this.callId;
    }

    public void setCallId(String callId) {
        this.callId = callId;
    }
}

