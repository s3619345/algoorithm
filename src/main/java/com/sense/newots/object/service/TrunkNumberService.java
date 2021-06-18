package com.sense.newots.object.service;


import com.sense.newots.object.entity.TrunkNumber;

import java.util.List;

/**
 @desc ...
 @date 2021-05-21 14:57:45
 @author szz
 */
public interface TrunkNumberService {
    TrunkNumber findbyNum(String number);

    List<TrunkNumber> getTTrunkNumbers(String domain);

    int getTTrunkNumberNum(String domain);

    boolean createTrunkNumber(TrunkNumber trunkNumber);

    boolean updateTrunkNumber(TrunkNumber trunkNumber);

    boolean deleteTrunkNumber(Integer id);

    boolean checkPoolName(String name);
}
