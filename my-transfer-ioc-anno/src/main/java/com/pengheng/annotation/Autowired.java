package com.pengheng.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE,ElementType.CONSTRUCTOR,ElementType.METHOD,ElementType.FIELD,ElementType.PARAMETER,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {
    String value() default "";
}
