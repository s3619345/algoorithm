

package com.sense.newots.httpClient;

import com.alibaba.fastjson.JSON;
import com.sense.newots.object.conf.InitConfig;
import com.sense.newots.request.MakeCall;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class CallUtil {
    /*  static Client c = null;
      static WebResource r = null;

      static {
          c = Client.create();
          c.setConnectTimeout(30000);
          c.setReadTimeout(30000);
          r = c.resource(InitConfig.OUTBOUND_URL);
      }
  */
    public static String makeCall(List<MakeCall> req) {
        log.info("## MAKE CALL  post " + JSON.toJSONString(req));
        //GenericType genericType = new GenericType<ReqResponse>() {};
        //GenericType genericType = new GenericType<ClientResponse>() {};

        if (1==1) {//测试上注释掉
            return null;
        }


        try {
            String rsp = com.sense.newots.httpClient.OkHttpClientUtils.easyPost(InitConfig.OUTBOUND_URL,JSON.toJSONString(req));
            return rsp ;
        } catch (Exception e) {
            log.info("## make call rosterId is error: "+req);
            log.error("Exception:{}"+e.getCause().getMessage());
            return null;
        }

    }
}

