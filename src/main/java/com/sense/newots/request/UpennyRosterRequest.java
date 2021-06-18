package com.sense.newots.request;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class UpennyRosterRequest extends BaseUpennyRosterRequest {
    private String templateCode;
    private String callbackUrl;

    public String getTemplateCode() {
        return this.templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getCallbackUrl() {
        return this.callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }
}
