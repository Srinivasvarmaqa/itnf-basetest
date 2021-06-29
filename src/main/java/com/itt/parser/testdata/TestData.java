package com.itt.parser.testdata;

import java.lang.annotation.*;

/**
 * Custom TestData annotation
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface TestData {
    String[] dataFilePath() default "";
}