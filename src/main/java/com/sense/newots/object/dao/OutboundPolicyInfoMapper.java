package com.sense.newots.object.dao;


import com.sense.newots.object.entity.OutboundPolicyInfo;

import java.util.List;

/**
 @desc ...
 @date 2021-05-20 15:39:11
 @author szz
 */
public interface OutboundPolicyInfoMapper {
    OutboundPolicyInfo getTOutboundPolicyInfos(String domain, String name);

    List<OutboundPolicyInfo> getTOutboundPolicyInfo(String domain);

    int getTOutboundPolicyInfoNum(String domain);

    List<OutboundPolicyInfo> getTOutboundPolicyInfosList(String domain, String policyName);

    int  createOutboundPolicyInfo(OutboundPolicyInfo template);

    int updateOutboundPolicyInfo(OutboundPolicyInfo template);

    int  deleteOutboundPolicyInfo(OutboundPolicyInfo template);

    int checkName(String name, String domain);

      List<OutboundPolicyInfo> getCallAnswerStep(String activityName);
}
