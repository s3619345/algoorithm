package com.sense.newots.object.service;

import com.sense.newots.object.entity.TrunkNumberPool;

import java.util.List;

/**
 @desc ...
 @date 2021-05-31 14:37:48
 @author szz
 */
public interface TrunkNumberPoolService {
    List<TrunkNumberPool> getTTrunkNumberPools(String domain);

    int getTTrunkNumberPoolNum(String domain);

    boolean  createTrunkNumberPool(TrunkNumberPool trunkNumberPool);

    boolean updateTrunkNumberPool(TrunkNumberPool trunkNumberPool);

   boolean deleteTrunkNumberPool(Integer id);

    boolean  checkPoolName(String name);
}
