package com.sense.newots.object.dao;

import com.sense.newots.object.entity.Holiday;

import java.util.List;

/**
 @desc ...
 @date 2021-06-04 09:31:31
 @author szz
 */
public interface HolidayMapper {

    List<Holiday> getTHolidays(String domain);

    Integer getTHolidayNum(String domain);

    int createHoliday(Holiday holiday);

    int deleteHoliday(Integer id);

    int updateHoliday(Holiday holiday);
}
