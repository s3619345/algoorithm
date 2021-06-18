package com.sense.newots.object.service;

import com.sense.newots.object.entity.DNCTemplate;

import java.util.List;

/**
 @desc ...
 @date 2021-06-03 13:57:05
 @author szz
 */
public interface DNCTemplateService {
    List<DNCTemplate> getTDNCTemplates(String domain, String tname);

    int getTDNCTemplateNum(String domain, String tname);

    boolean  createDNCTemplate(DNCTemplate info);

    DNCTemplate findById(Integer id);

    boolean  updateDNCTemplate(DNCTemplate info);

    boolean deleteDNCTemplate(Integer id);

    boolean checkDNCName(String name, String domain);

    List<DNCTemplate> getTDNCTemplateList(String domain);

    int getTDNCTemplateNumList(String domain);
}
