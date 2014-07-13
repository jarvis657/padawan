package com.jedi.example.padawan;

import com.google.common.collect.Lists;
import com.jedi.example.padawan.configurations.JedisConfiguration;
import com.jedi.example.padawan.configurations.JedisCoreConfiguration;
import com.jedi.example.padawan.core.healthcheck.JedisHealthCheck;
import com.jedi.example.padawan.core.redis.JedisPoolProvider;
import com.jedi.example.padawan.core.redis.JedisShardedPool;
import com.jedi.example.padawan.core.strategies.ConfigurationInterface;
import com.yammer.dropwizard.ConfiguredBundle;
import com.yammer.dropwizard.config.Configuration;
import com.yammer.dropwizard.lifecycle.Managed;
import lombok.Getter;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

import java.util.List;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
/**
 * Created with IntelliJ IDEA.
 * User: ramachandra.as
 */
@Getter
public abstract class JedisBundle<T extends Configuration> implements ConfiguredBundle<T>, ConfigurationInterface<T>{
    private JedisConfiguration jedisConfiguration;
    private JedisShardedPool jedisPool;

   @Override
   public void initialize(Bootstrap<?> bootstrap){}

   @Override
   public void run(T configuration, Environment environment) throws Exception{
       JedisConfiguration conf = getJedisConfiguration(configuration);
       jedisPool = new JedisShardedPool(provideJedisPool(conf), provideShardedJedisPool(conf));
       environment.addHealthCheck(new JedisHealthCheck("jedis-health", jedisPool));
       environment.manage(new Managed() {
           @Override
           public void start() throws Exception {}

           @Override
           public void stop() throws Exception {
               jedisPool.destroy();
           }
       });
       environment.addProvider(new JedisPoolProvider(jedisPool));
   }

    /**
     *
     * @param jedisConfiguration
     * @return
     */
   private JedisPool provideJedisPool(JedisConfiguration jedisConfiguration){
       JedisCoreConfiguration master = jedisConfiguration.getMasterConfiguration();
       JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
       jedisPoolConfig.setMaxTotal(jedisConfiguration.getMaxActiveThreadsMaster());
       return new JedisPool(jedisPoolConfig, master.getHostName(), master.getPort(), master.getTimeout());
   }

    /**
     * Uses MURMUR hash by default
     * @param jedisConfiguration
     * @return
     */
    private ShardedJedisPool provideShardedJedisPool(JedisConfiguration jedisConfiguration){
        List<JedisCoreConfiguration> slaves = jedisConfiguration.getSlaveConfigurations();
        List<JedisShardInfo> shards = Lists.newArrayList();
        for(JedisCoreConfiguration slave : slaves){
            shards.add( new JedisShardInfo(slave.getHostName(), slave.getPort(), slave.getTimeout()));
        }
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(jedisConfiguration.getMaxActiveThreadsShards());
        return new ShardedJedisPool(poolConfig, shards);
    }
}
