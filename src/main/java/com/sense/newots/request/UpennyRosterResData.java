package com.sense.newots.request;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UpennyRosterResData {
    private String receivedTime = "" + System.currentTimeMillis();

    public UpennyRosterResData() {
    }

    public String getReceivedTime() {
        return this.receivedTime;
    }

    public void setReceivedTime(String receivedTime) {
        this.receivedTime = receivedTime;
    }
}
