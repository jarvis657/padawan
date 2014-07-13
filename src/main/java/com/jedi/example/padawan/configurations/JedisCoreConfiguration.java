package com.jedi.example.padawan.configurations;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;

/**
 * Created with IntelliJ IDEA.
 * User: ramachandra.as
 */
@Getter @Setter @AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class JedisCoreConfiguration {

    private String hostName;

    private int port = 6379;

    @Min(0)
    private int timeout = 2;

    public JedisCoreConfiguration(String hostDetails) throws Exception{
        String[] splits = splitConfiguration(hostDetails);
        this.hostName = splits[0];
        this.port = Integer.valueOf(splits[1]);
        this.timeout = Integer.valueOf(splits[2]);
    }

    private String[] splitConfiguration(String hostDetails) throws Exception{
        String[] splits = hostDetails.split(":");
        if(splits.length != 3) throw new IllegalAccessException();
        return splits;
    }
}
