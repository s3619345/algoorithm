package com.sense.newots.object.service;

import com.sense.newots.object.entity.Holiday;

import java.util.List;

/**
 @desc ...
 @date 2021-06-04 09:31:52
 @author szz
 */
public interface HolidayService {

    List<Holiday> getTHolidays(String domain);

    Integer getTHolidayNum(String domain);

    boolean createHoliday(Holiday holiday);

    boolean deleteHoliday(Integer id);

    boolean updateHoliday(Holiday holiday);
}
