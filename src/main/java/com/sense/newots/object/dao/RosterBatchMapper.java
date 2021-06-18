package com.sense.newots.object.dao;


import com.sense.newots.object.entity.ChangeBatchInfo;
import com.sense.newots.object.entity.RosterBatchInfo;

import java.util.List;

/**
 @desc ...
 @date 2021-05-20 14:58:22
 @author szz
 */

public interface RosterBatchMapper {
    int updateRepeatCount(String batchId);

    List<RosterBatchInfo> getTodayRosterBatchInfos(String currentDate);

   RosterBatchInfo getRosterBatch(String batchId);

    int updateRosterBatchInfo(RosterBatchInfo template);

    List<RosterBatchInfo> getFinishRosterBatchInfos();

   List<RosterBatchInfo> findNextUncallBatch(String startTime, String endTime, String templateName);

    int updateRosterBatchByStatus(String time, String batchId);

    Integer checkTemplate(String templateName);

    RosterBatchInfo findById(String id);

    int createRosterBatchInfo(RosterBatchInfo template);

    List<RosterBatchInfo> getUnCallRosterBatchInfos(String domain, String rosterTemplateName,String currentDate);

    List<RosterBatchInfo> getTRosterBatchInfos(String domain, String templateName);

    int getTRosterBatchInfoNum(String domain, String templateName);

    int getUncallRosterBatchInfoNum(String domain, String templateName,String currentDate);

    int checkName(String name, String domain);

    int updateRosterBatchIdo(ChangeBatchInfo template);

    int  deleteRosterBatchInfo(String id);

}
