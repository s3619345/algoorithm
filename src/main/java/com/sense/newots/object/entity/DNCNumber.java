package com.sense.newots.object.entity;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class DNCNumber {
    private int id;
    private String phoneNum;
    private String domain;
    private String dncTemplateId;
    private int status;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoneNum() {
        return this.phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getDomain() {
        return this.domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDncTemplateId() {
        return this.dncTemplateId;
    }

    public void setDncTemplateId(String dncTemplateId) {
        this.dncTemplateId = dncTemplateId;
    }
}

