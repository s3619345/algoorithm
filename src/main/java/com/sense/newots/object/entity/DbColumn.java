package com.sense.newots.object.entity;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class DbColumn {
    private String name;
    private String type;
    private String condition;
    private int encrypt;
    private String showName;

    public DbColumn() {
    }

    public DbColumn(String name, String type, String condition) {
        this.name = name;
        this.type = type;
        this.condition = condition;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCondition() {
        return this.condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public int getEncrypt() {
        return this.encrypt;
    }

    public void setEncrypt(int encrypt) {
        this.encrypt = encrypt;
    }

    public String getShowName() {
        return this.showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }
}
