package com.sense.newots.commonentity;


import com.sense.newots.object.util.ResponseUtil;

import java.util.HashMap;
import java.util.Map;


public class BaseResource {
    public Map<String, Object> getMergeSumAndList(Object list, int count) {
        return getStateMergeSumAndList(list, count);
    }

    public static Map<String, Object> getStateMergeSumAndList(Object list, int count) {
        Map map = new HashMap();
        map.put("count", Integer.valueOf(count));
        map.put("list", list);
        return map;
    }

    public ResponseUtil setResponseUtil(int codeNum, String message, Object obj) {
        ResponseUtil response = new ResponseUtil();
        response.setData(obj);
        response.setReturnCode(codeNum);
        response.setReturnMsg(message);
        return response;
    }
}
