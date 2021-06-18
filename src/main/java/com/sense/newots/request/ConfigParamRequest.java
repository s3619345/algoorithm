package com.sense.newots.request;

import com.sense.newots.commonentity.PageRequest;

public class ConfigParamRequest extends PageRequest {
    private String paramType;

    public String getParamType() {
        return this.paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }
}