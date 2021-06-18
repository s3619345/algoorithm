package com.sense.newots.object.entity;

public class TrunkNumberPool {
    private int id;
    private String name;
    private String descb;
    private String domain;
    private int phonenumCount;
    private int trunkChannels;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDomain() {
        return this.domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescb() {
        return this.descb;
    }

    public void setDescb(String descb) {
        this.descb = descb;
    }

    public int getTrunkChannels() {
        return this.trunkChannels;
    }

    public void setTrunkChannels(int trunkChannels) {
        this.trunkChannels = trunkChannels;
    }

    public int getPhonenumCount() {
        return this.phonenumCount;
    }

    public void setPhonenumCount(int phonenumCount) {
        this.phonenumCount = phonenumCount;
    }
}
