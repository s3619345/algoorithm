package com.sense.newots.object.service.impl;

import com.google.gson.Gson;
import com.sense.newots.object.dao.TrunkNumberPoolMapper;
import com.sense.newots.object.entity.TrunkNumberPool;
import com.sense.newots.object.service.TrunkNumberPoolService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 @desc ...
 @date 2021-05-31 14:38:10
 @author szz
 */
@Service
@Slf4j
public class TrunkNumberPoolServiceImpl implements TrunkNumberPoolService {
    @Autowired
    private TrunkNumberPoolMapper trunkNumberPoolMapper;
    static Gson gson = new Gson();

    @Override
    public List<TrunkNumberPool> getTTrunkNumberPools(String domain) {
        log.info("## getTrunkNumberPools [" + domain + "]");
        List<TrunkNumberPool> list = trunkNumberPoolMapper.getTTrunkNumberPools(domain);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list;
    }

    @Override
    public int getTTrunkNumberPoolNum(String domain) {
        int i = trunkNumberPoolMapper.getTTrunkNumberPoolNum(domain);
        log.info("## getTTrunkNumberPoolNum [" + domain + "] result " + i);
        return i;
    }

    @Override
    public boolean createTrunkNumberPool(TrunkNumberPool trunkNumberPool) throws DataAccessException {
        log.info("## TrunkNumberPool create " + gson.toJson(trunkNumberPool));
        try {
            trunkNumberPoolMapper.createTrunkNumberPool(trunkNumberPool);
        } catch (Exception e) {
            log.error("Exception:{}", e);

            return false;
        }
        return true;
    }

    @Override
    public boolean updateTrunkNumberPool(TrunkNumberPool trunkNumberPool) {
        log.info("## TrunkNumberPool update " + gson.toJson(trunkNumberPool));
        try {
            trunkNumberPoolMapper.updateTrunkNumberPool(trunkNumberPool);
        } catch (Exception e) {
            log.error("Exception:{}", e);

            return false;
        }
        return true;
    }

    @Override
    public boolean deleteTrunkNumberPool(Integer id) throws DataAccessException {
        log.info("## TrunkNumberPool delete " + gson.toJson(id));
        try {
            trunkNumberPoolMapper.deleteTrunkNumberPool(id);
        } catch (Exception e) {
            log.error("Exception:{}", e);

            return false;
        }
        return true;
    }

    @Override
    public boolean checkPoolName(String name) {
        int i = trunkNumberPoolMapper.checkPoolName(name);
        log.info("## getTrunkNumberPools [" + name + "] result " + i);
        if (i == 0L)
            return true;
        return false;
    }
}
