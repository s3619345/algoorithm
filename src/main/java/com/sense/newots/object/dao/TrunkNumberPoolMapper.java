package com.sense.newots.object.dao;

import com.sense.newots.object.entity.TrunkNumberPool;

import java.util.List;

/**
 @desc ...
 @date 2021-05-31 14:37:08
 @author szz
 */
public interface TrunkNumberPoolMapper {
    List<TrunkNumberPool> getTTrunkNumberPools(String domain);

    int getTTrunkNumberPoolNum(String domain);

    int  createTrunkNumberPool(TrunkNumberPool trunkNumberPool);

    int updateTrunkNumberPool(TrunkNumberPool trunkNumberPool);

    int  deleteTrunkNumberPool(Integer id);

   int  checkPoolName(String name);
}
