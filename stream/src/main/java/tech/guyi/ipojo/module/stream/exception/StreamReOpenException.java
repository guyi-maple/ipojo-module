package tech.guyi.ipojo.module.stream.exception;

public class StreamReOpenException extends RuntimeException {

    public StreamReOpenException(){
        super("已关闭的流无法重新开启");
    }

}
