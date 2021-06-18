package com.sense.newots.object.controller;

import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import com.sense.newots.commonentity.BaseResource;
import com.sense.newots.commonentity.PageRequest;
import com.sense.newots.object.entity.Holiday;
import com.sense.newots.object.service.HolidayService;
import com.sense.newots.object.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

/**
 @desc ...
 @date 2021-06-04 09:29:31
 @author szz
 */
@RestController
@RequestMapping("/holiday")
@Slf4j
public class HolidayController extends BaseResource {

    @Context
    UriInfo uriInfo;

    @Context
    Request request;
    static Gson gson = new Gson();
    @Autowired
    private HolidayService holidayDao;

    @RequestMapping("list")
    @Produces({"application/json"})
    public String getHolidays(@RequestBody PageRequest request) {
        ResponseUtil responseUtil = new ResponseUtil();
        try {
            PageHelper.startPage(request.getStartPage() - 1, request.getPageNum());
            List lists = this.holidayDao.getTHolidays(request.getDomain());
            int count = this.holidayDao.getTHolidayNum(request.getDomain());
            responseUtil = setResponseUtil(1, "getHoliday Suc",
                    super.getMergeSumAndList(lists == null ? new ArrayList() : lists, count));
        } catch (Exception e) {
            responseUtil = setResponseUtil(0, e.getMessage(), null);
            log.error("getHolidays fail!:{}", e);
        }
        return gson.toJson(responseUtil);
    }

    @RequestMapping("add")
    @Produces({"application/json"})
    public ResponseUtil addHoliday(@RequestBody Holiday holiday) {
        ResponseUtil responseUtil = new ResponseUtil();
        try {
            if (holiday.getStartDate() == null)
                throw new IllegalArgumentException("请输入开始时间");
            this.holidayDao.createHoliday(holiday);
            responseUtil = setResponseUtil(1, "addHoliday Suc", null);
        } catch (Exception e) {
            log.error("addHoliday fail!:{}", e);
            responseUtil = setResponseUtil(0, e.getMessage(), null);
        }
        return responseUtil;
    }

    @RequestMapping("delete")
    @Produces({"application/json"})
    public ResponseUtil deleteHoliday(@RequestBody Holiday holiday) {
        ResponseUtil responseUtil = new ResponseUtil();
        try {
            this.holidayDao.deleteHoliday(holiday.getId());
            responseUtil = setResponseUtil(1, "deleteHoliday Suc", null);
        } catch (Exception e) {
            log.error("deleteHoliday fail!:{}", e);
            responseUtil = setResponseUtil(0, e.getMessage(), null);
        }
        return responseUtil;
    }

    @RequestMapping("update")
    @Produces({"application/json"})
    public ResponseUtil updateHoliday(@RequestBody Holiday holiday) {
        ResponseUtil responseUtil = new ResponseUtil();
        try {
            this.holidayDao.updateHoliday(holiday);
            responseUtil = setResponseUtil(1, "updateHoliday Suc", null);
        } catch (Exception e) {
            log.error("updateHoliday fail!:{}", e);
            responseUtil = setResponseUtil(0, e.getMessage(), null);
        }
        return responseUtil;
    }
}
