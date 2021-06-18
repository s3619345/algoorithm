package com.sense.newots.redis.buffer;

import com.sense.newots.redis.RedisConnection;

public class AbstractBufferImpl {
    protected RedisConnection conn;

    public AbstractBufferImpl(RedisConnection conn) {
        if (conn == null) {
            throw new NullPointerException("RedisConnection is not null!");
        }
        this.conn = conn;
    }

    public RedisConnection getConn() {
        return this.conn;
    }

    public void setConn(RedisConnection conn) {
        this.conn = conn;
    }
}

