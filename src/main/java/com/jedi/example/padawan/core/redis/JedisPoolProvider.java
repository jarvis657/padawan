package com.jedi.example.padawan.core.redis;

import com.jedi.example.padawan.core.annotations.JedisShards;
import com.sun.jersey.spi.inject.SingletonTypeInjectableProvider;

import javax.ws.rs.ext.Provider;

/**
 * Created with IntelliJ IDEA.
 * User: ramachandra.as
 */
@Provider
public class JedisPoolProvider extends SingletonTypeInjectableProvider<JedisShards, JedisShardedPool>{

    /**
     * Construct a new instance with the Type and the instance.
     *
     * @param instance the instance.
     */

    public JedisPoolProvider(JedisShardedPool instance) {
        super(JedisShardedPool.class, instance);
    }
}
