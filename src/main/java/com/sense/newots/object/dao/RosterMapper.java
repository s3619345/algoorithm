package com.sense.newots.object.dao;

import com.sense.newots.object.entity.RosterInfo;
import com.sense.newots.request.MakeCall;
import com.sense.newots.request.RosterResultW;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 @desc ...
 @date 2021-05-21 09:02:41
 @author szz
 */

public interface RosterMapper {
    int updateRosterResult(String batchName);

    int updateInfoByStatus(String batchId, int needNum);

    int replaceRosterHistory();

    int  delRosterInfo(String batchId);

    List<RosterInfo> findBatchRosters(String batchName,int callLimit, long id);

    int  addUnCalledRoster(final String batchId);

    int updateJdbcRosterInfo(@Param("callList") List<MakeCall> callList);

    int getContactNums(String domain, String templateName);

    int  createRosterInfo(RosterInfo roster);

    RosterResultW findById(Integer id);

    int  updateRosterCallResult(RosterResultW callresult);

    int createJdbcRosterInfo( List<RosterInfo> rosterInfoList);


}
