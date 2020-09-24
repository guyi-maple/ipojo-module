package top.guyi.ipojo.module.stream;

import top.guyi.ipojo.module.stream.annotation.Awaiter;
import top.guyi.ipojo.module.stream.async.Asynchronous;
import top.guyi.ipojo.module.stream.async.awaiter.FluxAwaiter;
import top.guyi.ipojo.module.stream.subscriber.Subscriber;

public class Test {

    @Awaiter
    private FluxAwaiter<String> awaiter;

    public void test(){
        awaiter.subscription(new Subscriber<String>() {
            @Override
            public void subscription(String value) {
                System.out.println(value);
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10 * 1000);
                    awaiter.publish("测试数据1");
                    Thread.sleep(10 * 1000);
                    awaiter.publish("测试数据2");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Asynchronous asynchronous = new Asynchronous();

        asynchronous.invoke("test text", new Runnable() {
            @Override
            public void run() {
                System.out.println("异步执行");
            }
        });

    }

}
