package com.jedi.example.padawan.core.redis;

import com.google.inject.Singleton;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * Created with IntelliJ IDEA.
 * User: ramachandra.as
 */
@Singleton
public class JedisShardedPool{
    private JedisPool jedisPool;
    private ShardedJedisPool shardedJedisPool;


    public JedisShardedPool(JedisPool jedisPool, ShardedJedisPool shardedJedisPool){
        this.jedisPool = jedisPool;
        this.shardedJedisPool = shardedJedisPool;
    }

    public void destroy(){
         jedisPool.destroy();
         shardedJedisPool.destroy();
    }

    public void ping() throws Exception{
        final Jedis conn = jedisPool.getResource();
        try{
            conn.ping();
        }finally {
            returnWriteResource(conn);
        }
    }

    public Jedis getWriteResource(){
       return jedisPool.getResource();
    }

    public void returnWriteResource(Jedis jedis){
        if(jedis != null){
            jedisPool.returnResource(jedis);
        }
    }

    public void returnBrokenWriteResource(Jedis jedis){
        if(jedis != null){
            jedisPool.returnBrokenResource(jedis);
            jedis = null;
        }
    }

    public ShardedJedis getReadResource(){
        return shardedJedisPool.getResource();
    }

    public void returnReadResource(ShardedJedis shardedJedis){
        if(shardedJedis != null){
            shardedJedisPool.returnResource(shardedJedis);
        }
    }

    public void returnReadBrokenResource(ShardedJedis shardedJedis){
        if(shardedJedis != null){
            shardedJedisPool.returnBrokenResource(shardedJedis);
            shardedJedis = null;
        }
    }
}
