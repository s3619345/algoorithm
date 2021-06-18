package com.sense.newots.object.dao;


import com.sense.newots.object.entity.TrunkNumber;

import java.util.List;

/**
 @desc ...
 @date 2021-05-21 14:55:49
 @author szz
 */

public interface TrunkNumberMapper {
    TrunkNumber findbyNum(String number);

    List<TrunkNumber> getTTrunkNumbers(String domain);

    int getTTrunkNumberNum(String domain);

    int createTrunkNumber(TrunkNumber trunkNumber);

    int updateTrunkNumber(TrunkNumber trunkNumber);

    int deleteTrunkNumber(Integer id);

    int checkPoolName(String name);
}
