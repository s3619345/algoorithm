package com.sense.newots.object.entity;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true) // 可以链式调用 setter
@Data
public class RosterInfo {
    private int id;
    private String jobId;
    private String phoneNum1;
    private String phoneNum2;
    private String phoneNum3;
    private String phoneNum4;
    private String phoneNum5;
    private String lastname;
    private String firstname;
    private int age;
    private String sex;
    private String customerId;
    private String address;
    private String email;
    private String cardType;
    private String cardNum;
    private String customFields;
    private String createTime;
    private int callRound = 1;
    private String callId;
    private String callee;
    private String makeCallTime;
    private String callAnswerTime;
    private String callEndTime;
    private String callResult;
    private int callTime;
    private int resultCode;
    private int status;
    private String batchName;
    private String templateName;
    private String activityName;
    private String domain;
    private String prefix;
    private String operators;
}
