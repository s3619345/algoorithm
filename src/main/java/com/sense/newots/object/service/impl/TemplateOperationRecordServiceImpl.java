package com.sense.newots.object.service.impl;

import com.google.gson.Gson;
import com.sense.newots.object.dao.TemplateOperationRecordMapper;
import com.sense.newots.object.entity.TemplateOperationRecord;
import com.sense.newots.object.service.TemplateOperationRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 @desc ...
 @date 2021-05-28 14:27:37
 @author szz
 */
@Service
@Slf4j
public class TemplateOperationRecordServiceImpl implements TemplateOperationRecordService {
    static Gson gson = new Gson();
    @Autowired
    private TemplateOperationRecordMapper templateOperationRecordDao;

    @Override
    public boolean createTemplateOperationRecordInfo(TemplateOperationRecord info) throws DataAccessException {
        log.info("## TemplateOperationRecord create " + gson.toJson(info));
        try {
            templateOperationRecordDao.createTemplateOperationRecordInfo(info);
        } catch (Exception e) {
            log.error("Exception:{}",e);
            return false;
        }
        return true;
    }
}
