package com.springapp.annotation;

import java.lang.annotation.*;
import java.util.IllegalFormatException;

/**
 * Created by ioana.diaconu on 2/25/2015.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
@Documented
public @interface CourseInfo {
    String name() default "";
    CourseState state() default CourseState.UPCOMING;
    String dateAdded() default "[unimplemented]";
    Class expected() default IllegalAccessError.class;
}
