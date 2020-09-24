package tech.guyi.ipojo.module.stream.producer;

import tech.guyi.ipojo.module.stream.publisher.Publisher;

public interface Producer<T> {

    void produce(Publisher<T> publisher);

}
