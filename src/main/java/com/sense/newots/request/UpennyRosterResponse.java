package com.sense.newots.request;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class UpennyRosterResponse {
    private int returnCode;
    private String returnMsg;
    private Object data;

    public UpennyRosterResponse() {
    }

    public UpennyRosterResponse(int returnCode, String returnMsg) {
        this.returnCode = returnCode;
        this.returnMsg = returnMsg;
        this.data = new UpennyRosterResData();
    }

    public UpennyRosterResponse(int returnCode, String returnMsg, Object data) {
        this.returnCode = returnCode;
        this.returnMsg = returnMsg;
        this.data = data;
    }

    public int getReturnCode() {
        return this.returnCode;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return this.returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
