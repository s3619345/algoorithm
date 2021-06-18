package com.sense.newots.object.service.impl;

import com.google.gson.Gson;
import com.sense.newots.object.dao.TrunkPoolCorrMapper;
import com.sense.newots.object.entity.TrunkPoolCorr;
import com.sense.newots.object.service.TrunkPoolCorrService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 @desc ...
 @date 2021-05-21 14:59:55
 @author szz
 */
@Service
@Slf4j
public class TrunkPoolCorrServiceImpl implements TrunkPoolCorrService {
    static Gson gson = new Gson();
    @Autowired
    private TrunkPoolCorrMapper trunkPoolCorrMapper;
    @Override
    public List<TrunkPoolCorr> getTTrunkPoolCorrs(String poolName) {
        log.info("## getTrunkPoolCorrs ["+poolName+"]");
        List<TrunkPoolCorr> list = trunkPoolCorrMapper.getTTrunkPoolCorrs(poolName);
        if(list==null || list.isEmpty()){
            return null;
        }
        return list;
    }

    @Override
    public int getTTrunkPoolNum(String domain, String poolName) {
        int i=trunkPoolCorrMapper.getTTrunkPoolNum(domain, poolName);
        log.info("## getTTrunkPoolNum [" + domain + "] result " + i);
        return i;
    }

    @Override
    public List<TrunkPoolCorr> getTTrunkPoolCorr(String domain, String poolName) {
        log.info("## getTrunkPoolCorr ["+poolName+"]"+domain);
        List<TrunkPoolCorr> list = trunkPoolCorrMapper.getTTrunkPoolCorr(domain, poolName);
        if(list==null || list.isEmpty()){
            return null;
        }
        return list;
    }

    @Override
    public boolean createTrunkPoolCorr(TrunkPoolCorr trunkPoolCorr)throws DataAccessException {
        log.info("## TrunkPoolCorr create " + gson.toJson(trunkPoolCorr));
        try {
            trunkPoolCorrMapper.createTrunkPoolCorr(trunkPoolCorr);
        } catch (Exception e) {
            log.error("Exception:{}",e);

            return false;
        }
        return true;
    }

    @Override
    public boolean updateTrunkPoolCorr(TrunkPoolCorr trunkPoolCorr) throws DataAccessException {
        log.info("## TrunkPoolCorr update " + gson.toJson(trunkPoolCorr));
        try {
            trunkPoolCorrMapper.updateTrunkPoolCorr(trunkPoolCorr);
        } catch (Exception e) {
            log.error("Exception:{}",e);

            return false;
        }
        return true;
    }

    @Override
    public boolean deleteTrunkPoolCorr(Integer id)throws DataAccessException {
        log.info("## TrunkPoolCorr delete " + gson.toJson(id));
        try {
            trunkPoolCorrMapper.deleteTrunkPoolCorr(id);
        } catch (Exception e) {
            log.error("Exception:{}",e);

            return false;
        }
        return true;
    }

}
