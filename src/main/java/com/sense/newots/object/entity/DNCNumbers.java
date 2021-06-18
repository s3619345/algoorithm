package com.sense.newots.object.entity;


import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;


@XmlRootElement
public class DNCNumbers {
    private int id;
    private List<String> phoneNumList;
    private String domain;
    private String dncTemplateId;

    public String getDomain() {
        return this.domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getDncTemplateId() {
        return this.dncTemplateId;
    }

    public void setDncTemplateId(String dncTemplateId) {
        this.dncTemplateId = dncTemplateId;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getPhoneNumList() {
        return this.phoneNumList;
    }

    public void setPhoneNumList(List<String> phoneNumList) {
        this.phoneNumList = phoneNumList;
    }
}

