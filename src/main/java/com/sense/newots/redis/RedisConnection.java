package com.sense.newots.redis;
import com.sense.newots.object.conf.InitConfig;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;
@Slf4j
public class RedisConnection {
    private String type;
    private List<String> redisHostList;
    private String currentHost;
    private static final JedisPoolConfig config = new JedisPoolConfig();
    private JedisPool jedisPool;

    protected static ReentrantLock lockJedis = new ReentrantLock();

    static {
        config.setMaxIdle(100);
        config.setMaxActive(100);
        config.setMaxWait(3000L);
        config.setTestWhileIdle(false);
    }

    public RedisConnection(String type) {
        this.type = type;
        parseHostList();
        initPool();
    }

    private void parseHostList() {
        String list = InitConfig.DB_URL;
        if (list == null) {
            log.warn("no redis host, type: " + this.type);
            return;
        }

        this.redisHostList = new ArrayList(Arrays.asList(list.split("\\s")));
    }

    private void initPool() {
        if (this.redisHostList == null) {
            return;
        }
        if (this.redisHostList.isEmpty()) {
            log.warn("reload redis host, type: " + this.type);
            parseHostList();
        }


        log.info("init pool, type: " + this.type + ", addr: " + this.redisHostList);
        Random rand = new Random();
        this.currentHost = ((String) this.redisHostList.get(rand.nextInt(this.redisHostList.size())));
        String[] tmp = this.currentHost.split(":");
        String ip = tmp[0];

        int port = Integer.valueOf(tmp[1]).intValue();
        try {
            int timeout = 3000;
            if ((InitConfig.DB_PWD == null) || (InitConfig.DB_PWD.length() == 0))
                this.jedisPool = new JedisPool(config, ip, port, timeout);
            else {
                this.jedisPool = new JedisPool(config, ip, port, timeout, InitConfig.DB_PWD);
            }
            log.info("connection : [" + getConnection() + "]");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public Jedis getConnection() {
        lockJedis.lock();
        Jedis jedis = null;
        try {
            jedis = this.jedisPool.getResource();
        } catch (JedisConnectionException e) {
            log.error(e.getMessage(), e);
            this.redisHostList.remove(this.currentHost);
            this.currentHost = null;
        } finally {
            lockJedis.unlock();
        }
        return jedis;
    }


    public void onFinally(Jedis jedis) {
        if (jedis != null) {
            if (jedis.getClient().isInMulti()) {
                jedis.getClient().discard();
                jedis.disconnect();
            }
            if (this.jedisPool != null)
                this.jedisPool.returnResource(jedis);
        }
    }

    public void onException(Jedis jedis) {
        if (jedis != null) {
            if (jedis.getClient().isInMulti()) {
                jedis.getClient().discard();
                jedis.disconnect();
            }

            if (this.jedisPool != null)
                this.jedisPool.returnBrokenResource(jedis);
        }
    }

    public static void main(String[] args)
            throws Exception {
    }
}

