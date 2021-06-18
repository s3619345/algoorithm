package com.sense.newots.object.controller;

import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import com.sense.newots.commonentity.BaseResource;
import com.sense.newots.commonentity.PageRequest;
import com.sense.newots.object.entity.DNCNumber;
import com.sense.newots.object.entity.DNCNumbers;
import com.sense.newots.object.entity.RecordId;
import com.sense.newots.object.entity.RecordIds;
import com.sense.newots.object.service.DNCNumberService;
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
import java.util.Iterator;
import java.util.List;

/**
 @desc ...
 @date 2021-06-16 09:15:34
 @author szz
 */
@RestController
@RequestMapping("/dncNumber")
@Slf4j
public class DNCNumberController extends BaseResource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    static Gson gson = new Gson();
    @Autowired
    private DNCNumberService dncDao;

    @RequestMapping("list")
    @Produces({"application/json"})
    public String getDNCNumbers(@RequestBody PageRequest request) {
        new ResponseUtil();

        ResponseUtil responseUtil;
        try {
            List lists;
            int count;
            if (request.getPhoneNum() != null && request.getPhoneNum().length() > 0) {
                int e = request.getStartPage() - 1;
                PageHelper.startPage(e, request.getPageNum());
                lists = this.dncDao.getTDNCNumbersQuery(request.getDomain(), request.getDncTemplateId(), request.getPhoneNum());
                count = this.dncDao.getTDNCNumberNumQuery(request.getDomain(), request.getDncTemplateId(), request.getPhoneNum());
            } else {
                int e = request.getStartPage() - 1;
                PageHelper.startPage(e, request.getPageNum());
                lists = this.dncDao.getTDNCNumbers(request.getDomain(), request.getDncTemplateId());
                count = this.dncDao.getTDNCNumberNum(request.getDomain(), request.getDncTemplateId());
            }

            responseUtil = this.setResponseUtil(1, "getDNCNumber Suc", super.getMergeSumAndList(lists == null ? new ArrayList() : lists, count));
        } catch (Exception var6) {
            responseUtil = this.setResponseUtil(0, var6.getMessage(), (Object) null);
            log.error("getDNCNumbers fail!:{}", var6);
        }

        return gson.toJson(responseUtil);
    }

    @RequestMapping("clear")
    @Produces({"application/json"})
    public ResponseUtil clearDNCNumber(@RequestBody RecordId rId) {
        new ResponseUtil();

        ResponseUtil responseUtil;
        try {
            boolean e = this.dncDao.clear(String.valueOf(rId.getId()));
            if (e) {
                responseUtil = this.setResponseUtil(1, "clear DncNumber Suc", (Object) null);
            } else {
                responseUtil = this.setResponseUtil(0, "clear DncNumber fail", (Object) null);
            }
        } catch (Exception var4) {
            responseUtil = this.setResponseUtil(0, var4.getMessage(), (Object) null);
            log.error("updateDNCNumber fail!:{}", var4);
        }

        return responseUtil;
    }

    @RequestMapping("add")
    @Produces({"application/json"})
    public ResponseUtil addDNCNumber(@RequestBody DNCNumber template) {
        new ResponseUtil();

        ResponseUtil responseUtil;
        try {
            boolean e = this.dncDao.createDNCNumber(template);
            if (e) {
                responseUtil = this.setResponseUtil(1, "add DNCNumber Suc", (Object) null);
            } else {
                responseUtil = this.setResponseUtil(0, "add DNCNumber fail", (Object) null);
            }
        } catch (Exception var4) {
            responseUtil = this.setResponseUtil(0, var4.getMessage(), (Object) null);
            log.error("createDNCNumber fail!:{}", var4);
        }

        return responseUtil;
    }

    @RequestMapping("addList")
    @Produces({"application/json"})
    public ResponseUtil addDNCNumbers(@RequestBody DNCNumbers template) {
        ResponseUtil responseUtil = new ResponseUtil();
        try {
            for (Iterator var4 = template.getPhoneNumList().iterator(); var4.hasNext(); responseUtil = this.setResponseUtil(1, "add DNCNumber Suc", (Object) null)) {
                String e = (String) var4.next();
                DNCNumber number = new DNCNumber();
                number.setDomain(template.getDomain());
                number.setDncTemplateId(template.getDncTemplateId());
                number.setPhoneNum(e);
                boolean ret = this.dncDao.createDNCNumber(number);
                if (!ret) {
                    responseUtil = this.setResponseUtil(0, "add DNCNumber fail", (Object) null);
                    break;
                }
            }
        } catch (Exception var7) {
            responseUtil = this.setResponseUtil(0, var7.getMessage(), (Object) null);
            log.error("createDNCNumber fail!:{}", var7);
        }

        return responseUtil;
    }

    @RequestMapping("update")
    @Produces({"application/json"})
    public ResponseUtil updateDNCNumber(@RequestBody DNCNumber template) {
        new ResponseUtil();

        ResponseUtil responseUtil;
        try {
            boolean e = this.dncDao.updateDNCNumber(template);
            if (e) {
                responseUtil = this.setResponseUtil(1, "update DncNumber Suc", (Object) null);
            } else {
                responseUtil = this.setResponseUtil(0, "update DncNumber fail", (Object) null);
            }
        } catch (Exception var4) {
            responseUtil = this.setResponseUtil(0, var4.getMessage(), (Object) null);
            log.error("updateDNCNumber fail!:{}", var4);
        }

        return responseUtil;
    }

    @RequestMapping("delete")
    @Produces({"application/json"})
    public ResponseUtil deleteDNCNumber(@RequestBody DNCNumber template) {
        new ResponseUtil();

        ResponseUtil responseUtil;
        try {
            boolean e = this.dncDao.deleteDNCNumber(template.getId());
            if (e) {
                responseUtil = this.setResponseUtil(1, "delete DncNumber Suc", (Object) null);
            } else {
                responseUtil = this.setResponseUtil(0, "delete DncNumber fail", (Object) null);
            }
        } catch (Exception var4) {
            responseUtil = this.setResponseUtil(0, var4.getMessage(), (Object) null);
            log.error("deleteDNCNumber fail!:{}", var4);
        }

        return responseUtil;
    }
    @RequestMapping("deleteList")
    @Produces({"application/json"})
    public ResponseUtil deleteDNCNumber(@RequestBody RecordIds ids) {
        ResponseUtil responseUtil = new ResponseUtil();

        try {
            for (Iterator var4 = ids.getIds().iterator(); var4.hasNext(); responseUtil = this.setResponseUtil(1, "delete DncNumber Suc", (Object) null)) {
                String e = (String) var4.next();
                DNCNumber number = this.dncDao.findById(Integer.parseInt(e));
                boolean ret = this.dncDao.deleteDNCNumber(number.getId());
                if (!ret) {
                    responseUtil = this.setResponseUtil(0, "delete DncNumber fail", (Object) null);
                    break;
                }
            }
        } catch (Exception var7) {
            responseUtil = this.setResponseUtil(0, var7.getMessage(), (Object) null);
            log.error("deleteDNCNumber fail!:{}", var7);
        }

        return responseUtil;
    }
}
