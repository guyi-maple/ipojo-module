package tech.guyi.ipojo.module.stream.subscriber;

public interface Subscriber<T> {

    void subscription(T value);

}
