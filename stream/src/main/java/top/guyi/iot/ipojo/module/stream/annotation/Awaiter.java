package top.guyi.iot.ipojo.module.stream.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface Awaiter {

    /**
     * 是否同步执行
     * @return 是否同步执行
     */
    boolean sync() default false;

}
