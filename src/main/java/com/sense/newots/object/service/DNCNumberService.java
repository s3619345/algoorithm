package com.sense.newots.object.service;

import com.sense.newots.object.entity.DNCNumber;

import java.util.List;

/**
 @desc ...
 @date 2021-06-03 13:57:27
 @author szz
 */
public interface DNCNumberService {
    boolean createDNCNumber(DNCNumber info);

    List<DNCNumber> getTDNCNumbersQuery(String domain, String dncSet, String phoneNum);

    int getTDNCNumberNumQuery(String domain, String dncSet, String phoneNum);

    List<DNCNumber> getTDNCNumbers(String domain, String dncSet);

    int getTDNCNumberNum(String domain, String dncSet);

    boolean  clear(String id);

    boolean createDNCNumberList( List<DNCNumber> infos);

    boolean updateDNCNumber(DNCNumber info);

    boolean deleteDNCNumber(int id);

    DNCNumber findById(int id);
}
