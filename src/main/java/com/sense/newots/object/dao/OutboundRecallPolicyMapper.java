package com.sense.newots.object.dao;

import com.sense.newots.object.entity.OutboundRecallPolicy;

import java.util.List;

/**
 @desc ...
 @date 2021-05-28 14:17:00
 @author szz
 */
public interface OutboundRecallPolicyMapper {
    List<OutboundRecallPolicy> getTOutboundRecallPolicys(String domain, String activityName);

    int createOutboundRecallPolicy(OutboundRecallPolicy template);

    int deleteOutboundRecallPolicy(String domain, String activityName);
}
