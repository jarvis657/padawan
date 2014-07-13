package com.jedi.example;

import com.yammer.dropwizard.config.Configuration;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: ramachandra.as
 */
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class ExampleConfiguration extends Configuration {

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    private SampleConfiguration sample;
}
