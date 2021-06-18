package com.sense.newots.object.service;


import com.sense.newots.object.entity.ConfigParam;

import java.util.List;

/**
 @desc ...
 @date 2021-05-21 14:57:15
 @author szz
 */
public interface ConfigParamService {
    ConfigParam findByName(String name);

    List<ConfigParam> getTConfigParams(String domain, String paramType);

    int getTConfigParamNum(String domain, String paramType);

    boolean createConfigParam(ConfigParam configParam);

    boolean deleteConfigParam(ConfigParam configParam);

    boolean updateConfigParam(ConfigParam configParam);

    boolean checkConfigParamName(String name);
}
