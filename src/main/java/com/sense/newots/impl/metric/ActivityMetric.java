package com.sense.newots.impl.metric;


import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class ActivityMetric {
    private String domain;
    private String activityName;
    private String templateName;
    private String startTime;
    private int status;
    private int callrate;
    private int maxCall;
    private String prefix;
    private String trunkGrp;
    private String currentBatch;
    private int currentRound;
    private int roterNum;
    private int batchNum;
    private int completeBatchNum;
    private int rosterNumDay;
    private int completeBatchNumDay;
    private int outCallNum;
    private int unCallNum;
    private int answerCallNum;
    private int dncNum;
    private Map<Integer, Integer> hours_callout;
    private Map<Integer, Integer> hours_callrate;
    private Map<Integer, Integer> hours_callAnswer;

    public ActivityMetric() {
        this.hours_callout = new HashMap();
        this.hours_callrate = new HashMap();
        this.hours_callAnswer = new HashMap();
        for (int i = 0; i < 24; i++) {
            this.hours_callout.put(Integer.valueOf(i), Integer.valueOf(0));
            this.hours_callrate.put(Integer.valueOf(i), Integer.valueOf(0));
            this.hours_callAnswer.put(Integer.valueOf(i), Integer.valueOf(0));
        }
    }

    public String toString() {
        return "roterNum-" + this.roterNum + "|" + "batchNum-" + this.batchNum + "|" + "outCallNum-" + this.outCallNum + "|" +
                "unCallNum-" + this.unCallNum + "|" + "answerCallNum-" + this.answerCallNum + "|" + "dncNum-" + this.dncNum + "|";
    }

    public void clear() {
        this.roterNum = 0;
        this.batchNum = 0;
        this.outCallNum = 0;
        this.unCallNum = 0;
        this.answerCallNum = 0;
        this.dncNum = 0;
        this.rosterNumDay = 0;
        this.completeBatchNumDay = 0;
        this.currentBatch = "";
        this.currentRound = 0;
        this.hours_callout.clear();
        this.hours_callrate.clear();
        this.hours_callAnswer.clear();
    }

    public int getRoterNum() {
        return this.roterNum;
    }

    public void setRoterNum(int roterNum) {
        this.roterNum = roterNum;
    }

    public void addRoterNum(int num) {
        this.roterNum += num;
    }

    public int getBatchNum() {
        return this.batchNum;
    }

    public void setBatchNum(int batchNum) {
        this.batchNum = batchNum;
    }

    public void addBatchNum(int num) {
        this.batchNum += num;
    }

    public int getOutCallNum() {
        return this.outCallNum;
    }

    public void setOutCallNum(int outCallNum) {
        this.outCallNum = outCallNum;
    }

    public void addOutCallNum() {
        this.outCallNum += 1;
        int current_hour = getCurrentHour();
        if (this.hours_callout.get(Integer.valueOf(current_hour)) != null) {
            int current_call = ((Integer) this.hours_callout.get(Integer.valueOf(current_hour))).intValue();
            current_call++;
            this.hours_callout.put(Integer.valueOf(current_hour), Integer.valueOf(current_call));
        } else {
            this.hours_callout.put(Integer.valueOf(current_hour), Integer.valueOf(1));
        }
        this.unCallNum = (this.roterNum - this.outCallNum);
        if (this.unCallNum <= 0)
            this.unCallNum = 0;
    }

    public void setCallRate(int rate) {
        this.callrate = rate;
        int current_hour = getCurrentHour();
        if (this.hours_callrate.get(Integer.valueOf(current_hour)) != null) {
            int current_rate = ((Integer) this.hours_callrate.get(Integer.valueOf(current_hour))).intValue();
            if (rate > current_rate)
                this.hours_callrate.put(Integer.valueOf(current_hour), Integer.valueOf(rate));
        } else {
            this.hours_callrate.put(Integer.valueOf(current_hour), Integer.valueOf(rate));
        }
             /*if (rate > this.maxCall)
              this.maxCall = rate;*/
    }

    public int getUnCallNum() {
        return this.unCallNum;
    }

    public void setUnCallNum(int unCallNum) {
        this.unCallNum = unCallNum;
    }

    public void addUnCallNum() {
        this.unCallNum += 1;
    }

    public int getAnswerCallNum() {
        return this.answerCallNum;
    }

    public void setAnswerCallNum(int answerCallNum) {
        this.answerCallNum = answerCallNum;
    }

    public void addAnswerCallNum() {
        int current_hour = getCurrentHour();
        if (this.hours_callAnswer.get(Integer.valueOf(current_hour)) != null) {
            int current_call = ((Integer) this.hours_callAnswer.get(Integer.valueOf(current_hour))).intValue();
            current_call++;
            this.hours_callAnswer.put(Integer.valueOf(current_hour), Integer.valueOf(current_call));
        } else {
            this.hours_callAnswer.put(Integer.valueOf(current_hour), Integer.valueOf(1));
        }
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

    private int getCurrentHour() {
        Calendar rightNow = Calendar.getInstance();
        int hour = rightNow.get(11);
        return hour;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getTrunkGrp() {
        return trunkGrp;
    }

    public void setTrunkGrp(String trunkGrp) {
        this.trunkGrp = trunkGrp;
    }


    public String getDomain() {
        return this.domain;
    }


    public void setDomain(String domain) {
        this.domain = domain;
    }


    public String getActivityName() {
        return this.activityName;
    }


    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }


    public String getStartTime() {
        return this.startTime;
    }


    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getStatus() {
        return this.status;
    }


    public void setStatus(int status) {
        this.status = status;
    }


    public int getCallrate() {
        return this.callrate;
    }


    public void setCallrate(int callrate) {
        this.callrate = callrate;
    }


    public String getTemplateName() {
        return this.templateName;
    }


    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }


    public int getCompleteBatchNum() {
        return this.completeBatchNum;
    }


    public void setCompleteBatchNum(int completeBatchNum) {
        this.completeBatchNum = completeBatchNum;
    }


    public int getMaxCall() {
        return this.maxCall;
    }


    public void setMaxCall(int maxCall) {
        this.maxCall = maxCall;
    }

    public String getCurrentBatch() {
        return this.currentBatch;
    }


    public void setCurrentBatch(String currentBatch) {
        this.currentBatch = currentBatch;
    }


    public int getCurrentRound() {
        return this.currentRound;
    }


    public void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }


    public int getRosterNumDay() {
        return this.rosterNumDay;
    }


    public void setRosterNumDay(int rosterNumDay) {
        this.rosterNumDay = rosterNumDay;
    }


    public void addRosterNumDay(int rosterNumDay) {
        this.rosterNumDay += rosterNumDay;
    }


    public int getCompleteBatchNumDay() {
        return this.completeBatchNumDay;
    }


    public void setCompleteBatchNumDay(int completeBatchNumDay) {
        this.completeBatchNumDay = completeBatchNumDay;
    }


    public void addCompleteBatchNumDay() {
        this.completeBatchNumDay += 1;
    }
}

