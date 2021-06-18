package com.sense.newots.object.service.impl;

import com.google.gson.Gson;
import com.sense.newots.object.dao.HolidayMapper;
import com.sense.newots.object.entity.Holiday;
import com.sense.newots.object.service.HolidayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 @desc ...
 @date 2021-06-04 09:32:09
 @author szz
 */
@Service
@Slf4j
public class HolidayServiceImpl implements HolidayService {
    @Autowired
    private HolidayMapper holidayMapper;
    static Gson gson = new Gson();
    @Override
    public List<Holiday> getTHolidays(String domain) {
        log.info("## getHolidays [" + domain + "]");
        List<Holiday> list = holidayMapper.getTHolidays(domain);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list;
    }

    @Override
    public Integer getTHolidayNum(String domain) {
        int i = holidayMapper.getTHolidayNum(domain);
        log.info("## getTHolidayNum [" + domain + "] result " + i);
        return i;
    }

    @Override
    public boolean createHoliday(Holiday holiday)throws DataAccessException {
        log.info("## Holiday create " + gson.toJson(holiday));
        try {
            holidayMapper.createHoliday(holiday);
        } catch (Exception e) {
            log.error("Exception:{}",e);
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteHoliday(Integer id)throws DataAccessException {
        log.info("## Holiday delete " + gson.toJson(id));
        try {
            holidayMapper.deleteHoliday(id);
        } catch (Exception e) {
            log.error("Exception:{}",e);
            return false;
        }
        return true;
    }

    @Override
    public boolean updateHoliday(Holiday holiday) throws DataAccessException {
        log.info("## Holiday update " + gson.toJson(holiday));
        try {
            holidayMapper.updateHoliday(holiday);
        } catch (Exception e) {
            log.error("Exception:{}",e);
            return false;
        }
        return true;
    }
}
