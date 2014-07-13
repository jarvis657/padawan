package com.jedi.example.padawan.configurations;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ramachandra.as
 */

@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class JedisConfiguration {

    private JedisCoreConfiguration masterConfiguration;

    private List<JedisCoreConfiguration> slaveConfigurations = Lists.newArrayList();

    @Min(0)
    @Max(4096)
    private int maxActiveThreadsMaster;

    @Min(0)
    @Max(4096)
    private int maxActiveThreadsShards;

    public JedisConfiguration(String masterConfiguration, List<String> slaveConfigurations, int maxActiveThreadsMaster, int maxActiveThreadsShards) throws Exception{
        this.masterConfiguration = new JedisCoreConfiguration(masterConfiguration);
        for(String slave : slaveConfigurations){
            this.slaveConfigurations.add(new JedisCoreConfiguration(slave));
        }
        this.maxActiveThreadsMaster = maxActiveThreadsMaster;
        this.maxActiveThreadsShards = maxActiveThreadsShards;
    }
}
