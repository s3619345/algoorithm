package com.sense.newots.object.dao;


import com.sense.newots.object.entity.TrunkPoolCorr;

import java.util.List;

/**
 @desc ...
 @date 2021-05-21 14:56:24
 @author szz
 */

public interface TrunkPoolCorrMapper {
    List<TrunkPoolCorr> getTTrunkPoolCorrs(String poolName);

    int getTTrunkPoolNum(String domain, String poolName);

    List<TrunkPoolCorr> getTTrunkPoolCorr(String domain, String poolName);

    int createTrunkPoolCorr(TrunkPoolCorr trunkPoolCorr);

    int updateTrunkPoolCorr(TrunkPoolCorr trunkPoolCorr);

    int deleteTrunkPoolCorr(Integer id);
}
