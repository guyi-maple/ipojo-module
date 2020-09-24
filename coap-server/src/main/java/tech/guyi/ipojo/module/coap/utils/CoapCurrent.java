package tech.guyi.ipojo.module.coap.utils;

import org.eclipse.californium.core.server.resources.CoapExchange;

public class CoapCurrent {

    private ThreadLocal<CoapExchange> exchange = new ThreadLocal<>();
    private ThreadLocal<String> clientIp = new ThreadLocal<>();

    public CoapExchange exchange(){
        return this.exchange.get();
    }
    public void exchange(CoapExchange exchange){
        this.exchange.set(exchange);
        this.clientIp.set(exchange.getSourceAddress().getHostAddress());
    }

    public String clientIp(){
        return this.clientIp.get();
    }

}
