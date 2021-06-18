package com.sense.newots.redis.cluster;

import java.util.Map;
import java.util.Set;

public interface RedisClusterDevice {
    Map<String, String> getInfo();

    Map<String, Set<String>> getKeys(String paramString);

    Map<String, String> scriptLoad(String paramString);

    Map<String, Boolean> scriptExists(String paramString);

    Map<String, String> scriptFlush();

    Map<String, String> getSlowLog();
}

