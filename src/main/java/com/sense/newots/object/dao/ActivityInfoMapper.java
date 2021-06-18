package com.sense.newots.object.dao;


import com.sense.newots.object.entity.ActivityInfo;

import java.util.List;
import java.util.Map;

/**
 @desc ...
 @date 2021-05-20 10:58:59
 @author szz
 */

public interface ActivityInfoMapper {
    List<ActivityInfo> selectActivityInfo();

    int updateActivityInfo(ActivityInfo info);

    ActivityInfo findActivityInfoByTemplate(String name);

    Integer clickCount(String bid, String startTime, String endTime);

    ActivityInfo getTActivityInfos(String domain, String templateName);

    List<ActivityInfo> getStoppedActivityInfos();

    int deleteActivityInfo(ActivityInfo info);

    int updatePrefix(String prefix, String name);

    List<ActivityInfo> getTActivityInfosList(String activityStatus, String prefix, String activityName);

    int getTActivityInfoNum(String domain);

    int createActivityInfo(ActivityInfo template);

    ActivityInfo findById(Integer id);

    int deleteActivityInfoById(Integer id);

    int checkName(String name, String domain);

    int sendCount(String templateName, String startTime, String endTime);

    List<Map<Object, Object>> findClick(String templateName, String startTime, String endTime);

    List<Map<Object, Object>> findReptile(String templateName, String startTime, String endTime);

    int updateRosterNum(int rosterNum, String name);
}
