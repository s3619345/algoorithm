package com.sense.newots.object.service;

import com.sense.newots.object.entity.BatchRecord;

/**
 @desc ...
 @date 2021-05-21 09:25:29
 @author szz
 */
public interface BatchRecordService {
    int updateRecordStatus(String time, String batchId);

    boolean updateRecordTime(String time, String batchId, int maxCapacity, String prefix, String trunk);

    boolean  saveBatchRecord(BatchRecord batchRecord);
}
