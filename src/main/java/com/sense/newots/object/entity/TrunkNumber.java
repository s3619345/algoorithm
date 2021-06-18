package com.sense.newots.object.entity;

import org.springframework.stereotype.Repository;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Repository
public class TrunkNumber {
    private int id;
    private String displayNum;
    private String trunkGrp;
    private String domain;
    private String validTime;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTrunkGrp() {
        return this.trunkGrp;
    }

    public void setTrunkGrp(String trunkGrp) {
        this.trunkGrp = trunkGrp;
    }

    public String getDomain() {
        return this.domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getDisplayNum() {
        return this.displayNum;
    }

    public void setDisplayNum(String displayNum) {
        this.displayNum = displayNum;
    }

    public String getValidTime() {
        return validTime;
    }

    public void setValidTime(String validTime) {
        this.validTime = validTime;
    }
}