package com.sense.newots.object.service.impl;

import com.google.gson.Gson;
import com.sense.newots.object.dao.ConfigParamMapper;
import com.sense.newots.object.entity.ConfigParam;
import com.sense.newots.object.service.ConfigParamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 @desc ...
 @date 2021-05-21 14:58:39
 @author szz
 */
@Service
@Slf4j
public class ConfigParamServiceImpl implements ConfigParamService {
    @Autowired
    private ConfigParamMapper configParamMapper;
    static Gson gson = new Gson();

    @Override
    public ConfigParam findByName(String name) {
        ConfigParam configParam =  configParamMapper.findByName(name);
        if (configParam == null) {
            return null;
        }
        return configParam;
    }

    @Override
    public List<ConfigParam> getTConfigParams(String domain, String paramType) {
        List<ConfigParam> list = configParamMapper.getTConfigParams(domain, paramType);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list;
    }

    @Override
    public int getTConfigParamNum(String domain, String paramType) {
        int i = configParamMapper.getTConfigParamNum(domain, paramType);
        log.info("## getTConfigParamNum [" + domain + "] result " + i);
        return i;
    }

    @Override
    public boolean createConfigParam(ConfigParam configParam) throws DataAccessException {
        log.info("## ConfigParam create " + gson.toJson(configParam));
        try {
            configParamMapper.createConfigParam(configParam);
        } catch (Exception e) {
            log.error("Exception:{}", e);
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteConfigParam(ConfigParam configParam) throws DataAccessException {
        log.info("## ConfigParam delete " + configParam);
        try {
            configParamMapper.deleteConfigParam(configParam);
        } catch (Exception e) {
            log.error("Exception:{}", e);
            return false;
        }
        return true;
    }

    @Override
    public boolean updateConfigParam(ConfigParam configParam) throws DataAccessException {
        log.info("## ConfigParam update " + gson.toJson(configParam));
        try {
            configParamMapper.updateConfigParam(configParam);
        } catch (Exception e) {
            log.error("Exception:{}", e);
            return false;
        }
        return true;
    }

    @Override
    public boolean checkConfigParamName(String name) {
        int i = configParamMapper.checkConfigParamName(name);
        log.info("## getTConfigParamNum [" + name + "] result " + i);
        if (i == 0L)
            return true;
        return false;
    }

}
