package com.sense.newots.object.service.impl;

import com.google.gson.Gson;
import com.sense.newots.object.dao.OutboundRecallPolicyMapper;
import com.sense.newots.object.entity.OutboundRecallPolicy;
import com.sense.newots.object.service.OutboundRecallPolicyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 @desc ...
 @date 2021-05-28 14:21:06
 @author szz
 */
@Service
@Slf4j
public class OutboundRecallPolicyServiceImpl implements OutboundRecallPolicyService {
    static Gson gson = new Gson();
    @Autowired
    private OutboundRecallPolicyMapper outboundRecallPolicyDao;


    @Override
    public List<OutboundRecallPolicy> getTOutboundRecallPolicys(String domain, String activityName) {
        List<OutboundRecallPolicy> list = outboundRecallPolicyDao.getTOutboundRecallPolicys(domain, activityName);
        if(list==null || list.isEmpty()){
            return null;
        }
        return list;
    }

    @Override
    public boolean createOutboundRecallPolicy(OutboundRecallPolicy round) throws DataAccessException {
        log.info("## OutboundRecallPolicy create " + gson.toJson(round));
        try {
            outboundRecallPolicyDao.createOutboundRecallPolicy(round);
        } catch (Exception e) {
            log.error("Exception:{}", e);
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteOutboundRecallPolicy(String domain, String activityName) throws DataAccessException {
        try {
            outboundRecallPolicyDao.deleteOutboundRecallPolicy(domain, activityName);
        } catch (Exception e) {
            log.error("Exception:{}", e);
            return false;
        }
        return true;
    }
}
