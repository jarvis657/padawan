package com.jedi.example.padawan.core.strategies;

import com.jedi.example.padawan.configurations.JedisConfiguration;
import com.yammer.dropwizard.config.Configuration;

/**
 * Created with IntelliJ IDEA.
 * User: ramachandra.as
 */
public interface ConfigurationInterface<T extends Configuration> {

    JedisConfiguration getJedisConfiguration(T configuration) throws Exception;

}
