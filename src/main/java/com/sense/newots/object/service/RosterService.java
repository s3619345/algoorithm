package com.sense.newots.object.service;


import com.sense.newots.object.entity.RosterInfo;
import com.sense.newots.request.MakeCall;
import com.sense.newots.request.RosterResultW;

import java.util.List;

/**
 @desc ...
 @date 2021-05-21 09:05:07
 @author szz
 */
public interface RosterService {
    int updateRosterResult(String batchName);

    int updateInfoByStatus(String batchId, int needNum);

    boolean replaceRosterHistory();

    boolean delRosterInfo(String batchId);

    List<RosterInfo> findBatchRosters(String batchName,int callLimit, long id);

    boolean addUnCalledRoster(final String batchId);

    int getContactNums(String domain, String templateName);

    boolean createRosterInfo(RosterInfo roster);

    RosterResultW findById(Integer id);

    boolean  updateRosterCallResult(RosterResultW callresult);

    boolean updateJdbcRosterInfo(List<MakeCall> callList);

   boolean createJdbcRosterInfo( List<RosterInfo> rosterInfoList);
}
