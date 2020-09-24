package tech.guyi.ipojo.module.stream.async.awaiter;

import tech.guyi.ipojo.module.stream.Flux;
import tech.guyi.ipojo.module.stream.producer.Producer;
import tech.guyi.ipojo.module.stream.publisher.Publisher;
import tech.guyi.ipojo.module.stream.subscriber.Subscriber;

public class FluxAwaiter<T> {

    private Flux<T> flux;
    private Publisher<T> publisher;
    private T data;

    public static <T> FluxAwaiter<T> create(boolean sync){
        final FluxAwaiter<T> awaiter = new FluxAwaiter<>();
        awaiter.flux = Flux.create(new Producer<T>() {
            @Override
            public void produce(Publisher<T> publisher) {
                awaiter.publisher = publisher;
            }
        },sync);
        awaiter.flux.open();
        return awaiter;
    }

    public static <T> FluxAwaiter<T> create(){
        return create(false);
    }

    public void publish(T data){
        this.data = data;
        this.publisher.publish(this.data);
    }

    public void subscription(Subscriber<T> subscriber){
        if (flux != null){
            this.flux.subscription(subscriber);
        }else{
            subscriber.subscription(this.data);
        }
    }

}
