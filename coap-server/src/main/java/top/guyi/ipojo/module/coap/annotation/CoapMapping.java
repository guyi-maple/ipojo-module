package top.guyi.ipojo.module.coap.annotation;

import top.guyi.ipojo.module.coap.enums.CoapMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CoapMapping {

    String path();

    CoapMethod method() default CoapMethod.POST;

}
