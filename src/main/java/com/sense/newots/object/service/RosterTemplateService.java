package com.sense.newots.object.service;


import com.sense.newots.object.entity.RosterTemplateInfo;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 @desc ...
 @date 2021-05-27 11:06:49
 @author szz
 */
public interface RosterTemplateService {
    List<RosterTemplateInfo> getTRosterTemplateInfo(String domain, String templateName);

    int getTRosterTemplateInfoNum(String domain);

    List<RosterTemplateInfo> getTRosterTemplateList(String domain, String templateName);

    boolean createRosterTemplateInfo(RosterTemplateInfo template);

    RosterTemplateInfo findByName(String domain, String templateName);

    boolean updateRosterTemplateInfo(RosterTemplateInfo template);

    boolean deleteRosterTemplateInfo(RosterTemplateInfo template);

    boolean checkName(String name, String domain);

    ArrayList<HashMap<String, String>> importIntoDateSource(BufferedReader reader, boolean isAuto, String batchId);
}
