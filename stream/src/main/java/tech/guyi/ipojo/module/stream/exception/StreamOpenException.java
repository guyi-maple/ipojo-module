package tech.guyi.ipojo.module.stream.exception;

public class StreamOpenException extends RuntimeException {

    public StreamOpenException(){
        super("流未开启或已被关闭");
    }

}
