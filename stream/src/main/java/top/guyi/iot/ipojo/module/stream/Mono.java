package top.guyi.iot.ipojo.module.stream;

import top.guyi.iot.ipojo.module.stream.producer.Producer;
import top.guyi.iot.ipojo.module.stream.publisher.DefaultPublisher;

public class Mono<T> extends AbstractStream<T> {

    public static <E> Mono<E> create(Producer<E> producer){
        Mono<E> mono = new Mono<>();
        mono.setPublisher(new DefaultPublisher<>(mono));
        mono.setProducer(producer);
        return mono;
    }

    @Override
    public void onNext(T value) {
        super.onNext(value);
        super.close();
    }

}
