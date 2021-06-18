package com.sense.newots.object.entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RosterTemplatePreparedField {
    private int id;
    private String prepareFieldName;
    private String prepareFieldDesc;
    private String prepareFieldType;
    private int status;
    private int beAllowedEncrypt;
    private String creater;
    private String updater;
    private String createTime;
    private String updateTime;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrepareFieldName() {
        return this.prepareFieldName;
    }

    public void setPrepareFieldName(String prepareFieldName) {
        this.prepareFieldName = prepareFieldName;
    }

    public String getPrepareFieldDesc() {
        return this.prepareFieldDesc;
    }

    public void setPrepareFieldDesc(String prepareFieldDesc) {
        this.prepareFieldDesc = prepareFieldDesc;
    }

    public String getPrepareFieldType() {
        return this.prepareFieldType;
    }

    public void setPrepareFieldType(String prepareFieldType) {
        this.prepareFieldType = prepareFieldType;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getBeAllowedEncrypt() {
        return this.beAllowedEncrypt;
    }

    public void setBeAllowedEncrypt(int beAllowedEncrypt) {
        this.beAllowedEncrypt = beAllowedEncrypt;
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

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
