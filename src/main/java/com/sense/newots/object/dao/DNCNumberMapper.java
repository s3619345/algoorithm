package com.sense.newots.object.dao;

import com.sense.newots.object.entity.DNCNumber;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 @desc ...
 @date 2021-06-03 13:56:14
 @author szz
 */
public interface DNCNumberMapper {
    int createDNCNumber(DNCNumber info);

    List<DNCNumber> getTDNCNumbersQuery(String domain, String dncSet, String phoneNum);

    int getTDNCNumberNumQuery(String domain, String dncSet, String phoneNum);

    List<DNCNumber> getTDNCNumbers(String domain, String dncSet);

    int getTDNCNumberNum(String domain, String dncSet);

    int clear(String id);

    int createDNCNumberList(@Param("infos") List<DNCNumber> infos);

    int updateDNCNumber(DNCNumber info);

    int deleteDNCNumber(int id);

    DNCNumber findById(int id);
}
