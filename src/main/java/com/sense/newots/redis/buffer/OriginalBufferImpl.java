package com.sense.newots.redis.buffer;
import com.sense.newots.redis.RedisConnection;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;
import java.util.Set;
@Slf4j
public class OriginalBufferImpl extends AbstractBufferImpl
        implements IOriginalBuffer {

    public OriginalBufferImpl(RedisConnection conn) {
        super(conn);
    }

    public String set(String key, String value) {
        Jedis jedis = this.conn.getConnection();
        try {
            String ret = jedis.set(key, value);
            return ret;
        } catch (Exception e) {
            log.error("redis set error", e);
            this.conn.onException(jedis);
            return null;
        } finally {
            this.conn.onFinally(jedis);
        }
    }

    public String get(String key) {
        Jedis jedis = this.conn.getConnection();
        try {
            String ret = jedis.get(key);
            return ret;
        } catch (Exception e) {
            log.error("redis get error", e);
            this.conn.onException(jedis);
            return null;
        } finally {
            this.conn.onFinally(jedis);
        }
    }

    public Long hset(String key, String field, String value) {
        Jedis jedis = this.conn.getConnection();
        try {
            Long ret = jedis.hset(key, field, value);
            return ret;
        } catch (Exception e) {
            log.error("redis hset error", e);
            this.conn.onException(jedis);
            return null;
        } finally {
            this.conn.onFinally(jedis);
        }
    }

    public String hget(String key, String field) {
        Jedis jedis = this.conn.getConnection();
        try {
            String ret = jedis.hget(key, field);
            return ret;
        } catch (Exception e) {
            log.error("redis hget error", e);
            this.conn.onException(jedis);
            return null;
        } finally {
            this.conn.onFinally(jedis);
        }
    }

    public Long hsetnx(String key, String field, String value) {
        Jedis jedis = this.conn.getConnection();
        try {
            Long ret = jedis.hsetnx(key, field, value);
            return ret;
        } catch (Exception e) {
            log.error("redis hsetnx error", e);
            this.conn.onException(jedis);
            return null;
        } finally {
            this.conn.onFinally(jedis);
        }
    }

    public String hmset(String key, Map<String, String> hash) {
        Jedis jedis = this.conn.getConnection();
        try {
            String ret = jedis.hmset(key, hash);
            return ret;
        } catch (Exception e) {
            log.error("redis hmset error", e);
            this.conn.onException(jedis);
            return null;
        } finally {
            this.conn.onFinally(jedis);
        }
    }

    public List<String> hmget(String key, String[] fields) {
        Jedis jedis = this.conn.getConnection();
        try {
            List ret = jedis.hmget(key, fields);
            return ret;
        } catch (Exception e) {
            log.error("redis hmget error", e);
            this.conn.onException(jedis);
            return null;
        } finally {
            this.conn.onFinally(jedis);
        }
    }

    public Long hincrBy(String key, String field, long value) {
        Jedis jedis = this.conn.getConnection();
        try {
            Long ret = jedis.hincrBy(key, field, value);
            return ret;
        } catch (Exception e) {
            log.error("redis hincrBy error", e);
            this.conn.onException(jedis);
            return null;
        } finally {
            this.conn.onFinally(jedis);
        }
    }

    public Boolean hexists(String key, String field) {
        Jedis jedis = this.conn.getConnection();
        try {
            Boolean ret = jedis.hexists(key, field);
            return ret;
        } catch (Exception e) {
            log.error("redis hexists error", e);
            this.conn.onException(jedis);
            return null;
        } finally {
            this.conn.onFinally(jedis);
        }
    }

    public Long hdel(String key, String field) {
        Jedis jedis = this.conn.getConnection();
        try {
            Long ret = jedis.hdel(key, new String[]{field});
            return ret;
        } catch (Exception e) {
            log.error("redis hdel error", e);
            this.conn.onException(jedis);
            return null;
        } finally {
            this.conn.onFinally(jedis);
        }
    }

    public Long hlen(String key) {
        Jedis jedis = this.conn.getConnection();
        try {
            Long ret = jedis.hlen(key);
            return ret;
        } catch (Exception e) {
            log.error("redis hlen error", e);
            this.conn.onException(jedis);
            return null;
        } finally {
            this.conn.onFinally(jedis);
        }
    }

    public Set<String> hkeys(String key) {
        Jedis jedis = this.conn.getConnection();
        try {
            Set ret = jedis.hkeys(key);
            return ret;
        } catch (Exception e) {
            log.error("redis hkeys error", e);
            this.conn.onException(jedis);
            return null;
        } finally {
            this.conn.onFinally(jedis);
        }
    }

    public List<String> hvals(String key) {
        Jedis jedis = this.conn.getConnection();
        try {
            List ret = jedis.hvals(key);
            return ret;
        } catch (Exception e) {
            log.error("redis hvals error", e);
            this.conn.onException(jedis);
            return null;
        } finally {
            this.conn.onFinally(jedis);
        }
    }

    public Map<String, String> hgetAll(String key) {
        Jedis jedis = this.conn.getConnection();
        try {
            Map ret = jedis.hgetAll(key);
            return ret;
        } catch (Exception e) {
            log.error("redis hgetAll error", e);
            this.conn.onException(jedis);
            return null;
        } finally {
            this.conn.onFinally(jedis);
        }
    }

    public Boolean exists(String key) {
        Jedis jedis = this.conn.getConnection();
        try {
            Boolean ret = jedis.exists(key);
            return ret;
        } catch (Exception e) {
            log.error("redis exists error", e);
            this.conn.onException(jedis);
            return null;
        } finally {
            this.conn.onFinally(jedis);
        }
    }

    public Long del(String key) {
        Jedis jedis = this.conn.getConnection();
        try {
            Long ret = jedis.del(key);
            return ret;
        } catch (Exception e) {
            log.error("redis del error", e);
            this.conn.onException(jedis);
            return null;
        } finally {
            this.conn.onFinally(jedis);
        }
    }

    public Long expire(String key, int seconds) {
        Jedis jedis = this.conn.getConnection();
        try {
            Long ret = jedis.expire(key, seconds);
            return ret;
        } catch (Exception e) {
            log.error("redis expire error", e);
            this.conn.onException(jedis);
            return null;
        } finally {
            this.conn.onFinally(jedis);
        }
    }

    public Long expireAt(String key, long unixTime) {
        Jedis jedis = this.conn.getConnection();
        try {
            Long ret = jedis.expireAt(key, unixTime);
            return ret;
        } catch (Exception e) {
            log.error("redis expireAt error", e);
            this.conn.onException(jedis);
            return null;
        } finally {
            this.conn.onFinally(jedis);
        }
    }

    public Long lpush(String key, String value) {
        Jedis jedis = this.conn.getConnection();
        try {
            Long ret = jedis.lpush(key, new String[]{value});
            return ret;
        } catch (Exception e) {
            log.error("redis lpush error", e);
            this.conn.onException(jedis);
            return null;
        } finally {
            this.conn.onFinally(jedis);
        }
    }

    public Long llen(String key) {
        Jedis jedis = this.conn.getConnection();
        try {
            Long ret = jedis.llen(key);
            return ret;
        } catch (Exception e) {
            log.error("redis llen error", e);
            this.conn.onException(jedis);
            return null;
        } finally {
            this.conn.onFinally(jedis);
        }
    }

    public List<String> lrange(String key, long start, long end) {
        Jedis jedis = this.conn.getConnection();
        try {
            List ret = jedis.lrange(key, start, end);
            return ret;
        } catch (Exception e) {
            log.error("redis lrange error", e);
            this.conn.onException(jedis);
            return null;
        } finally {
            this.conn.onFinally(jedis);
        }
    }

    public String ltrim(String key, long start, long end) {
        Jedis jedis = this.conn.getConnection();
        try {
            String ret = jedis.ltrim(key, start, end);
            return ret;
        } catch (Exception e) {
            log.error("redis ltrim error", e);
            this.conn.onException(jedis);
            return null;
        } finally {
            this.conn.onFinally(jedis);
        }
    }

    public String lindex(String key, long index) {
        Jedis jedis = this.conn.getConnection();
        try {
            String ret = jedis.lindex(key, index);
            return ret;
        } catch (Exception e) {
            log.error("redis lindex error", e);
            this.conn.onException(jedis);
            return null;
        } finally {
            this.conn.onFinally(jedis);
        }
    }

    public Long lrem(String key, long count, String value) {
        Jedis jedis = this.conn.getConnection();
        try {
            Long ret = jedis.lrem(key, count, value);
            return ret;
        } catch (Exception e) {
            log.error("redis lrem error", e);
            this.conn.onException(jedis);
            return null;
        } finally {
            this.conn.onFinally(jedis);
        }
    }

    public Long sadd(String key, String member) {
        Jedis jedis = this.conn.getConnection();
        try {
            Long ret = jedis.sadd(key, new String[]{member});
            return ret;
        } catch (Exception e) {
            log.error("redis sadd error", e);
            this.conn.onException(jedis);
            return null;
        } finally {
            this.conn.onFinally(jedis);
        }
    }

    public Set<String> smembers(String key) {
        Jedis jedis = this.conn.getConnection();
        try {
            Set ret = jedis.smembers(key);
            return ret;
        } catch (Exception e) {
            log.error("redis smembers error", e);
            this.conn.onException(jedis);
            return null;
        } finally {
            this.conn.onFinally(jedis);
        }
    }

    public Long srem(String key, String member) {
        Jedis jedis = this.conn.getConnection();
        try {
            Long ret = jedis.srem(key, new String[]{member});
            return ret;
        } catch (Exception e) {
            log.error("redis srem error", e);
            this.conn.onException(jedis);
            return null;
        } finally {
            this.conn.onFinally(jedis);
        }
    }

    public String spop(String key) {
        Jedis jedis = this.conn.getConnection();
        try {
            String ret = jedis.spop(key);
            return ret;
        } catch (Exception e) {
            log.error("redis spop error", e);
            this.conn.onException(jedis);
            return null;
        } finally {
            this.conn.onFinally(jedis);
        }
    }

    public Long smove(String srckey, String dstkey, String member) {
        Jedis jedis = this.conn.getConnection();
        try {
            Long ret = jedis.smove(srckey, dstkey, member);
            return ret;
        } catch (Exception e) {
            log.error("redis smove error", e);
            this.conn.onException(jedis);
            return null;
        } finally {
            this.conn.onFinally(jedis);
        }
    }

    public Long scard(String key) {
        Jedis jedis = this.conn.getConnection();
        try {
            Long ret = jedis.scard(key);
            return ret;
        } catch (Exception e) {
            log.error("redis scard error", e);
            this.conn.onException(jedis);
            return null;
        } finally {
            this.conn.onFinally(jedis);
        }
    }

    public Boolean sismember(String key, String member) {
        Jedis jedis = this.conn.getConnection();
        try {
            Boolean ret = jedis.sismember(key, member);
            return ret;
        } catch (Exception e) {
            log.error("redis sismember error", e);
            this.conn.onException(jedis);
            return null;
        } finally {
            this.conn.onFinally(jedis);
        }
    }

    public Long zadd(String key, double score, String member) {
        Jedis jedis = this.conn.getConnection();
        try {
            Long ret = jedis.zadd(key, score, member);
            return ret;
        } catch (Exception e) {
            log.error("redis zadd error", e);
            this.conn.onException(jedis);
            return null;
        } finally {
            this.conn.onFinally(jedis);
        }
    }

    public Set<String> zrange(String key, int start, int end) {
        Jedis jedis = this.conn.getConnection();
        try {
            Set ret = jedis.zrange(key, start, end);
            return ret;
        } catch (Exception e) {
            log.error("redis zrange error", e);
            this.conn.onException(jedis);
            return null;
        } finally {
            this.conn.onFinally(jedis);
        }
    }

    public Long zrem(String key, String member) {
        Jedis jedis = this.conn.getConnection();
        try {
            Long ret = jedis.zrem(key, new String[]{member});
            return ret;
        } catch (Exception e) {
            log.error("redis zrem error", e);
            this.conn.onException(jedis);
            return null;
        } finally {
            this.conn.onFinally(jedis);
        }
    }

    public Double zincrby(String key, double score, String member) {
        Jedis jedis = this.conn.getConnection();
        try {
            Double ret = jedis.zincrby(key, score, member);
            return ret;
        } catch (Exception e) {
            log.error("redis zincrby error", e);
            this.conn.onException(jedis);
            return null;
        } finally {
            this.conn.onFinally(jedis);
        }
    }

    public Long zcount(String key, double min, double max) {
        Jedis jedis = this.conn.getConnection();
        try {
            Long ret = jedis.zcount(key, min, max);
            return ret;
        } catch (Exception e) {
            log.error("redis zcount error", e);
            this.conn.onException(jedis);
            return null;
        } finally {
            this.conn.onFinally(jedis);
        }
    }

    public Set<String> zrangeByScore(String key, double min, double max) {
               Jedis jedis = this.conn.getConnection();
        try {
            Set ret = jedis.zrangeByScore(key, min, max);
            return ret;
        } catch (Exception e) {
            log.error("redis zrangeByScore error", e);
            this.conn.onException(jedis);
            return null;
        } finally {
            this.conn.onFinally(jedis);
        }
    }

    public Set<String> zrangeByScore(String key, String min, String max) {
        Jedis jedis = this.conn.getConnection();
        try {
            Set ret = jedis.zrangeByScore(key, min, max);
            return ret;
        } catch (Exception e) {
            log.error("redis zrangeByScore error", e);
            this.conn.onException(jedis);
            return null;
        } finally {
            this.conn.onFinally(jedis);
        }
    }

    public Set<String> zrangeByScore(String key, double min, double max, int offset, int count) {
        Jedis jedis = this.conn.getConnection();
        try {
            Set ret = jedis.zrangeByScore(key, min, max, offset, count);
            return ret;
        } catch (Exception e) {
            log.error("redis zrangeByScore error", e);
            this.conn.onException(jedis);
            return null;
        } finally {
            this.conn.onFinally(jedis);
        }
    }

    public Set<String> zrevrangeByScore(String key, double max, double min) {
        Jedis jedis = this.conn.getConnection();
        try {
            Set ret = jedis.zrevrangeByScore(key, max, min);

            return ret;
        } catch (Exception e) {
            log.error("redis zrevrangeByScore error", e);
            this.conn.onException(jedis);
            return null;
        } finally {
            this.conn.onFinally(jedis);
        }
    }

    public Set<String> zrevrangeByScore(String key, double max, double min, int offset, int count) {
        Jedis jedis = this.conn.getConnection();
        try {
            Set ret = jedis.zrevrangeByScore(key, max, min, offset,
                    count);
            return ret;
        } catch (Exception e) {
            log.error("redis zrevrangeByScore error", e);
            this.conn.onException(jedis);
            return null;
        } finally {
            this.conn.onFinally(jedis);
        }
    }

    public Double zscore(String key, String member) {
        Jedis jedis = this.conn.getConnection();
        try {
            Double ret = jedis.zscore(key, member);
            return ret;
        } catch (Exception e) {
            log.error("redis zscore error", e);
            this.conn.onException(jedis);
            return null;
        } finally {
            this.conn.onFinally(jedis);
        }
    }

    public void subscribe(String channel) {
    }

    public Long publish(String channel, String message) {
        Jedis jedis = this.conn.getConnection();
        try {
            Long ret = jedis.publish(channel, message);
            return ret;
        } catch (Exception e) {
            log.error("redis publish error", e);
            this.conn.onException(jedis);
            return null;
        } finally {
            this.conn.onFinally(jedis);
        }
    }
}
