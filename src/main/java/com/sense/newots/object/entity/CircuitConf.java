package com.sense.newots.object.entity;

import lombok.Data;


/**
 * Created by senseinfo on 2019/3/11.
 */
@Data

public class CircuitConf {

    private int id;
    private String activityName;
    private String activityDesc;
    private String startTime;
    private String endTime;
    private String prefix;
    private int isRun;
    private String createTime;

}
