package com.sense.newots.object.entity;


public class ConfigParam {
    private int id;
    private String name;
    private String value;
    private String domain;
    private String paramType;
    private int validLength;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDomain() {
        return this.domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getParamType() {
        return this.paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

    public int getValidLength() {
        return this.validLength;
    }

    public void setValidLength(int validLength) {
        this.validLength = validLength;
    }
}
