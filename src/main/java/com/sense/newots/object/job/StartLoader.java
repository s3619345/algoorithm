package com.sense.newots.object.job;

import com.sense.newots.object.ActivityUtil;
import com.sense.newots.object.conf.InitConfig;
import com.sense.newots.util.RedisUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;


/**
 @desc ...
 @date 2021-05-24 11:23:00
 @author szz
 */
@Component
public class StartLoader implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        InitConfig.init();
        RedisUtil.init();
        JobUtil.init();
        ActivityUtil.init();
    }
}
