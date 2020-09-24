package tech.guyi.ipojo.module.stream.publisher;

public interface Publisher<T> {

    void publish(T value);

    void compile();

}
