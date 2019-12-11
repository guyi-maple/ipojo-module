package top.guyi.iot.ipojo.module.stream;

import top.guyi.iot.ipojo.module.stream.producer.Producer;
import top.guyi.iot.ipojo.module.stream.publisher.DefaultPublisher;

public class Flux<T> extends AbstractStream<T> {

    public static <E> Flux<E> create(Producer<E> producer){
        Flux<E> flux = new Flux<>();
        flux.setPublisher(new DefaultPublisher<>(flux));
        flux.setProducer(producer);
        return flux;
    }

}
