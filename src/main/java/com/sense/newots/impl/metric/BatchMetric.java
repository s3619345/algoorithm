
package com.sense.newots.impl.metric;

import java.util.Map;

public class BatchMetric {
    private String activityName;
    private String batchName;
    private String domain;
    private int roterNum;
    private int outCallNum;
    private int unCallNum;
    private int answerCallNum;
    private int dncNum;
    private int status;
    private String startTime;
    private int round;
    private int fail1Num;
    private int fail2Num;
    private int fail3Num;
    private int fail4Num;
    private int fail5Num;
    private int failOtherNum;
    private Map<String, String> ivrResultNum;


    public String toString() {
        return "roterNum-" + this.roterNum + "|" +
                "outCallNum-" + this.outCallNum + "|" +
                "unCallNum-" + this.unCallNum + "|" +
                "answerCallNum-" + this.answerCallNum + "|" +
                "空号-" + this.fail1Num + "|" +
                "关机-" + this.fail2Num + "|" +
                "用户正忙-" + this.fail3Num + "|" +
                "振铃没接听-" + this.fail4Num + "|" +
                "停机-" + getFail5Num() + "|" +
                "其它-" + this.failOtherNum + "|" +
                "dncNum-" + this.dncNum + "|";
    }

    public void clear() {
        this.roterNum = 0;
        this.outCallNum = 0;
        this.unCallNum = 0;
        this.answerCallNum = 0;
        this.dncNum = 0;
        this.fail1Num = 0;
        this.fail2Num = 0;
        this.fail3Num = 0;
        this.fail4Num = 0;
        setFail5Num(0);
        this.failOtherNum = 0;
        ivrResultNum.clear();
    }

    public int getRoterNum() {
        return this.roterNum;
    }


    public void setRoterNum(int roterNum) {
        this.roterNum = roterNum;
        this.unCallNum = (roterNum - this.outCallNum);
    }


    public int getOutCallNum() {
        return this.outCallNum;
    }


    public void setOutCallNum(int outCallNum) {
        this.outCallNum = outCallNum;
        this.unCallNum = (this.roterNum - outCallNum);
        if (this.unCallNum <= 0)
            this.unCallNum = 0;
    }


    public void addOutCallNum() {

        this.outCallNum += 1;
        this.unCallNum = (this.roterNum - this.outCallNum);
        if (this.unCallNum <= 0)
            this.unCallNum = 0;

    }

    public int getUnCallNum() {
        return this.unCallNum;
    }


    public void setUnCallNum(int unCallNum) {
        this.unCallNum = unCallNum;
    }

    public int getAnswerCallNum() {
        return this.answerCallNum;
    }


    public void setAnswerCallNum(int answerCallNum) {
        this.answerCallNum = answerCallNum;
    }


    public void addAnswerCallNum() {
        this.answerCallNum += 1;
    }


    public int getDncNum() {
        return this.dncNum;
    }

    public void setDncNum(int dncNum) {
        this.dncNum = dncNum;
    }


    public void addDncNum() {
        this.dncNum += 1;
    }


    public int getFail1Num() {
        return this.fail1Num;
    }


    public void setFail1Num(int fail1Num) {
        this.fail1Num = fail1Num;
    }

    public void addFail1Num() {
        this.fail1Num += 1;
    }


    public int getFail2Num() {
        return this.fail2Num;
    }


    public void setFail2Num(int fail2Num) {
        this.fail2Num = fail2Num;
    }

    public void addFail2Num() {
        this.fail2Num += 1;
    }


    public int getFail3Num() {
        return this.fail3Num;
    }


    public void setFail3Num(int fail3Num) {
        this.fail3Num = fail3Num;
    }


    public void addFail3Num() {
        this.fail3Num += 1;
    }


    public int getFail4Num() {
        return this.fail4Num;
    }


    public void setFail4Num(int fail4Num) {
        this.fail4Num = fail4Num;
    }


    public void addFail4Num() {
        this.fail4Num += 1;
    }

    public int getFailOtherNum() {
        return this.failOtherNum;
    }

    public void setFailOtherNum(int failOtherNum) {
        this.failOtherNum = failOtherNum;
    }


    public void addFailOtherNum() {
        this.failOtherNum += 1;
    }


    public String getActivityName() {
        return this.activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }


    public String getBatchName() {
        return this.batchName;
    }


    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }


    public String getDomain() {
        return this.domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }


    public int getFail5Num() {
        return this.fail5Num;
    }


    public void setFail5Num(int fail5Num) {
        this.fail5Num = fail5Num;
    }


    public void addFail5Num() {
        this.fail5Num += 1;
    }


    public int getStatus() {
        return this.status;
    }


    public void setStatus(int status) {
        this.status = status;
    }


    public String getStartTime() {
        return this.startTime;
    }


    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }


    public int getRound() {
        return this.round;
    }


    public void setRound(int round) {
        this.round = round;
    }

    public Map<String, String> getIvrResultNum() {
        return ivrResultNum;
    }

    public void setIvrResultNum(Map<String, String> ivrResultNum) {
        this.ivrResultNum = ivrResultNum;
    }
}

