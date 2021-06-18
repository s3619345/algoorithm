package com.sense.newots.object.entity;


public class DNCTemplate {
    private int id;
    private String dncTemplateName;
    private String dncTemplateDesc;
    private String domain;
    private int type;
    private int status;
    private int serverType;
    private String serverAddr;
    private String creater;
    private String updater;
    private String updateTime;
    private String executeTime;
    private String filterCondition;

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

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getServerType() {
        return this.serverType;
    }

    public void setServerType(int serverType) {
        this.serverType = serverType;
    }

    public String getServerAddr() {
        return this.serverAddr;
    }

    public void setServerAddr(String serverAddr) {
        this.serverAddr = serverAddr;
    }

    public String getCreater() {
        return this.creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getUpdater() {
        return this.updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public String getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getExecuteTime() {
        return this.executeTime;
    }

    public void setExecuteTime(String executeTime) {
        this.executeTime = executeTime;
    }

    public String getFilterCondition() {
        return this.filterCondition;
    }

    public void setFilterCondition(String filterCondition) {
        this.filterCondition = filterCondition;
    }

    public String getDncTemplateName() {
        return this.dncTemplateName;
    }

    public void setDncTemplateName(String dncTemplateName) {
        this.dncTemplateName = dncTemplateName;
    }

    public String getDncTemplateDesc() {
        return this.dncTemplateDesc;
    }

    public void setDncTemplateDesc(String dncTemplateDesc) {
        this.dncTemplateDesc = dncTemplateDesc;
    }
}

