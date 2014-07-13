package com.jedi.example;

import com.jedi.example.padawan.JedisBundle;
import com.jedi.example.padawan.configurations.JedisConfiguration;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.assets.AssetsBundle;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.views.ViewBundle;

/**
 * Created with IntelliJ IDEA.
 * User: ramachandra.as
 */
public class SampleService extends Service<ExampleConfiguration> {

    private JedisBundle<ExampleConfiguration> jedisBundle = new JedisBundle<ExampleConfiguration>() {
        @Override
        public JedisConfiguration getJedisConfiguration(ExampleConfiguration configuration) throws Exception{
            return configuration.getSample().getJedisConfiguration();
        }
    };

    @Override
    public void initialize(Bootstrap<ExampleConfiguration> bootstrap) {
        bootstrap.setName("dropwizard-jedis-shards");
        bootstrap.addBundle(jedisBundle);
        bootstrap.addBundle(new ViewBundle());
        bootstrap.addBundle(new AssetsBundle("/assets/", "/"));
    }

    @Override
    public void run(ExampleConfiguration configuration, Environment environment) throws Exception {
        environment.addResource(SampleResource.class);
    }

    public static void main(String args[]) throws Exception{
        new SampleService().run(args);
    }
}
