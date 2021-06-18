package com.sense.newots.object.entity;

import lombok.Data;

/**
 * Created by senseinfo on 2020/5/11.
 */
@Data

public class BatchRecord {
    private int id;
    private String batchId;
    private int status;
    private String prefix;
    private int maxCapacity;
    private String trunkGrp;
    private String userName;
    private String addTime;
    private String callStartTime;
}
