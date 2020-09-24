package top.guyi.ipojo.module.stream;

import top.guyi.ipojo.module.stream.producer.Producer;
import top.guyi.ipojo.module.stream.publisher.DefaultPublisher;

public class Mono<T> extends AbstractStream<T> {

    public static <E> Mono<E> create(Producer<E> producer){
        return create(producer,false);
    }

    public static <E> Mono<E> create(Producer<E> producer,boolean sync){
        Mono<E> mono = new Mono<>();
        mono.setPublisher(new DefaultPublisher<>(mono));
        mono.setProducer(producer);
        mono.setSync(sync);
        return mono;
    }

    @Override
    public void onNext(T value) {
        super.onNext(value);
        super.close();
    }

}
