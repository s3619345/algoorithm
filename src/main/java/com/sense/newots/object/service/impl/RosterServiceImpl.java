package com.sense.newots.object.service.impl;

import com.google.gson.Gson;
import com.sense.newots.object.dao.RosterMapper;
import com.sense.newots.object.entity.RosterInfo;
import com.sense.newots.object.service.RosterService;
import com.sense.newots.request.MakeCall;
import com.sense.newots.request.RosterResultW;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 @desc ...
 @date 2021-05-21 09:05:37
 @author szz
 */
@Service
@Slf4j
public class RosterServiceImpl implements RosterService {
    static Gson gson = new Gson();
    @Autowired
    private RosterMapper rosterMapper;
    private Lock lock = new ReentrantLock();    //注意这个地方

    @Override
    public int updateRosterResult(String batchName) throws DataAccessException {
        int i;
        lock.lock();
        try {
            i = rosterMapper.updateRosterResult(batchName);
            return i;
        } catch (Exception e) {
            log.error("Exception:{}", e);
            return 0;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int updateInfoByStatus(String batchId, int needNum) throws DataAccessException {
        int i;
        try {
            i = rosterMapper.updateInfoByStatus(batchId, needNum);
        } catch (Exception e) {
            log.error("Exception:{}", e);
            return 0;
        }
        return i;
    }

    @Override
    public boolean replaceRosterHistory() throws DataAccessException {
        try {
            rosterMapper.replaceRosterHistory();
        } catch (Exception e) {
            log.error("Exception:{}", e);
            return false;
        }
        return true;
    }

    @Override
    public boolean delRosterInfo(String batchId) throws DataAccessException {
        try {
            rosterMapper.delRosterInfo(batchId);
        } catch (Exception e) {
            log.error("Exception:{}", e);
            return false;
        }
        return true;
    }

    @Override
    public List<RosterInfo> findBatchRosters(String batchName, int callLimit, long id) {
        List<RosterInfo> list = rosterMapper.findBatchRosters(batchName, callLimit, id);
        log.info("## rosterInfos list size " + list.size());
        if (list == null || list.isEmpty()) {
            return new ArrayList<>();
        }
        return list;
    }

    @Override
    public boolean addUnCalledRoster(String batchId) throws DataAccessException {
        log.info("## String batchId。。。。。。。。。。 " + batchId);
        lock.lock();
        try {
            rosterMapper.addUnCalledRoster(batchId);
            return true;
        } catch (Exception e) {
            log.error("Exception:{}", e);
            return false;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int getContactNums(String domain, String templateName) {
        return rosterMapper.getContactNums(domain, templateName);
    }

    @Override
    public boolean createRosterInfo(RosterInfo roster) throws DataAccessException {
        log.info("## RosterInfo create " + gson.toJson(roster));
        try {
            rosterMapper.createRosterInfo(roster);
        } catch (Exception e) {
            log.error("Exception:{}", e);
            return false;
        }
        return false;
    }

    @Override
    public RosterResultW findById(Integer id) {
        RosterResultW rosterResultW = rosterMapper.findById(id);
        log.info("## rosterInfo findById" + rosterResultW);
        if (rosterResultW == null) {
            return null;
        }
        return rosterResultW;
    }

    @Override
    public boolean updateRosterCallResult(RosterResultW callresult) throws DataAccessException {
        log.info("## update RosterResultW " + gson.toJson(callresult));
        try {
            rosterMapper.updateRosterCallResult(callresult);
        } catch (Exception e) {
            log.error("Exception:{}", e);
            return false;
        }
        return true;
    }


    @Override
    public boolean updateJdbcRosterInfo( List<MakeCall> callList) throws DataAccessException {
        log.info("##  update JdbcRosterInfo " + gson.toJson(callList));
        try {
            rosterMapper.updateJdbcRosterInfo(callList);
        } catch (Exception e) {
            log.error("Exception:{}", e);
            return false;
        }
        return true;
    }

    @Override
    public  boolean createJdbcRosterInfo(List<RosterInfo> rosterInfoList) throws DataAccessException {
        log.info("## create JdbcRosterInfo" + gson.toJson(rosterInfoList));
        try {
            rosterMapper.createJdbcRosterInfo(rosterInfoList);
        } catch (Exception e) {
            log.error("Exception:{}", e);
            return false;
        }
        return true;
    }

}
