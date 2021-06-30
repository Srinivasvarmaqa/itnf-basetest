package com.itt.testrail;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom TestRail annotation.
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface TestRail {
    public static final String UNCLASSIFIED_SECTION_PATH = "Unclassified";
    int projectId() default 0;
    int suiteId() default 0;
    int sectionId() default 0;
    int[] caseId() default 0;
}
