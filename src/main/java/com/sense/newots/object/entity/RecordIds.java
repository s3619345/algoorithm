package com.sense.newots.object.entity;


import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;


@XmlRootElement
public class RecordIds {
    private List<String> ids;
    private String domain;

    public String getDomain() {
        return this.domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public List<String> getIds() {
        return this.ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }
}

