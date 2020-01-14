package top.guyi.iot.ipojo.module.stream;

import lombok.Getter;
import lombok.Setter;
import top.guyi.iot.ipojo.application.annotation.Component;
import top.guyi.iot.ipojo.application.annotation.Resource;
import top.guyi.iot.ipojo.application.bean.interfaces.InitializingBean;
import top.guyi.iot.ipojo.module.stream.exception.StreamOpenException;
import top.guyi.iot.ipojo.module.stream.exception.StreamReOpenException;
import top.guyi.iot.ipojo.module.stream.producer.Producer;
import top.guyi.iot.ipojo.module.stream.publisher.Publisher;
import top.guyi.iot.ipojo.module.stream.subscriber.Subscriber;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;

@Component(order = 800)
public class AbstractStream<T> implements Stream<T>, InitializingBean {

    protected static ExecutorService service;

    @Resource
    @Setter
    protected ExecutorService executorService;
    @Override
    public void afterPropertiesSet() {
        AbstractStream.service = this.executorService;
    }

    @Setter
    private Publisher<T> publisher;

    @Setter
    private Producer<T> producer;
    private List<Subscriber<T>> subscribers = new LinkedList<>();

    @Getter
    @Setter
    private boolean sync;

    @Override
    public <R extends Stream<T>> R  subscription(Subscriber<T> subscriber){
        this.subscribers.add(subscriber);
        return (R) this;
    }

    @Override
    public void open() {
        this.open(false);
    }

    @Override
    public void open(boolean runnable) {
        if (this.producer == null){
            throw new StreamReOpenException();
        }
        if (runnable){
            AbstractStream.service.execute(new Runnable() {
                @Override
                public void run() {
                    producer.produce(publisher);
                }
            });
        }else {
            producer.produce(publisher);
        }
    }

    @Override
    public void close() {
        this.producer = null;
        this.subscribers = null;
        this.publisher = null;
    }

    @Override
    public void onNext(final T value) {
        if (this.producer == null){
            throw new StreamOpenException();
        }
        for (final Subscriber<T> subscriber : this.subscribers) {
            if (this.sync){
                subscriber.subscription(value);
            }else{
                AbstractStream.service.execute(new Runnable() {
                    @Override
                    public void run() {
                        subscriber.subscription(value);
                    }
                });
            }
        }
    }

}
