package com.jedi.example.padawan.core.healthcheck;

import com.yammer.metrics.core.HealthCheck;
import com.jedi.example.padawan.core.redis.JedisShardedPool;

/**
 * Created with IntelliJ IDEA.
 * User: ramachandra.as
 */
public class JedisHealthCheck extends HealthCheck{
    private JedisShardedPool jedisShardedPool;

    public JedisHealthCheck(String name, JedisShardedPool jedisShardedPool){
        super(name);
        this.jedisShardedPool = jedisShardedPool;
    }

    @Override
    protected Result check() throws Exception {
        try{
            jedisShardedPool.ping();
            return HealthCheck.Result.healthy();
        }catch (Exception e){
            return HealthCheck.Result.unhealthy(e);
        }
    }
}
