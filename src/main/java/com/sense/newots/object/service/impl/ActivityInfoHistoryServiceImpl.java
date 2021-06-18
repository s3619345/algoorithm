package com.sense.newots.object.service.impl;

import com.google.gson.Gson;
import com.sense.newots.object.dao.ActivityInfoHistoryMapper;
import com.sense.newots.object.entity.ActivityInfoHistory;
import com.sense.newots.object.service.ActivityInfoHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 @desc ...
 @date 2021-05-21 10:44:07
 @author szz
 */
@Service
@Slf4j
public class ActivityInfoHistoryServiceImpl implements ActivityInfoHistoryService {
    static Gson gson = new Gson();
    @Autowired
    private ActivityInfoHistoryMapper activityInfoHistoryMapper;

    @Override
    public int createActivityInfoHistory(ActivityInfoHistory info) throws DataAccessException {
        log.info("## ActivityInfoHistory create " + gson.toJson(info));
        int i;
        try {
            i = activityInfoHistoryMapper.createActivityInfoHistory(info);
        } catch (Exception e) {
            log.error("Exception:{}", e);
            return 0;
        }
        return i;
    }

    @Override
    public List<ActivityInfoHistory> getTActivityInfoHistorys(String domain, String activityName) {
        log.info("## getActivityInfoHistorys [" + activityName + "] ");
        List<ActivityInfoHistory> list = activityInfoHistoryMapper.getTActivityInfoHistorys(domain, activityName);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list;
    }

    @Override
    public int getTActivityInfoHistoryNum(String domain) {
        int i = activityInfoHistoryMapper.getTActivityInfoHistoryNum(domain);
        log.info("## getTActivityInfoHistoryNum [" + domain + "] result " + i);
        return i;
    }

    @Override
    public boolean updateActivityInfoHistory(ActivityInfoHistory info) throws DataAccessException {
        log.info("## ActivityInfoHistory update " + gson.toJson(info));
        try {
            activityInfoHistoryMapper.updateActivityInfoHistory(info);
        } catch (Exception e) {
            log.error("Exception:{}",e);
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteActivityInfoHistory(ActivityInfoHistory info) throws DataAccessException{
        try {
            activityInfoHistoryMapper.deleteActivityInfoHistory(info);
        } catch (Exception e) {
            log.error("Exception:{}",e);
            return false;
        }
        return true;
    }
}
