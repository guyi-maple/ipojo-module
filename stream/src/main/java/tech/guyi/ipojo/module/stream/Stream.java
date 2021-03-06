package tech.guyi.ipojo.module.stream;

import tech.guyi.ipojo.module.stream.subscriber.Subscriber;

public interface Stream<T> {

    void onNext(T value);

    <R extends Stream<T>> R subscription(Subscriber<T> subscriber);

    void open();

    void open(boolean runnable);

    void close();

}
