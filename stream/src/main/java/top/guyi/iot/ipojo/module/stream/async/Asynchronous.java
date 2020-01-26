package top.guyi.iot.ipojo.module.stream.async;

import top.guyi.iot.ipojo.application.annotation.Component;
import top.guyi.iot.ipojo.application.annotation.Resource;

import java.util.concurrent.ExecutorService;

@Component
public class Asynchronous {

    @Resource
    private ExecutorService service;

    public <R> R invoke(R result,Runnable invoker){
        service.execute(invoker);
        return result;
    }

    public void invoke(Runnable invoker){
        service.execute(invoker);
    }

}
