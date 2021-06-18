package com.sense.newots.redis.buffer;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IOriginalBuffer {
    String set(String paramString1, String paramString2);

    String get(String paramString);

    Long hset(String paramString1, String paramString2, String paramString3);

    String hget(String paramString1, String paramString2);

    Long hsetnx(String paramString1, String paramString2, String paramString3);

    String hmset(String paramString, Map<String, String> paramMap);

    List<String> hmget(String paramString, String[] paramArrayOfString);

    Long hincrBy(String paramString1, String paramString2, long paramLong);

    Boolean hexists(String paramString1, String paramString2);

    Long hdel(String paramString1, String paramString2);

    Long hlen(String paramString);

    Set<String> hkeys(String paramString);

    List<String> hvals(String paramString);

    Map<String, String> hgetAll(String paramString);

    Boolean exists(String paramString);

    Long del(String paramString);

    Long expire(String paramString, int paramInt);

    Long expireAt(String paramString, long paramLong);

    Long lpush(String paramString1, String paramString2);

    Long llen(String paramString);

    List<String> lrange(String paramString, long paramLong1, long paramLong2);

    String ltrim(String paramString, long paramLong1, long paramLong2);

    String lindex(String paramString, long paramLong);

    Long lrem(String paramString1, long paramLong, String paramString2);

    Long sadd(String paramString1, String paramString2);

    Set<String> smembers(String paramString);

    Long srem(String paramString1, String paramString2);

    String spop(String paramString);

    Long smove(String paramString1, String paramString2, String paramString3);

    Long scard(String paramString);

    Boolean sismember(String paramString1, String paramString2);

    Long zadd(String paramString1, double paramDouble, String paramString2);

    Set<String> zrange(String paramString, int paramInt1, int paramInt2);

    Long zrem(String paramString1, String paramString2);

    Double zincrby(String paramString1, double paramDouble, String paramString2);

    Long zcount(String paramString, double paramDouble1, double paramDouble2);

    Set<String> zrangeByScore(String paramString, double paramDouble1, double paramDouble2);

    Set<String> zrangeByScore(String paramString1, String paramString2, String paramString3);

    Set<String> zrangeByScore(String paramString, double paramDouble1, double paramDouble2, int paramInt1, int paramInt2);

    Set<String> zrevrangeByScore(String paramString, double paramDouble1, double paramDouble2);

    Set<String> zrevrangeByScore(String paramString, double paramDouble1, double paramDouble2, int paramInt1, int paramInt2);

    Double zscore(String paramString1, String paramString2);

    void subscribe(String paramString);

    Long publish(String paramString1, String paramString2);
}

