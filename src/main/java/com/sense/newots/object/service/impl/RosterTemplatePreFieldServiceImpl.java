package com.sense.newots.object.service.impl;

import com.sense.newots.object.dao.RosterTemplatePreFieldMapper;
import com.sense.newots.object.entity.RosterTemplatePreparedField;
import com.sense.newots.object.service.RosterTemplatePreFieldService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 @desc ...
 @date 2021-05-27 14:39:36
 @author szz
 */
@Service
@Slf4j
public class RosterTemplatePreFieldServiceImpl implements RosterTemplatePreFieldService {
    @Autowired
    private RosterTemplatePreFieldMapper rosterTemplatePreFieldMapper;
    @Override
    public List<RosterTemplatePreparedField> getTRosterTemplatePreparedFields() {
        log.info("## getRosterTemplatePreparedFields [from RosterTemplatePreparedField]");
        List<RosterTemplatePreparedField> list = rosterTemplatePreFieldMapper.getTRosterTemplatePreparedFields();
        if(list==null|| list.isEmpty()){
            return null;
        }
        return list;
    }
}
