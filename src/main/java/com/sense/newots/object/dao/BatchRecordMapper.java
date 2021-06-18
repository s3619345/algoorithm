package com.sense.newots.object.dao;


import com.sense.newots.object.entity.BatchRecord;

/**
 @desc ...
 @date 2021-05-21 09:16:10
 @author szz
 */
public interface BatchRecordMapper {
    int updateRecordStatus(String time, String batchId);

    int updateRecordTime(String time, String batchId, int maxCapacity, String prefix, String trunk);

    int  saveBatchRecord(BatchRecord batchRecord);
}
