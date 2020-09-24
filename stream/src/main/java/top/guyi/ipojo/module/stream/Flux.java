package top.guyi.ipojo.module.stream;

import top.guyi.ipojo.module.stream.producer.Producer;
import top.guyi.ipojo.module.stream.publisher.DefaultPublisher;

public class Flux<T> extends AbstractStream<T> {

    public static <E> Flux<E> create(Producer<E> producer){
        return create(producer,false);
    }

    public static <E> Flux<E> create(Producer<E> producer,boolean sync){
        Flux<E> flux = new Flux<>();
        flux.setPublisher(new DefaultPublisher<>(flux));
        flux.setProducer(producer);
        flux.setSync(sync);
        return flux;
    }

}
