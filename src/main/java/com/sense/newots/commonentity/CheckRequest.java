package com.sense.newots.commonentity;

import com.sense.newots.request.BaseRequest;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class CheckRequest extends BaseRequest {
    private String name;
    private String domain;

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
}
