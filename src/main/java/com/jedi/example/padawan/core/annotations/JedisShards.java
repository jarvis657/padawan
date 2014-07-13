package com.jedi.example.padawan.core.annotations;

import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: ramachandra.as
 */
@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JedisShards {
}
