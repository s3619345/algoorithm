package com.sense.newots.request;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BaseUpennyRosterRequest extends BaseRequest {

    private String jobId;
    private Long phone;
    private UserInfoModel jobData;
    private String callee;
    private String prefix;
    private String operators;

    @Override
    public String toString() {
        return "BaseUpennyRosterRequest{" +
                "jobId='" + jobId + '\'' +
                ", phone=" + phone +
                ", jobData=" + jobData +
                ", callee='" + callee + '\'' +
                ", prefix='" + prefix + '\'' +
                ", operators='" + operators + '\'' +
                '}';
    }

    public String getCallee() {
        return callee;
    }

    public void setCallee(String callee) {
        this.callee = callee;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getOperators() {
        return operators;
    }

    public void setOperators(String operators) {
        this.operators = operators;
    }


    public String getJobId() {
        return this.jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public Long getPhone() {
        return this.phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public UserInfoModel getJobData() {
        return this.jobData;
    }

    public void setJobData(UserInfoModel jobData) {
        this.jobData = jobData;
    }
}