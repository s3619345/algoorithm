package com.sense.newots.object.service.impl;

import com.google.gson.Gson;
import com.sense.newots.object.dao.RosterTemplateMapper;
import com.sense.newots.object.entity.RosterTemplateInfo;
import com.sense.newots.object.service.RosterTemplateService;
import com.sense.newots.object.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 @desc ...
 @date 2021-05-27 11:07:39
 @author szz
 */
@Service
@Slf4j
public class RosterTemplateServiceImpl implements RosterTemplateService {
    static Gson gson = new Gson();
    @Autowired
    private RosterTemplateMapper rosterTemplateMapper;

    @Override
    public List<RosterTemplateInfo> getTRosterTemplateInfo(String domain, String templateName) {
        log.info("## getRosterTemplateInfos" + domain + "templateName" + templateName);
        List<RosterTemplateInfo> list = rosterTemplateMapper.getTRosterTemplateInfo(domain, templateName);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list;
    }

    @Override
    public int getTRosterTemplateInfoNum(String domain) {
        log.info("## getTRosterTemplateInfoNum [" + domain + "]");
        return rosterTemplateMapper.getTRosterTemplateInfoNum(domain);
    }

    @Override
    public List<RosterTemplateInfo> getTRosterTemplateList(String domain, String templateName) {
        List<RosterTemplateInfo> list = rosterTemplateMapper.getTRosterTemplateList(domain, templateName);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list;
    }

    @Override
    public boolean createRosterTemplateInfo(RosterTemplateInfo template) throws DataAccessException {
        log.info("## RosterTemplateInfo create " + gson.toJson(template));
        try {
            rosterTemplateMapper.createRosterTemplateInfo(template);
        } catch (Exception e) {
            log.error("Exception:{}", e);
            return false;
        }
        return true;
    }

    @Override
    public RosterTemplateInfo findByName(String domain, String templateName) {
        log.info("## getRosterTemplateInfos [" + domain + "] ");
        RosterTemplateInfo rosterTemplateInfo = rosterTemplateMapper.findByName(domain, templateName);
        if (rosterTemplateInfo == null) {
            return null;
        }
        return rosterTemplateInfo;
    }

    @Override
    public boolean updateRosterTemplateInfo(RosterTemplateInfo template) throws DataAccessException {
        log.info("## RosterTemplateInfo update " + gson.toJson(template));
        try {
            rosterTemplateMapper.updateRosterTemplateInfo(template);
        } catch (Exception e) {
            log.error("Exception:{}", e);
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteRosterTemplateInfo(RosterTemplateInfo template) throws DataAccessException {
        log.info("## RosterTemplateInfo delete " + gson.toJson(template));
        try {
            rosterTemplateMapper.deleteRosterTemplateInfo(template);
        } catch (Exception e) {
            log.error("Exception:{}", e);
            return false;
        }
        return true;
    }

    @Override
    public boolean checkName(String name, String domain) {
        int i = rosterTemplateMapper.checkName(name, domain);
        if (i == 0L) {
            return true;
        }
        return false;
    }

    @Override
    public ArrayList<HashMap<String, String>> importIntoDateSource(BufferedReader reader, boolean isAuto, String batchId) {
        String tempString = null;
        /*  76 */
        String[] arr = null;
        /*  77 */
        String[] arrColumn = null;
        /*  78 */
        int line = 0;
        /*  79 */
        ArrayList contacts = new ArrayList();

        /*  81 */
        String autoBatchId = TimeUtil.getCurrentTimeStr();
        try {
            /*  83 */
            while ((tempString = reader.readLine()) != null) {
                /*  84 */
                arr = tempString.split(",");
                /*  85 */
                line++;
                /*  86 */
                if (line == 1) {
                    /*  87 */
                    arrColumn = tempString.split(",");
                } else {
                    /*  89 */
                    HashMap map = new HashMap();
                    /*  90 */
                    for (int i = 0; i < arr.length; i++) {
                        /*  91 */
                        map.put(arrColumn[i], arr[i]);
                    }
                    /*  93 */
                    if ((!isAuto) && (StringUtils.isBlank(batchId))) {
                        /*  94 */
                        throw new IllegalArgumentException("请输入批次");
                    }
                    /*  96 */
                    if (!isAuto) {
                        /*  97 */
                        map.put("batchId", batchId);
                    }
                    /*  99 */
                    else if (map.get("batchId") == null) {
                        /* 100 */
                        map.put("batchId", autoBatchId);
                    }

                    /* 103 */
                    contacts.add(map);
                }
            }
        } catch (Exception e) {
            /* 107 */
            log.info("upload compelete ");
        }
        /* 109 */
        return contacts;
    }
}
