package com.sense.newots.object.dao;

import com.sense.newots.object.entity.DNCTemplate;

import java.util.List;

/**
 @desc ...
 @date 2021-06-04 11:13:30
 @author szz
 */
public interface DNCTemplateMapper {
    List<DNCTemplate> getTDNCTemplates(String domain, String tname);

    int getTDNCTemplateNum(String domain, String tname);

    int  createDNCTemplate(DNCTemplate info);

    DNCTemplate findById(Integer id);

    int  updateDNCTemplate(DNCTemplate info);

    int deleteDNCTemplate(Integer id);

    int checkDNCName(String name, String domain);

    List<DNCTemplate> getTDNCTemplateList(String domain);

    int getTDNCTemplateNumList(String domain);
}
