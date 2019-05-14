package io.mtc.servicelayer.annotation;

import java.lang.annotation.*;

/**
 * Created by majun on 2018/9/3.
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Path {
    String value() default "";
}
