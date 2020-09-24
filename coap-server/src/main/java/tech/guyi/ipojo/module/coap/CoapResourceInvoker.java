package tech.guyi.ipojo.module.coap;

import tech.guyi.ipojo.application.ApplicationContext;

public interface CoapResourceInvoker {

    Class<?> argsClass();

    Object invoke(ApplicationContext context,Object args);

}
