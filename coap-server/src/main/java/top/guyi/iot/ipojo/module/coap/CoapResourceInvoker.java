package top.guyi.iot.ipojo.module.coap;

import top.guyi.iot.ipojo.application.ApplicationContext;

public interface CoapResourceInvoker {

    Class<?> argsClass();

    Object invoke(ApplicationContext context,Object args);

}
