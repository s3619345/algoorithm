package com.sense.newots.request;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Accessors(chain = true) // 可以链式调用 setter
@Data
public class MakeCall {
    private String uui;
    private String caller;
    private String callee;
    //private String agent;
    private String rosterinfoId;
    private String activityName;
    private String batchName;
    private String trunkGrp;
    private String ani;
    private int round;
    private String domain;

}

