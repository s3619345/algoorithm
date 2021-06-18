package com.sense.newots.object.service;

import com.sense.newots.object.entity.OutboundRecallPolicy;

import java.util.List;

/**
 @desc ...
 @date 2021-05-28 14:20:51
 @author szz
 */
public interface OutboundRecallPolicyService {
    List<OutboundRecallPolicy> getTOutboundRecallPolicys(String domain, String activityName);

    boolean createOutboundRecallPolicy(OutboundRecallPolicy template);

    boolean deleteOutboundRecallPolicy(String domain, String activityName);
}
