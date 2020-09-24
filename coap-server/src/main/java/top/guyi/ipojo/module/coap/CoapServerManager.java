package top.guyi.ipojo.module.coap;

import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.network.CoapEndpoint;
import org.osgi.framework.BundleContext;
import tech.guyi.ipojo.application.ApplicationContext;
import tech.guyi.ipojo.application.bean.interfaces.ApplicationStartEvent;
import tech.guyi.ipojo.application.bean.interfaces.ApplicationStartSuccessEvent;
import tech.guyi.ipojo.application.bean.interfaces.ApplicationStopEvent;
import tech.guyi.ipojo.application.osgi.log.StaticLogger;

import java.net.InetSocketAddress;
import java.util.concurrent.ScheduledExecutorService;

public abstract class CoapServerManager implements ApplicationStartEvent, ApplicationStartSuccessEvent, ApplicationStopEvent {

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
        this.server.setExecutor(applicationContext.get(ScheduledExecutorService.class,true));
        this.server.start();
        StaticLogger.info("coap server start success {}",this.server.getEndpoints().get(0).getUri());
    }

    @Override
    public void onStop(ApplicationContext applicationContext, BundleContext bundleContext) {
        if (this.server != null){
            this.server.stop();
            this.server = null;
        }
    }
}
