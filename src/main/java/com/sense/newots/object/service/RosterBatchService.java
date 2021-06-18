package com.sense.newots.object.service;


import com.sense.newots.object.entity.ChangeBatchInfo;
import com.sense.newots.object.entity.RosterBatchInfo;

import java.util.List;

/**
 @desc ...
 @date 2021-05-20 15:15:49
 @author szz
 */
public interface RosterBatchService {
    int updateRepeatCount(String batchId);

    List<RosterBatchInfo> getTodayRosterBatchInfos();

    int updateRosterBatchInfo(RosterBatchInfo template);

    RosterBatchInfo getRosterBatch(String batchId);

    List<RosterBatchInfo> getFinishRosterBatchInfos();

    List<RosterBatchInfo> findNextUncallBatch(String startTime, String endTime, String templateName);

    boolean updateRosterBatchByStatus(String time, String batchId);

    Integer checkTemplate(String templateName);

    RosterBatchInfo findById(String id);

    boolean createRosterBatchInfo(RosterBatchInfo template);

    List<RosterBatchInfo> getUnCallRosterBatchInfos(String domain, String rosterTemplateName,String currentDate);

    List<RosterBatchInfo> getTRosterBatchInfos(String domain, String templateName);

    int getTRosterBatchInfoNum(String domain, String templateName);

    int getUncallRosterBatchInfoNum(String domain, String templateName,String currentDate);

    boolean checkName(String name, String domain);

    int updateRosterBatchIdo(ChangeBatchInfo template);

    boolean deleteRosterBatchInfo(String id);
}
