package tech.guyi.ipojo.module.stream.async.awaiter;

import tech.guyi.ipojo.module.stream.Mono;
import tech.guyi.ipojo.module.stream.producer.Producer;
import tech.guyi.ipojo.module.stream.publisher.Publisher;
import tech.guyi.ipojo.module.stream.subscriber.Subscriber;

public class MonoAwaiter<T> {

    private Mono<T> mono;
    private Publisher<T> publisher;
    private T data;

    public static <T> MonoAwaiter<T> create(boolean sync){
        final MonoAwaiter<T> awaiter = new MonoAwaiter<>();
        awaiter.mono = Mono.create(new Producer<T>() {
            @Override
            public void produce(Publisher<T> publisher) {
                awaiter.publisher = publisher;
            }
        },sync);
        awaiter.mono.open();
        return awaiter;
    }

    public static <T> MonoAwaiter<T> create(){
        return create(false);
    }

    public void publish(T data){
        this.data = data;
        this.publisher.publish(this.data);
        this.mono = null;
        this.publisher = null;
    }

    public void subscription(Subscriber<T> subscriber){
        if (mono != null){
            this.mono.subscription(subscriber);
        }else{
            subscriber.subscription(this.data);
        }
    }

}
