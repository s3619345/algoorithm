package com.sense.newots.object.service;

import com.sense.newots.object.entity.ActivityInfoHistory;

import java.util.List;

/**
 @desc ...
 @date 2021-05-21 10:43:37
 @author szz
 */
public interface ActivityInfoHistoryService {
    int createActivityInfoHistory(ActivityInfoHistory info);

    List<ActivityInfoHistory> getTActivityInfoHistorys(String domain, String activityName);

    int getTActivityInfoHistoryNum(String domain);

    boolean  updateActivityInfoHistory(ActivityInfoHistory info);

    boolean  deleteActivityInfoHistory(ActivityInfoHistory info);
}
