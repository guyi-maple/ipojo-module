package top.guyi.iot.ipojo.module.coap;

import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.network.CoapEndpoint;
import org.osgi.framework.BundleContext;
import top.guyi.iot.ipojo.application.ApplicationContext;
import top.guyi.iot.ipojo.application.bean.interfaces.ApplicationStartEvent;
import top.guyi.iot.ipojo.application.bean.interfaces.ApplicationStartSuccessEvent;

import java.net.InetSocketAddress;

public abstract class CoapServerManager implements ApplicationStartEvent, ApplicationStartSuccessEvent {

    protected abstract void registerMapping(CoapServer server,ApplicationContext context);

    private CoapServer server;

    @Override
    public void onStart(ApplicationContext applicationContext, BundleContext bundleContext) throws Exception {
        this.server = new CoapServer();
        server.addEndpoint(new CoapEndpoint(new InetSocketAddress("0.0.0.0",5683)));
        this.registerMapping(this.server,applicationContext);
    }

    @Override
    public void onStartSuccess(ApplicationContext applicationContext, BundleContext bundleContext) throws Exception {
        this.server.start();
        System.out.println("coap服务器启动成功");
    }
}
