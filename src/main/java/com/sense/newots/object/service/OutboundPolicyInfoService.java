package com.sense.newots.object.service;


import com.sense.newots.object.entity.OutboundPolicyInfo;

import java.util.List;

/**
 @desc ...
 @date 2021-05-20 17:22:06
 @author szz
 */
public interface OutboundPolicyInfoService {
    OutboundPolicyInfo getTOutboundPolicyInfos(String domain, String name);

    List<OutboundPolicyInfo> getTOutboundPolicyInfo(String domain);

    int getTOutboundPolicyInfoNum(String domain);

    List<OutboundPolicyInfo> getTOutboundPolicyInfosList(String domain, String policyName);

    boolean createOutboundPolicyInfo(OutboundPolicyInfo template);

    boolean updateOutboundPolicyInfo(OutboundPolicyInfo template);

    boolean deleteOutboundPolicyInfo(OutboundPolicyInfo template);

    boolean checkName(String name, String domain);

    String  getCallAnswerStep(String activityName);
}
