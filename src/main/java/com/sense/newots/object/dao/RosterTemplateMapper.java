package com.sense.newots.object.dao;


import com.sense.newots.object.entity.RosterTemplateInfo;

import java.util.List;

/**
 @desc ...
 @date 2021-05-27 10:46:46
 @author szz
 */
public interface RosterTemplateMapper {
    List<RosterTemplateInfo> getTRosterTemplateInfo(String domain, String templateName);

    int getTRosterTemplateInfoNum(String domain);

    List<RosterTemplateInfo> getTRosterTemplateList(String domain, String templateName);

    int createRosterTemplateInfo(RosterTemplateInfo template);

    RosterTemplateInfo findByName(String domain, String templateName);

    int updateRosterTemplateInfo(RosterTemplateInfo template);

    int deleteRosterTemplateInfo(RosterTemplateInfo template);

    int checkName(String name, String domain);


}
