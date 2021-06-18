package com.sense.newots.object.service.impl;

import com.google.gson.Gson;
import com.sense.newots.object.dao.CircuitConfMapper;
import com.sense.newots.object.entity.CircuitConf;
import com.sense.newots.object.service.CircuitConfService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 @desc ...
 @date 2021-05-24 09:12:56
 @author szz
 */
@Service
@Slf4j
public class CircuitConfServiceImpl implements CircuitConfService {
    static Gson gson = new Gson();
    @Autowired
    private CircuitConfMapper circuitConfMapper;

    @Override
    public List<CircuitConf> getCircuitListStrartTime() {
        List<CircuitConf> list = circuitConfMapper.getCircuitListStrartTime();
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list;
    }

    @Override
    public List<CircuitConf> getCircuitListEndTime() {
        List<CircuitConf> list = circuitConfMapper.getCircuitListEndTime();
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list;
    }

    @Override
    public boolean updateCircuit(CircuitConf circuitConf) {
        log.info("## update circuit "+gson.toJson(circuitConf));
        try {
            circuitConfMapper.updateCircuit(circuitConf);
        } catch (Exception e) {
            log.error("DataAccessException:{}",e);
            return false;
        }
        return true;
    }

    @Override
    public boolean updateCircuitByStatus()  throws DataAccessException {
        try {
            circuitConfMapper.updateCircuitByStatus();
        } catch (Exception e) {
            log.error("DataAccessException:{}",e);
            return false;
        }
        return true;
    }

    @Override
    public List<CircuitConf> getCircuitInfos(String isRun, String activityName, String prefix) {
        List<CircuitConf> list = circuitConfMapper.getCircuitInfos(isRun, activityName, prefix);
        if(list==null || list.isEmpty()){
            return null;
        }
        return list;
    }

    @Override
    public int getCircuitInfoNum() {
        int i= circuitConfMapper.getCircuitInfoNum();
        log.info("getCircuitInfoNum count" +i);
        return i;
    }

    @Override
    public List<CircuitConf> findByActivityId(String activityName) {
        List<CircuitConf> list = circuitConfMapper.findByActivityId(activityName);
        if(list==null || list.isEmpty()){
            return null;
        }
        return list;
    }

    @Override
    public boolean saveCircuit(CircuitConf circuit)  throws DataAccessException {
        log.info("## Circuit create"+gson.toJson(circuit));
        try {
            circuitConfMapper.saveCircuit(circuit);
        } catch (Exception e) {
            log.error("Exception:{}", e);
            return false;
        }
        return true;
    }

    @Override
    public boolean delCircuit(Integer id) throws DataAccessException {
        log.info("del circuit info"+gson.toJson(id));
        try {
            circuitConfMapper.delCircuit(id);
        } catch (Exception e) {
            log.error("Exception:{}", e);
            return false;
        }
        return true;
    }
}
