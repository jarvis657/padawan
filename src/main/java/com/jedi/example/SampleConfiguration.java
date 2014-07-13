package com.jedi.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jedi.example.padawan.configurations.JedisConfiguration;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import com.yammer.dropwizard.config.Configuration;

/**
 * Created with IntelliJ IDEA.
 * User: ramachandra.as
 */
@Getter @Setter @AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SampleConfiguration extends Configuration{

    @JsonProperty
    @NotEmpty
    @NotNull
    private String master;

    @JsonProperty
    @NotEmpty
    @NotNull
    private List<String> shards;

    @Min(0)
    @Max(4096)
    private int masterActiveThreads;

    @Min(0)
    @Max(4096)
    private int shardActiveThreads;

    public JedisConfiguration getJedisConfiguration() throws Exception{
        return new JedisConfiguration(master, shards, masterActiveThreads, shardActiveThreads);
    }
}
