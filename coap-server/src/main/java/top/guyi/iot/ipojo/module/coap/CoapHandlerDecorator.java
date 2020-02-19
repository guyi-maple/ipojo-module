package top.guyi.iot.ipojo.module.coap;

import com.google.gson.Gson;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.server.resources.CoapExchange;
import top.guyi.iot.ipojo.application.ApplicationContext;
import top.guyi.iot.ipojo.application.osgi.log.StaticLogger;
import top.guyi.iot.ipojo.module.coap.enums.CoapMethod;
import top.guyi.iot.ipojo.module.coap.utils.CoapCurrent;

import java.util.HashMap;
import java.util.Map;

public abstract class CoapHandlerDecorator extends CoapResource {

    private ApplicationContext applicationContext;
    private CoapCurrent current;

    public CoapHandlerDecorator(String name,ApplicationContext applicationContext) {
        super(name);
        this.applicationContext = applicationContext;
        this.current = applicationContext.get(CoapCurrent.class,true);
        this.registerAll();
    }

    private Map<CoapMethod,CoapResourceInvoker> invokers = new HashMap<>();
    protected void register(CoapMethod method, CoapResourceInvoker invoker){
        this.invokers.put(method,invoker);
    }

    protected abstract void registerAll();

    private static Gson gson = new Gson();

    @Override
    public void handleGET(CoapExchange exchange) {
        this.current.exchange(exchange);
        this.execute(exchange,CoapMethod.GET);
    }

    @Override
    public void handlePOST(CoapExchange exchange) {
        this.current.exchange(exchange);
        this.execute(exchange,CoapMethod.POST);
    }

    private void execute(CoapExchange exchange, CoapMethod method){
        CoapResourceInvoker invoker = this.invokers.get(method);
        if (invoker == null){
            exchange.respond(CoAP.ResponseCode.METHOD_NOT_ALLOWED);
            return;
        }
        this.execute(exchange,invoker);
    }

    private void execute(CoapExchange exchange,CoapResourceInvoker invoker) {
        StaticLogger.info("receive coap request [{}] [{}]",this.getURI(),exchange.getRequestText());
        Object args = this.getArgs(exchange,invoker);
        args = args == null ? exchange : args;
        Object result = invoker.invoke(applicationContext,args);
        String json = gson.toJson(result);
        StaticLogger.info("send coap response [{}] [{}]",this.getURI(),json);
        exchange.respond(json);
    }

    private Object getArgs(CoapExchange exchange, CoapResourceInvoker invoker){
        Class<?> type = invoker.argsClass();
        if (type == null){
            return null;
        }
        if (CoapExchange.class.isAssignableFrom(type)){
            return null;
        }
        String json = exchange.getRequestText();
        StaticLogger.info("receive coap request [{}] [{}]",this.getURI(),json);
        return gson.fromJson(json,type);
    }

}
