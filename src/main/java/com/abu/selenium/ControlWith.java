package com.abu.selenium;

import com.abu.utils.PostActions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ControlWith {

    String css() default "";

    String xpath() default "";

    String name() default "";

    String className() default "";

    String linkText() default "";

    String partialLinkText() default "";

    String tagName() default "";

    String id() default "";

    PostActions[] postActions() default PostActions.NONE;

    String description();
}
