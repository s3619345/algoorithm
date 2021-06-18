package com.sense.newots.object.service;


import com.sense.newots.object.entity.TrunkPoolCorr;

import java.util.List;

/**
 @desc ...
 @date 2021-05-21 14:58:08
 @author szz
 */
public interface TrunkPoolCorrService {
    List<TrunkPoolCorr> getTTrunkPoolCorrs(String poolName);

    int getTTrunkPoolNum(String domain, String poolName);

    List<TrunkPoolCorr> getTTrunkPoolCorr(String domain, String poolName);

    boolean createTrunkPoolCorr(TrunkPoolCorr trunkPoolCorr);

    boolean updateTrunkPoolCorr(TrunkPoolCorr trunkPoolCorr);

    boolean deleteTrunkPoolCorr(Integer id);
}
