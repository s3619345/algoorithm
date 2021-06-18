package com.sense.newots.object.service.impl;

import com.google.gson.Gson;
import com.sense.newots.object.dao.RosterBatchMapper;
import com.sense.newots.object.entity.ChangeBatchInfo;
import com.sense.newots.object.entity.RosterBatchInfo;
import com.sense.newots.object.service.RosterBatchService;
import com.sense.newots.object.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 @desc ...
 @date 2021-05-20 15:16:36
 @author szz
 */
@Service
@Slf4j
public class RosterBatchServiceImpl implements RosterBatchService {
    static Gson gson = new Gson();
    @Autowired
    private RosterBatchMapper rosterBatchDAO;

    @Override
    public int updateRepeatCount(String batchId) throws DataAccessException {
        int i;
        try {
            i = rosterBatchDAO.updateRepeatCount(batchId);
        } catch (Exception e) {
            log.error("Exception:{}", e);
            return 0;
        }
        return i;
    }

    @Override
    public List<RosterBatchInfo> getTodayRosterBatchInfos() {
        List<RosterBatchInfo> list = rosterBatchDAO.getTodayRosterBatchInfos(TimeUtil.getCurrentDateStr());
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }
        return list;
    }

    @Override
    public int updateRosterBatchInfo(RosterBatchInfo template) throws DataAccessException {
        int i;
        try {
            i = rosterBatchDAO.updateRosterBatchInfo(template);
        } catch (Exception e) {
            log.error("Exception:{}", e);
            return 0;
        }
        return i;
    }

    @Override
    public RosterBatchInfo getRosterBatch(String batchId) {
        RosterBatchInfo rosterBatch = rosterBatchDAO.getRosterBatch(batchId);
        log.info("## getRosterBatchInfos [" + batchId + "] ");
        if (rosterBatch == null) {
            return null;
        }
        return rosterBatch;
    }

    @Override
    public List<RosterBatchInfo> getFinishRosterBatchInfos() {
        List<RosterBatchInfo> list = rosterBatchDAO.getFinishRosterBatchInfos();
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list;
    }

    @Override
    public List<RosterBatchInfo> findNextUncallBatch(String startTime, String endTime, String templateName) {
        List<RosterBatchInfo> list = rosterBatchDAO.findNextUncallBatch(startTime, endTime, templateName);
       // log.info("## getRosterBatchInfos [" + templateName + "] ");
        if (list == null) {
            return new ArrayList<>();
        }
        return list;
    }

    @Override
    public boolean updateRosterBatchByStatus(String time, String batchId) throws DataAccessException {
        log.info("####更新batchInfo");
        try {
            rosterBatchDAO.updateRosterBatchByStatus(time, batchId);
        } catch (Exception e) {
            log.error("Exception:{}", e);
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public Integer checkTemplate(String templateName) {
        Integer i = rosterBatchDAO.checkTemplate(templateName);
        log.info("## getRosterBatchInfos" + templateName);
        if (i == null) {
            return 0;
        }
        return i;
    }

    @Override
    public RosterBatchInfo findById(String id) {
        return rosterBatchDAO.findById(id);
    }

    @Override
    public synchronized boolean createRosterBatchInfo(RosterBatchInfo template) throws DataAccessException {
        log.info("## RosterBatchInfo create " + gson.toJson(template));
        try {
            rosterBatchDAO.createRosterBatchInfo(template);
        } catch (Exception e) {
            log.error("Exception:{}", e);
            return false;
        }
        return true;
    }

    @Override
    public List<RosterBatchInfo> getUnCallRosterBatchInfos(String domain, String rosterTemplateName, String currentDate) {
        log.info("## getRosterBatchInfos [" + rosterTemplateName + "]");
        List<RosterBatchInfo> list = rosterBatchDAO.getUnCallRosterBatchInfos(domain, rosterTemplateName, currentDate);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list;
    }

    @Override
    public List<RosterBatchInfo> getTRosterBatchInfos(String domain, String templateName) {
        log.info("## getTRosterBatchInfos"+templateName);
        List<RosterBatchInfo> list=rosterBatchDAO.getTRosterBatchInfos(domain,templateName);
        if(list!=null || list.isEmpty()){
            return null;
        }
        return list;
    }

    @Override
    public int getTRosterBatchInfoNum(String domain, String templateName) {
       int i=rosterBatchDAO.getTRosterBatchInfoNum(domain,templateName);
       if(i<=0){
           return 0;
       }
        return i;
    }

    @Override
    public int getUncallRosterBatchInfoNum(String domain, String templateName, String currentDate) {
        int i=rosterBatchDAO.getUncallRosterBatchInfoNum(domain, templateName, currentDate);
        if(i<0){
            return 0;
        }
        return i;
    }

    @Override
    public boolean checkName(String name, String domain) {
       int i=rosterBatchDAO.checkName(name,domain);
        return i==0;
    }

    @Override
    public int updateRosterBatchIdo(ChangeBatchInfo template) {
         int i=rosterBatchDAO.updateRosterBatchIdo(template);
        return i;
    }

    @Override
    public boolean deleteRosterBatchInfo(String id) throws DataAccessException {
        log.info("## RosterBatchInfo delete " + gson.toJson(id));
        try {
            rosterBatchDAO.deleteRosterBatchInfo(id);
        } catch (Exception e) {
            log.error("Exception:{}",e);
            return false;
        }
       return true;
    }
}
