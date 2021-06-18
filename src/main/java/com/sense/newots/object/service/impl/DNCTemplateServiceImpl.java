package com.sense.newots.object.service.impl;

import com.google.gson.Gson;
import com.sense.newots.object.dao.DNCTemplateMapper;
import com.sense.newots.object.entity.DNCTemplate;
import com.sense.newots.object.service.DNCTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 @desc ...
 @date 2021-06-03 13:57:53
 @author szz
 */
@Service
@Slf4j
public class DNCTemplateServiceImpl implements DNCTemplateService {
    @Autowired
    private DNCTemplateMapper dncTemplcateMapper;
    static Gson gson = new Gson();

    @Override
    public List<DNCTemplate> getTDNCTemplates(String domain, String tname) {
        log.info("## getDNCTemplates [" + domain + "]");
        List<DNCTemplate> list = dncTemplcateMapper.getTDNCTemplates(domain, tname);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list;
    }

    @Override
    public int getTDNCTemplateNum(String domain, String tname) {
        int i = dncTemplcateMapper.getTDNCTemplateNum(domain, tname);
        log.info("## getTDNCTemplateNum [" + domain + "] result " + i);
        return i;
    }

    @Override
    public boolean createDNCTemplate(DNCTemplate info) throws DataAccessException {
        log.info("## DNCTemplate create " + gson.toJson(info));
        try {
            dncTemplcateMapper.createDNCTemplate(info);
        } catch (Exception e) {
            log.error("Exception:{}", e);

            return false;
        }
        return true;
    }

    @Override
    public DNCTemplate findById(Integer id) {
        DNCTemplate template = dncTemplcateMapper.findById(id);
        log.info("DNCTemplate findById" + template);
        return template;
    }

    @Override
    public boolean updateDNCTemplate(DNCTemplate info) throws DataAccessException {
        log.info("## DNCTemplate update " + gson.toJson(info));
        try {
            dncTemplcateMapper.updateDNCTemplate(info);
        } catch (Exception e) {
            log.error("Exception:{}", e);

            return false;
        }
        return true;
    }

    @Override
    public boolean deleteDNCTemplate(Integer id) throws DataAccessException {
        log.info("## DNCTemplate delete " + gson.toJson(id));
        try {
            dncTemplcateMapper.deleteDNCTemplate(id);
        } catch (Exception e) {
            log.error("Exception:{}", e);

            return false;
        }
        return true;
    }

    @Override
    public boolean checkDNCName(String name, String domain) {
        int i = dncTemplcateMapper.checkDNCName(name, domain);
        log.info("## getDNCTemplates [" + name + "] result " + i);
        if (i == 0L)
            return true;
        return false;
    }

    @Override
    public List<DNCTemplate> getTDNCTemplateList(String domain) {
        log.info("## getDNCTemplateList [" + domain + "]");
        List<DNCTemplate> list = dncTemplcateMapper.getTDNCTemplateList(domain);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list;
    }

    @Override
    public int getTDNCTemplateNumList(String domain) {
        int i = dncTemplcateMapper.getTDNCTemplateNumList(domain);
        log.info("## getTDNCTemplateNumList [" + domain + "] result " + i);
        return i;
    }
}
