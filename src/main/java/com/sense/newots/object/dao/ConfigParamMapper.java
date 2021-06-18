package com.sense.newots.object.dao;


import com.sense.newots.object.entity.ConfigParam;

import java.util.List;

/**
 @desc ...
 @date 2021-05-21 14:55:18
 @author szz
 */
public interface ConfigParamMapper {
    ConfigParam findByName(String name);

    List<ConfigParam> getTConfigParams(String domain, String paramType);

    int getTConfigParamNum(String domain, String paramType);

    int createConfigParam(ConfigParam configParam);

    int  deleteConfigParam(ConfigParam configParam);

    int updateConfigParam(ConfigParam configParam);

    int checkConfigParamName(String name);
}
