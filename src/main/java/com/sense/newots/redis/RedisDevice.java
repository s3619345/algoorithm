//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sense.newots.redis;

import com.sense.newots.redis.buffer.IOriginalBuffer;
import com.sense.newots.redis.buffer.OriginalBufferImpl;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.Map;
import java.util.Set;

@Slf4j
public class RedisDevice extends AbstractRedisDevice {
    public static final String ROSTER_TAG = "roster";
    private static final String hashTagS = "{";
    private static final String hashTagE = "}";
    private IOriginalBuffer originalBuffer = null;

    public RedisDevice() {
        this.setOriginalBuffer(new OriginalBufferImpl(this.getConnection(RedisNodeType.ROSTER)));
    }

    public IOriginalBuffer getOriginalBuffer() {
        return this.originalBuffer;
    }

    public String getCallRoster(String rosterId) {
        RedisConnection rc = this.getConnection(RedisNodeType.ROSTER);
        Jedis jedis = rc.getConnection();

        try {
            byte[] value = jedis.get(this.generateKey("roster", rosterId).getBytes());
            if(value != null && value.length != 0) {
                return new String(value);
            }
        } catch (Exception var9) {
              log.error("redis error", var9);
            rc.onException(jedis);
            return null;
        } finally {
            rc.onFinally(jedis);
        }

        return null;
    }

//    public int getkeysNum(String rosterId) {
//        RedisConnection rc = this.getConnection(RedisNodeType.ROSTER);
//        Jedis jedis = rc.getConnection();
//
//        Set value;
//        try {
//            value = jedis.keys(this.generateKey("roster", rosterId).getBytes());
//            if(value == null) {
//                return 0;
//            }
//        } catch (Exception var9) {
//            log.error("redis error", var9);
//            rc.onException(jedis);
//            return 0;
//        } finally {
//            rc.onFinally(jedis);
//        }
//
//        return value.size();
//    }

    public Set<String> getkeysNum(String rosterId) {
        RedisConnection rc = this.getConnection(RedisNodeType.ROSTER);
        Jedis jedis = rc.getConnection();

        Set<String> value;
        try {
            value = jedis.keys(rosterId);
            return value;
        } catch (Exception var9) {
            log.error("redis error", var9);
            rc.onException(jedis);
        } finally {
            rc.onFinally(jedis);
        }

        return null;
    }


    public boolean setCallRoster(String rosterId, String rosterInfo) {
        RedisConnection rc = this.getConnection(RedisNodeType.ROSTER);
        Jedis jedis = rc.getConnection();

        try {
            jedis.set(this.generateKey("roster", rosterId).getBytes(), rosterInfo.getBytes());
            return true;
        } catch (Exception var9) {
              log.error("redis error", var9);
            rc.onException(jedis);
        } finally {
            rc.onFinally(jedis);
        }

        return false;
    }

    public boolean expireCallRoster(String rosterId) {
        RedisConnection rc = this.getConnection(RedisNodeType.ROSTER);
        Jedis jedis = rc.getConnection();

        try {
            jedis.expire(this.generateKey("roster", rosterId).getBytes(), 240);
              log.info("####  redis销毁时间"+240);
            return true;
        } catch (Exception var8) {
              log.error("redis error", var8);
            rc.onException(jedis);
        } finally {
            rc.onFinally(jedis);
        }

        return false;
    }
    public boolean expireRosterId(String rosterId) {
        RedisConnection rc = this.getConnection(RedisNodeType.ROSTER);
        Jedis jedis = rc.getConnection();

        try {
            jedis.expire(rosterId, 240);
            log.info("####  rosterId销毁时间"+240);
            return true;
        } catch (Exception var8) {
            log.error("redis error", var8);
            rc.onException(jedis);
        } finally {
            rc.onFinally(jedis);
        }

        return false;
    }

    public Long delCallRoster(String rosterId) {
        RedisConnection rc = this.getConnection(RedisNodeType.ROSTER);
        Jedis jedis = rc.getConnection();
        Long value = null;

        Long var7;
        try {
            value = jedis.del(this.generateKey("roster", rosterId).getBytes());
            var7 = value;
            return var7;
        } catch (Exception var10) {
              log.error("redis error", var10);
            rc.onException(jedis);
            var7 = value;
        } finally {
            rc.onFinally(jedis);
        }

        return var7;
    }

    public boolean incr(String batchName) {
        RedisConnection rc = this.getConnection(RedisNodeType.ROSTER);
        Jedis jedis = rc.getConnection();

        try {
            jedis.incr(batchName);
            return true;
        } catch (Exception var9) {
              log.error("redis error", var9);
            rc.onException(jedis);
        } finally {
            rc.onFinally(jedis);
        }

        return false;
    }
    public boolean set(String batchName,String value) {
        RedisConnection rc = this.getConnection(RedisNodeType.ROSTER);
        Jedis jedis = rc.getConnection();

        try {
            jedis.set(batchName,value);
            return true;
        } catch (Exception var9) {
            log.error("redis error", var9);
            rc.onException(jedis);
        } finally {
            rc.onFinally(jedis);
        }

        return false;
    }


    public boolean incrBy(String batchName,Long value) {
        RedisConnection rc = this.getConnection(RedisNodeType.ROSTER);
        Jedis jedis = rc.getConnection();

        try {
            jedis.incrBy(batchName,value);
            return true;
        } catch (Exception var9) {
            log.error("redis error", var9);
            rc.onException(jedis);
        } finally {
            rc.onFinally(jedis);
        }

        return false;
    }
    public boolean delBatchId(String batchName) {
        RedisConnection rc = this.getConnection(RedisNodeType.ROSTER);
        Jedis jedis = rc.getConnection();

        try {
            jedis.del(batchName);
            return true;
        } catch (Exception var9) {
              log.error("redis error", var9);
            rc.onException(jedis);
        } finally {
            rc.onFinally(jedis);
        }

        return false;
    }

    public String getKey(String batchName) {
        RedisConnection rc = this.getConnection(RedisNodeType.ROSTER);
        Jedis jedis = rc.getConnection();

        String value;
        try {
            value = jedis.get(batchName);
        } catch (Exception var9) {
            log.error("redis error", var9);
            rc.onException(jedis);
            return null;
        } finally {
            rc.onFinally(jedis);
        }

        return value;
    }


    public Long decr(String key) {
        RedisConnection rc = this.getConnection(RedisNodeType.ROSTER);
        Jedis jedis = rc.getConnection();

        try {
            Long var8 = jedis.decr(key);
            return var8;
        } catch (Exception var10) {
            log.error("redis error", var10);
            rc.onException(jedis);
        } finally {
            rc.onFinally(jedis);
        }

        return Long.valueOf(0L);
    }

    public void sublishMsg(JedisPubSub jedisPubSub, String channels) {
        RedisConnection rc = this.getConnection(RedisNodeType.ROSTER);
        Jedis jedis = rc.getConnection();
        try {
            jedis.subscribe(jedisPubSub,channels);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                rc.onFinally(jedis);
            }
        }
    }

    public Set<String> keys(String key) {
        RedisConnection rc = this.getConnection(RedisNodeType.ROSTER);
        Jedis jedis = rc.getConnection();
        try {
            return jedis.keys(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                rc.onFinally(jedis);
            }
        }
        return null;
    }


    public Map<String, String> hgetAll(String key) {
        RedisConnection rc = this.getConnection(RedisNodeType.ROSTER);
        Jedis jedis = rc.getConnection();
        try {
            return jedis.hgetAll(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                rc.onFinally(jedis);
            }
        }
        return null;
    }

    public Set<String> hkeys(String key) {
        RedisConnection rc = this.getConnection(RedisNodeType.ROSTER);
        Jedis jedis = rc.getConnection();
        try {
            return jedis.hkeys(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                rc.onFinally(jedis);
            }
        }
        return null;
    }

    public Long hdel(String key,String fields) {
        RedisConnection rc = this.getConnection(RedisNodeType.ROSTER);
        Jedis jedis = rc.getConnection();
        try {
            return jedis.hdel(key,fields);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                rc.onFinally(jedis);
            }
        }
        return null;
    }

    public Long del(String keys) {
        RedisConnection rc = this.getConnection(RedisNodeType.ROSTER);
        Jedis jedis = rc.getConnection();
        try {
            return jedis.del(keys);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                rc.onFinally(jedis);
            }
        }
        return null;
    }



    public Long incr(String tag, Object key) {
        return this.incr(RedisNodeType.ROSTER, tag, key);
    }


    public Long incrBy(String tag, Object key, long value) {
        return this.incrBy(RedisNodeType.ROSTER, tag, key, value);
    }

    public boolean set(String tag, Object key, String value, int seconds) {
        return this.setex(RedisNodeType.ROSTER, tag, key, value, seconds);
    }

    public boolean set(String tag, Object key, String value) {
        return this.set(RedisNodeType.ROSTER, tag, key, value);
    }

    public boolean setnx(String tag, Object key, String value) {
        return this.setnx(RedisNodeType.ROSTER, tag, key, value);
    }

    public boolean set(String tag, Object key, Object value) {
        return this.set(tag, key, value.toString());
    }

    public String get(String tag, Object key) {
        return this.get(RedisNodeType.ROSTER, tag, key);
    }

    public Long getLong(String tag, Object key) {
        String value = this.get(tag, key);
        return value == null?null:Long.valueOf(Long.parseLong(value));
    }

    public Integer getInt(String tag, Object key) {
        String value = this.get(tag, key);
        return value == null?null:Integer.valueOf(Integer.parseInt(value));
    }

    public boolean del(String tag, Object key) {
        long ret = this.del(RedisNodeType.ROSTER, tag, key);
        return ret > 0L;
    }

    public boolean expire(String tag, Object key, int seconds) {
        return this.expire(RedisNodeType.ROSTER, tag, key, seconds);
    }

    public boolean expireAt(String tag, Object key, long unixTime) {
        return this.expireAt(RedisNodeType.ROSTER, tag, key, unixTime);
    }

    public Long ttl(String tag, Object key) {
        return this.ttl(RedisNodeType.ROSTER, tag, key);
    }

    public boolean isExist(String tag, Object key) {
        return this.isExist(RedisNodeType.ROSTER, tag, key);
    }

    public void setAskCount(String tag, long uid, int count, int seconds) {
        String key = this.generateKey(tag, Long.valueOf(uid));
        RedisConnection rc = this.getConnection(RedisNodeType.ROSTER);
        Jedis jedis = rc.getConnection();

        try {
            jedis.set(key, String.valueOf(count));
            jedis.expire(key, seconds);
        } catch (Exception var13) {
              log.error("redis error", var13);
            rc.onException(jedis);
        } finally {
            rc.onFinally(jedis);
        }

    }

    public Integer getAskCount(String tag, long uid) {
        String key = this.generateKey(tag, Long.valueOf(uid));
        RedisConnection rc = this.getConnection(RedisNodeType.ROSTER);
        Jedis jedis = rc.getConnection();

        try {
            if(jedis.exists(key).booleanValue()) {
                Integer var9 = Integer.valueOf(Integer.parseInt(jedis.get(key)));
                return var9;
            }

            return null;
        } catch (Exception var12) {
              log.error("redis error", var12);
            rc.onException(jedis);
        } finally {
            rc.onFinally(jedis);
        }

        return null;
    }

    public boolean incrByAskCount(String tag, long uid, int count, int seconds) {
        String key = this.generateKey(tag, Long.valueOf(uid));
        RedisConnection rc = this.getConnection(RedisNodeType.ROSTER);
        Jedis jedis = rc.getConnection();

        try {
            if(!jedis.exists(key).booleanValue()) {
                return false;
            }

            jedis.incrBy(key, (long)count);
            jedis.expire(key, seconds);
        } catch (Exception var13) {
              log.error("redis error", var13);
            rc.onException(jedis);
        } finally {
            rc.onFinally(jedis);
        }

        return true;
    }

    private String formatHashtag(String src, int start, int end) {
        if(src != null && src.length() >= end && start <= end && start >= 0 && end >= 0) {
            StringBuilder sb = new StringBuilder();
            if(start == 0) {
                sb.append("{");
            } else {
                sb.append(src.substring(0, start));
                sb.append("{");
            }

            sb.append(src.substring(start, end));
            sb.append("}");
            sb.append(src.substring(end, src.length()));
            return sb.toString();
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void setOriginalBuffer(IOriginalBuffer originalBuffer) {
        this.originalBuffer = originalBuffer;
    }

    public static class LimitRate {
        public int checkPeriod;
        public int checkTimes;
        public int banPeriod;

        public LimitRate(int checkPeriod, int checkTimes, int banPeriod) {
            this.checkPeriod = checkPeriod;
            this.checkTimes = checkTimes;
            this.banPeriod = banPeriod;
        }

        public LimitRate() {
        }
    }
}
