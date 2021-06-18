package com.sense.newots.commonentity;

import lombok.Data;

/**
 * Created by Administrator on 2018/6/4 0004.
 */
@Data
public class CronEntity {
    private String batchId;
    private String jobName;
    private String groupName;
    private String nextFireTime;
    private int state;//**STATE_BLOCKED 4 阻塞  STATE_COMPLETE 2 完成  STATE_ERROR 3 错误 STATE_NONE -1 不存在 STATE_NORMAL 0 正常 STATE_PAUSED 1 暂停**

    @Override
    public String toString() {
        return "CronEntity{" +
                "batchId='" + batchId + '\'' +
                ", jobName='" + jobName + '\'' +
                ", groupName='" + groupName + '\'' +
                ", nextFireTime='" + nextFireTime + '\'' +
                ", state=" + state +
                '}';
    }
}
