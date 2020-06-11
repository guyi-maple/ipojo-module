
## Mono

单次使用的流，当推送一个元素后，流就会被关闭。

``` java

private Publisher<String> monoPublisher;

public void test(){
    Mono<String> mono = Mono.create(new Producer<String>(){
        @Override
        public void produce(Publisher<String> publisher) {
            monoPublisher = publisher;
        }
    });
    mono.open();
    mono.subscription(new Subscriber<String>() {
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
                monoPublisher.publish("测试数据");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }).start();
}
```

上面的示例是一个标准的使用方式，如果觉得繁琐，可以使用注解 [@Awaiter](../stream/src/main/java/top/guyi/iot/ipojo/module/stream/annotation/Awaiter.java) 快速创建一个Mono流。

``` java
@Awaiter
private MonoAwaiter<String> mono;

public void test(){
    mono.subscription(new Subscriber<String>() {
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
                mono.publish("测试数据");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }).start();
}
```

## Flux 

可以多次使用的流，使用方式与Mono基本相同。

``` java
private Publisher<String> fluxPublisher;

public void test(){
    Flux<String> flux = Flux.create(new Producer<String>(){
        @Override
        public void produce(Publisher<String> publisher) {
            fluxPublisher = publisher;
        }
    });
    flux.open();
    flux.subscription(new Subscriber<String>() {
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
                fluxPublisher.publish("测试数据1");
                Thread.sleep(10 * 1000);
                fluxPublisher.publish("测试数据2");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }).start();

}
```

``` java
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

}
```

## [Asynchronous](../stream/src/main/java/top/guyi/iot/ipojo/module/stream/async/Asynchronous.java)

使用此组件可以完成先返回数据随后异步执行代码的逻辑。

``` java
@Resource
private Asynchronous asynchronous;

public String test(){
    return this.asynchronous.invoke("test text", new Runnable() {
        @Override
        public void run() {
            System.out.println("异步执行");
        }
    });
}
```


