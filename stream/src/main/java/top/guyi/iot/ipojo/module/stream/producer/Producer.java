package top.guyi.iot.ipojo.module.stream.producer;

import top.guyi.iot.ipojo.module.stream.publisher.Publisher;

public interface Producer<T> {

    void produce(Publisher<T> publisher);

}
