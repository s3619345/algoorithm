package com.sense.newots.object.service.impl;

import com.google.gson.Gson;
import com.sense.newots.object.dao.BatchRecordMapper;
import com.sense.newots.object.entity.BatchRecord;
import com.sense.newots.object.service.BatchRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 @desc ...
 @date 2021-05-21 09:26:03
 @author szz
 */
@Service
@Slf4j
public class BatchRecordServiceImpl implements BatchRecordService {
    static Gson gson = new Gson();
    @Autowired
    private BatchRecordMapper batchRecordMapper;
    @Override
    public int updateRecordStatus(String time, String batchId)throws DataAccessException {
        int i;
        try {
            i=batchRecordMapper.updateRecordStatus(time,batchId);
        } catch (Exception e) {
            log.error("Exception:{}", e);
            return 0;
        }
        return i;
    }

    @Override
    public boolean updateRecordTime(String time, String batchId, int maxCapacity, String prefix, String trunk)throws DataAccessException {
        try {
            batchRecordMapper.updateRecordTime(time,batchId,maxCapacity,prefix,trunk);
        } catch (Exception e) {
            log.error("Exception:{}", e);
            return false;
        }
        return true;
    }

    @Override
    public boolean saveBatchRecord(BatchRecord batchRecord) {
        log.info("## BatchRecord create"+gson.toJson(batchRecord));
        try {
            batchRecordMapper.saveBatchRecord(batchRecord);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
