package com.sense.newots.object.service.impl;

import com.google.gson.Gson;
import com.sense.newots.object.dao.TrunkNumberMapper;
import com.sense.newots.object.entity.TrunkNumber;
import com.sense.newots.object.service.TrunkNumberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 @desc ...
 @date 2021-05-21 14:59:23
 @author szz
 */
@Service
@Slf4j
public class TrunkNumberServiceImpl implements TrunkNumberService {
    static Gson gson = new Gson();
    @Autowired
    private TrunkNumberMapper trunkNumberMapper;

    @Override
    public TrunkNumber findbyNum(String number) {
        log.info("## getTrunkNumbers [" + number + "] ");
        TrunkNumber trunkNumber = trunkNumberMapper.findbyNum(number);
        if (trunkNumber == null) {
            return null;
        }
        return trunkNumber;
    }

    @Override
    public List<TrunkNumber> getTTrunkNumbers(String domain) {
        List<TrunkNumber> list = trunkNumberMapper.getTTrunkNumbers(domain);
        log.info("## getTrunkNumbers [" + domain + "]");
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list;
    }

    @Override
    public int getTTrunkNumberNum(String domain) {
        int i = trunkNumberMapper.getTTrunkNumberNum(domain);
        log.info("## getTTrunkNumberNum [" + domain + "] result " + i);
        return i;
    }

    @Override
    public boolean createTrunkNumber(TrunkNumber trunkNumber) throws DataAccessException {
        log.info("## TrunkNumber update " + gson.toJson(trunkNumber));
        try {
            trunkNumberMapper.createTrunkNumber(trunkNumber);
        } catch (Exception e) {
            log.error("Exception:{}", e);

            return false;
        }
        return true;
    }

    @Override
    public boolean updateTrunkNumber(TrunkNumber trunkNumber) throws DataAccessException {
        log.info("## TrunkNumber update " + gson.toJson(trunkNumber));
        try {
            trunkNumberMapper.updateTrunkNumber(trunkNumber);
        } catch (Exception e) {
            log.error("Exception:{}", e);

            return false;
        }
        return true;
    }

    @Override
    public boolean deleteTrunkNumber(Integer id) throws DataAccessException {
        log.info("## TrunkNumber delete " + gson.toJson(id));
        try {
            trunkNumberMapper.deleteTrunkNumber(id);
        } catch (Exception e) {
            log.error("Exception:{}", e);

            return false;
        }
        return true;
    }

    @Override
    public boolean checkPoolName(String name) {
        int i = trunkNumberMapper.checkPoolName(name);
        log.info("## getTrunkNumbers [" + name + "] result " + i);
        if (i == 0L)
            return true;
        return false;
    }


}
