package com.sense.newots.object.entity;
public class TemplateOperationRecord {
    private int id;
    private String userName;
    private String template;
    private String status;
    private String prefix;
    private String maxCapacity;
    private String trunkGrp;

    public String getIsSMS() {
        return isSMS;
    }

    public void setIsSMS(String isSMS) {
        this.isSMS = isSMS;
    }

    private String isSMS;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(String maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public String getTrunkGrp() {
        return trunkGrp;
    }

    public void setTrunkGrp(String trunkGrp) {
        this.trunkGrp = trunkGrp;
    }
}