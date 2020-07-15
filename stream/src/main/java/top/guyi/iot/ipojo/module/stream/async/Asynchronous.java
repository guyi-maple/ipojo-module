package top.guyi.iot.ipojo.module.stream.async;

import top.guyi.iot.ipojo.application.annotation.Component;
import top.guyi.iot.ipojo.application.annotation.Resource;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class Asynchronous {

    @Resource
    private ScheduledExecutorService service;

    public <R> R invoke(R result,Runnable invoker){
        service.execute(invoker);
        return result;
    }

    public void invoke(Runnable invoker){
        service.execute(invoker);
    }

    public void invoke(long delay,Runnable runnable){
        this.invoke(delay,TimeUnit.SECONDS,runnable);
    }

    public void invoke(long delay,TimeUnit unit,Runnable runnable){
        service.schedule(runnable,delay,unit);
    }

}
