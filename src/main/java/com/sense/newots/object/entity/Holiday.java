package com.sense.newots.object.entity;


public class Holiday {
    private int id;
    private String startDate;
    private String endDate;
    private int holidayType;
    private String domain;

    public String getDomain() {
        return this.domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return this.endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getHolidayType() {
        return this.holidayType;
    }

    public void setHolidayType(int holidayType) {
        this.holidayType = holidayType;
    }
}

