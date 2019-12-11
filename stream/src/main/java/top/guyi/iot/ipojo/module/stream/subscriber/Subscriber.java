package top.guyi.iot.ipojo.module.stream.subscriber;

public interface Subscriber<T> {

    void subscription(T value);

}
