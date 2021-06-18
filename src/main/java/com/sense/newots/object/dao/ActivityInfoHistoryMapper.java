package com.sense.newots.object.dao;


import com.sense.newots.object.entity.ActivityInfoHistory;

import java.util.List;

/**
 @desc ...
 @date 2021-05-21 10:42:04
 @author szz
 */
public interface ActivityInfoHistoryMapper {
    int createActivityInfoHistory(ActivityInfoHistory info);

    List<ActivityInfoHistory> getTActivityInfoHistorys(String domain, String activityName);

    int getTActivityInfoHistoryNum(String domain);

    int  updateActivityInfoHistory(ActivityInfoHistory info);

    int  deleteActivityInfoHistory(ActivityInfoHistory info);
}
