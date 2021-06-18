package com.sense.newots.object.service;


import com.sense.newots.object.entity.ActivityInfo;

import java.util.List;
import java.util.Map;

/**
 @desc ...
 @date 2021-05-20 11:07:04
 @author szz
 */
public interface ActivityInfoService {
    List<ActivityInfo> selectActivityInfo();

    ActivityInfo findActivityInfoByTemplate(String name);

    boolean updateActivityInfo(ActivityInfo info);

    Integer clickCount(String bid, String startTime, String endTime);

    ActivityInfo getTActivityInfos(String domain, String templateName);

    List<ActivityInfo> getStoppedActivityInfos();

    boolean deleteActivityInfo(ActivityInfo info);

    boolean updatePrefix(String prefix, String name);

    List<ActivityInfo> getTActivityInfosList(String activityStatus, String prefix,String activityName);

    int getTActivityInfoNum(String domain);

    boolean createActivityInfo(ActivityInfo template);

    ActivityInfo findById(Integer id);

    boolean deleteActivityInfoById(Integer id);

    boolean checkName(String name, String domain);

    int  sendCount(String templateName,String startTime,String endTime);

    List<Map<Object, Object>> findClick(String templateName, String startTime, String endTime);

    List<Map<Object, Object>> findReptile(String templateName,String startTime,String endTime);

    boolean updateRosterNum(int rosterNum, String name);
}
