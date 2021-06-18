//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sense.newots.redis;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;
import redis.clients.jedis.Tuple;

import java.util.*;
import java.util.Map.Entry;

@Slf4j
public abstract class AbstractRedisDevice {
    private Map<com.sense.newots.redis.RedisNodeType, com.sense.newots.redis.RedisConnection> connMap = new HashMap();

    public AbstractRedisDevice() {
        com.sense.newots.redis.RedisNodeType[] var4;
        int var3 = (var4 = com.sense.newots.redis.RedisNodeType.values()).length;

        for (int var2 = 0; var2 < var3; ++var2) {
            com.sense.newots.redis.RedisNodeType type = var4[var2];
            com.sense.newots.redis.RedisConnection conn = new com.sense.newots.redis.RedisConnection(type.getProxyFull());
            this.connMap.put(type, conn);
        }

    }

    protected com.sense.newots.redis.RedisConnection getConnection(com.sense.newots.redis.RedisNodeType nodeType) {
        return (com.sense.newots.redis.RedisConnection) this.connMap.get(nodeType);
    }

    public static String combineKey(Object... keys) {
        StringBuilder sb = new StringBuilder();
        Object[] var5 = keys;
        int var4 = keys.length;

        for (int var3 = 0; var3 < var4; ++var3) {
            Object key = var5[var3];
            sb.append(key).append("|");
        }

        return sb.substring(0, sb.length() - 1);
    }

    public static String combineKey(String... keys) {
        StringBuilder sb = new StringBuilder();
        String[] var5 = keys;
        int var4 = keys.length;

        for (int var3 = 0; var3 < var4; ++var3) {
            String key = var5[var3];
            sb.append(key).append("|");
        }

        return sb.substring(0, sb.length() - 1);
    }

    protected String generateKey(String tag, Object key) {
        return key == null ? tag : tag + "|" + key;
    }

    protected String generateKey(String tag, Object... keys) {
        StringBuilder sb = new StringBuilder();
        sb.append(tag);
        Object[] var7 = keys;
        int var6 = keys.length;

        for (int var5 = 0; var5 < var6; ++var5) {
            Object key = var7[var5];
            sb.append("|").append(key);
        }

        return sb.toString();
    }

    public long del(com.sense.newots.redis.RedisNodeType nodeType, String tag, Object key) {
        com.sense.newots.redis.RedisConnection rc = this.getConnection(nodeType);
        Jedis jedis = rc.getConnection();

        try {
            long var8 = jedis.del(this.generateKey(tag, key)).longValue();
            return var8;
        } catch (Exception var12) {
            log.error("redis error", var12);
            rc.onException(jedis);
        } finally {
            rc.onFinally(jedis);
        }

        return 0L;
    }

    public boolean expire(com.sense.newots.redis.RedisNodeType nodeType, String tag, Object key, int seconds) {
        com.sense.newots.redis.RedisConnection rc = this.getConnection(nodeType);
        Jedis jedis = rc.getConnection();

        try {
            jedis.expire(this.generateKey(tag, key), seconds);
            return true;
        } catch (Exception var11) {
            log.error("redis error", var11);
            rc.onException(jedis);
        } finally {
            rc.onFinally(jedis);
        }

        return false;
    }

    public boolean expireAt(com.sense.newots.redis.RedisNodeType nodeType, String tag, Object key, long unixTime) {
        com.sense.newots.redis.RedisConnection rc = this.getConnection(nodeType);
        Jedis jedis = rc.getConnection();

        try {
            jedis.expireAt(this.generateKey(tag, key), unixTime);
            return true;
        } catch (Exception var12) {
            log.error("redis error", var12);
            rc.onException(jedis);
        } finally {
            rc.onFinally(jedis);
        }

        return false;
    }

    public Long ttl(com.sense.newots.redis.RedisNodeType nodeType, String tag, Object key) {
        com.sense.newots.redis.RedisConnection rc = this.getConnection(nodeType);
        Jedis jedis = rc.getConnection();

        try {
            Long var8 = jedis.ttl(this.generateKey(tag, key));
            return var8;
        } catch (Exception var11) {
            log.error("redis error", var11);
            rc.onException(jedis);
        } finally {
            rc.onFinally(jedis);
        }

        return null;
    }

    public boolean isExist(com.sense.newots.redis.RedisNodeType nodeType, String tag, Object key) {
        com.sense.newots.redis.RedisConnection rc = this.getConnection(nodeType);
        Jedis jedis = rc.getConnection();

        try {
            boolean var8 = jedis.exists(this.generateKey(tag, key)).booleanValue();
            return var8;
        } catch (Exception var11) {
            log.error("redis error", var11);
            rc.onException(jedis);
        } finally {
            rc.onFinally(jedis);
        }

        return false;
    }

    public String type(com.sense.newots.redis.RedisNodeType nodeType, String tag, Object key) {
        com.sense.newots.redis.RedisConnection rc = this.getConnection(nodeType);
        Jedis jedis = rc.getConnection();

        try {
            String var8 = jedis.type(this.generateKey(tag, key));
            return var8;
        } catch (Exception var11) {
            log.error("redis error", var11);
            rc.onException(jedis);
        } finally {
            rc.onFinally(jedis);
        }

        return null;
    }

    public Long incr(com.sense.newots.redis.RedisNodeType nodeType, String tag, Object key) {
        com.sense.newots.redis.RedisConnection rc = this.getConnection(nodeType);
        Jedis jedis = rc.getConnection();

        try {
            Long var8 = jedis.incr(this.generateKey(tag, key));
            return var8;
        } catch (Exception var11) {
            log.error(var11.getMessage(), var11);
            rc.onException(jedis);
        } finally {
            rc.onFinally(jedis);
        }

        return Long.valueOf(0L);
    }

    public Long incrBy(com.sense.newots.redis.RedisNodeType nodeType, String tag, Object key, long value) {
        com.sense.newots.redis.RedisConnection rc = this.getConnection(nodeType);
        Jedis jedis = rc.getConnection();

        try {
            Long var10 = jedis.incrBy(this.generateKey(tag, key), value);
            return var10;
        } catch (Exception var13) {
            log.error("redis error", var13);
            rc.onException(jedis);
        } finally {
            rc.onFinally(jedis);
        }

        return null;
    }

    public Long decr(com.sense.newots.redis.RedisNodeType nodeType, String tag, Object key) {
        com.sense.newots.redis.RedisConnection rc = this.getConnection(nodeType);
        Jedis jedis = rc.getConnection();

        try {
            Long var8 = jedis.decr(this.generateKey(tag, key));
            return var8;
        } catch (Exception var11) {
            log.error(var11.getMessage(), var11);
            rc.onException(jedis);
        } finally {
            rc.onFinally(jedis);
        }

        return Long.valueOf(0L);
    }

    public boolean setex(com.sense.newots.redis.RedisNodeType nodeType, String tag, Object key, String value, int expireSeconds) {
        com.sense.newots.redis.RedisConnection rc = this.getConnection(nodeType);
        Jedis jedis = rc.getConnection();

        try {
            String e = jedis.setex(this.generateKey(tag, key), expireSeconds, value);
            boolean var10 = "OK".equals(e);
            return var10;
        } catch (Exception var13) {
            log.error("redis error", var13);
            rc.onException(jedis);
        } finally {
            rc.onFinally(jedis);
        }

        return false;
    }

    public boolean set(com.sense.newots.redis.RedisNodeType nodeType, String tag, Object key, String value) {
        com.sense.newots.redis.RedisConnection rc = this.getConnection(nodeType);
        Jedis jedis = rc.getConnection();

        try {
            String e = jedis.set(this.generateKey(tag, key), value);
            boolean var9 = "OK".equals(e);
            return var9;
        } catch (Exception var12) {
            log.error("redis error", var12);
            rc.onException(jedis);
        } finally {
            rc.onFinally(jedis);
        }

        return false;
    }

    public boolean set(com.sense.newots.redis.RedisNodeType nodeType, String tag, Object key, Object value) {
        return this.set(nodeType, tag, key, value.toString());
    }

    public boolean setnx(com.sense.newots.redis.RedisNodeType nodeType, String tag, Object key, String value) {
        com.sense.newots.redis.RedisConnection rc = this.getConnection(nodeType);
        Jedis jedis = rc.getConnection();

        try {
            Long e = jedis.setnx(this.generateKey(tag, key), value);
            boolean var9 = e.longValue() == 1L;
            return var9;
        } catch (Exception var12) {
            log.error("redis error", var12);
            rc.onException(jedis);
        } finally {
            rc.onFinally(jedis);
        }

        return false;
    }

    public String get(com.sense.newots.redis.RedisNodeType nodeType, String tag, Object key) {
        com.sense.newots.redis.RedisConnection rc = this.getConnection(nodeType);
        Jedis jedis = rc.getConnection();

        try {
            String var8 = jedis.get(this.generateKey(tag, key));
            return var8;
        } catch (Exception var11) {
            log.error("redis error", var11);
            rc.onException(jedis);
        } finally {
            rc.onFinally(jedis);
        }

        return null;
    }

    public byte[] getBytes(com.sense.newots.redis.RedisNodeType nodeType, String tag, Object key) {
        com.sense.newots.redis.RedisConnection rc = this.getConnection(nodeType);
        Jedis jedis = rc.getConnection();

        try {
            byte[] var8 = jedis.get(this.generateKey(tag, key).getBytes());
            return var8;
        } catch (Exception var11) {
            log.error("redis error", var11);
            rc.onException(jedis);
        } finally {
            rc.onFinally(jedis);
        }

        return null;
    }

    public Long getLong(com.sense.newots.redis.RedisNodeType nodeType, String tag, Object key) {
        String value = this.get(nodeType, tag, key);
        return value == null ? null : Long.valueOf(Long.parseLong(value));
    }

    public Integer getInt(com.sense.newots.redis.RedisNodeType nodeType, String tag, Object key) {
        String value = this.get(nodeType, tag, key);
        return value == null ? null : Integer.valueOf(Integer.parseInt(value));
    }

    public long sadd(com.sense.newots.redis.RedisNodeType nodeType, String tag, Object key, String... values) {
        com.sense.newots.redis.RedisConnection rc = this.getConnection(nodeType);
        Jedis jedis = rc.getConnection();

        try {
            long var9 = jedis.sadd(this.generateKey(tag, key), values).longValue();
            return var9;
        } catch (Exception var13) {
            log.error("redis error", var13);
            rc.onException(jedis);
        } finally {
            rc.onFinally(jedis);
        }

        return 0L;
    }

    public long srem(com.sense.newots.redis.RedisNodeType nodeType, String tag, Object key, String... values) {
        com.sense.newots.redis.RedisConnection rc = this.getConnection(nodeType);
        Jedis jedis = rc.getConnection();

        try {
            long var9 = jedis.srem(this.generateKey(tag, key), values).longValue();
            return var9;
        } catch (Exception var13) {
            log.error("redis error", var13);
            rc.onException(jedis);
        } finally {
            rc.onFinally(jedis);
        }

        return 0L;
    }

    public boolean sismember(com.sense.newots.redis.RedisNodeType nodeType, String tag, Object key, String value) {
        com.sense.newots.redis.RedisConnection rc = this.getConnection(nodeType);
        Jedis jedis = rc.getConnection();

        try {
            boolean var9 = jedis.sismember(this.generateKey(tag, key), value).booleanValue();
            return var9;
        } catch (Exception var12) {
            log.error("redis error", var12);
            rc.onException(jedis);
        } finally {
            rc.onFinally(jedis);
        }

        return false;
    }

    public boolean sismember(com.sense.newots.redis.RedisNodeType nodeType, String tag, Object key, String... values) {
        com.sense.newots.redis.RedisConnection rc = this.getConnection(nodeType);
        Jedis jedis = rc.getConnection();

        try {
            Pipeline e = jedis.pipelined();
            ArrayList retList = new ArrayList();
            String combinedKey = this.generateKey(tag, key);
            String[] var13 = values;
            int var12 = values.length;

            for (int var11 = 0; var11 < var12; ++var11) {
                String ret = var13[var11];
                Response ret1 = e.sismember(combinedKey, ret);
                retList.add(ret1);
            }

            e.sync();
            Iterator var21 = retList.iterator();

            while (var21.hasNext()) {
                Response var20 = (Response) var21.next();
                if (!((Boolean) var20.get()).booleanValue()) {
                    return false;
                }
            }
        } catch (Exception var18) {
            log.error("redis error", var18);
            rc.onException(jedis);
            return false;
        } finally {
            rc.onFinally(jedis);
        }

        return true;
    }

    public long scard(com.sense.newots.redis.RedisNodeType nodeType, String tag, Object key) {
        com.sense.newots.redis.RedisConnection rc = this.getConnection(nodeType);
        Jedis jedis = rc.getConnection();

        try {
            long var8 = jedis.scard(this.generateKey(tag, key)).longValue();
            return var8;
        } catch (Exception var12) {
            log.error("redis error", var12);
            rc.onException(jedis);
        } finally {
            rc.onFinally(jedis);
        }

        return 0L;
    }

    public Set<String> smembers(com.sense.newots.redis.RedisNodeType nodeType, String tag, Object key) {
        com.sense.newots.redis.RedisConnection rc = this.getConnection(nodeType);
        Jedis jedis = rc.getConnection();

        try {
            Set var8 = jedis.smembers(this.generateKey(tag, key));
            return var8;
        } catch (Exception var11) {
            log.error("redis error", var11);
            rc.onException(jedis);
        } finally {
            rc.onFinally(jedis);
        }

        return null;
    }

    public Map<String, Long> scard(com.sense.newots.redis.RedisNodeType nodeType, String tag, Collection<?> keys) {
        com.sense.newots.redis.RedisConnection rc = this.getConnection(nodeType);
        Jedis jedis = rc.getConnection();

        try {
            HashMap e = new HashMap();
            Pipeline pl = jedis.pipelined();
            Iterator entry = keys.iterator();

            while (entry.hasNext()) {
                Object retMap2 = entry.next();
                String theKey = this.generateKey(tag, retMap2);
                Response ret = pl.scard(theKey);
                e.put(theKey, ret);
            }

            pl.sync();
            HashMap retMap21 = new HashMap();
            Iterator theKey1 = e.entrySet().iterator();

            while (theKey1.hasNext()) {
                Entry entry1 = (Entry) theKey1.next();
                retMap21.put((String) entry1.getKey(), (Long) ((Response) entry1.getValue()).get());
            }

            HashMap var13 = retMap21;
            return var13;
        } catch (Exception var16) {
            log.error("redis error", var16);
            rc.onException(jedis);
        } finally {
            rc.onFinally(jedis);
        }

        return null;
    }

    public Map<String, Set<String>> smembers(com.sense.newots.redis.RedisNodeType nodeType, String tag, Collection<?> keys) {
        com.sense.newots.redis.RedisConnection rc = this.getConnection(nodeType);
        Jedis jedis = rc.getConnection();

        try {
            HashMap e = new HashMap();
            Pipeline pl = jedis.pipelined();
            Iterator entry = keys.iterator();

            while (entry.hasNext()) {
                Object retMap2 = entry.next();
                String theKey = this.generateKey(tag, retMap2);
                Response ret = pl.smembers(theKey);
                e.put(theKey, ret);
            }

            pl.sync();
            HashMap retMap21 = new HashMap();
            Iterator theKey1 = e.entrySet().iterator();

            while (theKey1.hasNext()) {
                Entry entry1 = (Entry) theKey1.next();
                retMap21.put((String) entry1.getKey(), (Set) ((Response) entry1.getValue()).get());
            }

            HashMap var13 = retMap21;
            return var13;
        } catch (Exception var16) {
            log.error("redis error", var16);
            rc.onException(jedis);
        } finally {
            rc.onFinally(jedis);
        }

        return null;
    }

    public Map<String, Double> zrangeWithScores(com.sense.newots.redis.RedisNodeType nodeType, String tag, Object key, long start, long end) {
        com.sense.newots.redis.RedisConnection rc = this.getConnection(nodeType);
        Jedis jedis = rc.getConnection();

        try {
            Set e = jedis.zrangeWithScores(this.generateKey(tag, key), start, end);
            LinkedHashMap retMap = new LinkedHashMap();
            Iterator var13 = e.iterator();

            while (var13.hasNext()) {
                Tuple tuple = (Tuple) var13.next();
                retMap.put(tuple.getElement(), Double.valueOf(tuple.getScore()));
            }

            LinkedHashMap var15 = retMap;
            return var15;
        } catch (Exception var18) {
            log.error("redis error", var18);
            rc.onException(jedis);
        } finally {
            rc.onFinally(jedis);
        }

        return null;
    }

    public Double zscore(com.sense.newots.redis.RedisNodeType nodeType, String tag, Object key, String member) {
        com.sense.newots.redis.RedisConnection rc = this.getConnection(nodeType);
        Jedis jedis = rc.getConnection();

        try {
            Double var9 = jedis.zscore(this.generateKey(tag, key), member);
            return var9;
        } catch (Exception var12) {
            log.error("redis error", var12);
            rc.onException(jedis);
        } finally {
            rc.onFinally(jedis);
        }

        return null;
    }

    public Object evalsha(com.sense.newots.redis.RedisNodeType nodeType, String script, List<String> keys, String... args) {
        com.sense.newots.redis.RedisConnection rc = this.getConnection(nodeType);
        Jedis jedis = rc.getConnection();

        try {
            Object var9 = jedis.evalsha(script, keys, Arrays.asList(args));
            return var9;
        } catch (Exception var12) {
            log.error("redis error", var12);
            rc.onException(jedis);
        } finally {
            rc.onFinally(jedis);
        }

        return null;
    }
}
