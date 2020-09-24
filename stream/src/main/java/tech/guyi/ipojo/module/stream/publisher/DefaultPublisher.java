package tech.guyi.ipojo.module.stream.publisher;

import tech.guyi.ipojo.module.stream.Stream;
import tech.guyi.ipojo.module.stream.exception.StreamOpenException;

public class DefaultPublisher<T> implements Publisher<T> {

    private Stream<T> stream;
    public DefaultPublisher(Stream<T> stream) {
        this.stream = stream;
    }

    @Override
    public void publish(T value) {
        if (this.stream == null){
            throw new StreamOpenException();
        }
        this.stream.onNext(value);
    }

    @Override
    public void compile() {
        this.stream.close();
        this.stream = null;
    }

}
