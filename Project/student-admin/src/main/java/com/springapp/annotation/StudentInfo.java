package com.springapp.annotation;

import java.lang.annotation.*;

/**
 * Created by ioana.diaconu on 2/26/2015.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface StudentInfo {
    String firstName() default "Test";
    String lastName() default "test";
    String age() default"20";
}
