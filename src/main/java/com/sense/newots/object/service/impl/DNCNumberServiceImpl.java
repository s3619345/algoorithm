package com.sense.newots.object.service.impl;

import com.google.gson.Gson;
import com.sense.newots.object.dao.DNCNumberMapper;
import com.sense.newots.object.entity.DNCNumber;
import com.sense.newots.object.service.DNCNumberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 @desc ...
 @date 2021-06-03 13:58:34
 @author szz
 */
@Service
@Slf4j
public class DNCNumberServiceImpl implements DNCNumberService {
    static Gson gson = new Gson();
    @Autowired
    private DNCNumberMapper dncNumberMapper;

    @Override
    public boolean createDNCNumber(DNCNumber info) throws DataAccessException {
        log.info("## DNCNumber create " + gson.toJson(info));
        try {
            dncNumberMapper.createDNCNumber(info);
        } catch (Exception e) {
            log.error("Exception:{}", e);

            return false;
        }
        return true;
    }

    @Override
    public List<DNCNumber> getTDNCNumbersQuery(String domain, String dncSet, String phoneNum) {
        log.info("## getDNCNumbers [" + domain + "] dncSet-" + dncSet + "|phoneNum-" + phoneNum);
        List<DNCNumber> list = dncNumberMapper.getTDNCNumbersQuery(domain, dncSet, phoneNum);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list;
    }

    @Override
    public int getTDNCNumberNumQuery(String domain, String dncSet, String phoneNum) {
        int i = dncNumberMapper.getTDNCNumberNumQuery(domain, dncSet, phoneNum);
        if (i <= 0) {
            return 0;
        }
        return i;
    }

    @Override
    public List<DNCNumber> getTDNCNumbers(String domain, String dncSet) {
        log.info("## getDNCNumbers [" + domain + "] dncSet-" + dncSet);
        List<DNCNumber> list = dncNumberMapper.getTDNCNumbers(domain, dncSet);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list;
    }

    @Override
    public int getTDNCNumberNum(String domain, String dncSet) {
        int i = dncNumberMapper.getTDNCNumberNum(domain, dncSet);
        if (i <= 0) {
            return 0;
        }
        return i;
    }

    @Override
    public boolean clear(String id) {
        log.info("## clear DNCNumbers [" + id + "] result ");
        int i = dncNumberMapper.clear(id);
        if(i<=0){
            return false;
        }
        return true;
    }

    @Override
    public boolean createDNCNumberList(List<DNCNumber> infos)  throws DataAccessException{
        log.info("## DNCNumber create " + gson.toJson(infos));
        try {
            dncNumberMapper.createDNCNumberList(infos);
        } catch (Exception e) {
            log.error("Exception:{}",e);

            return false;
        }
        return true;
    }

    @Override
    public boolean updateDNCNumber(DNCNumber info) throws DataAccessException{
        log.info("## DNCNumber update " + gson.toJson(info));
        try {
            dncNumberMapper.updateDNCNumber(info);
        } catch (Exception e) {
            log.error("Exception:{}",e);

            return false;
        }
        return true;
    }

    @Override
    public boolean deleteDNCNumber(int id) throws DataAccessException {
        log.info("## DNCNumber delete " + gson.toJson(id));
        try {
            dncNumberMapper.deleteDNCNumber(id);
        } catch (Exception e) {
            log.error("Exception:{}",e);

            return false;
        }
        return true;
    }

    @Override
    public DNCNumber findById(int id) {
        DNCNumber dncNumber = dncNumberMapper.findById(id);
        return dncNumber;
    }

}
