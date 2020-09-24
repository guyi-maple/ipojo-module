package tech.guyi.ipojo.module.helper.decorator;

/**
 * @author guyi
 * 对象装饰器
 */
public interface ObjectDecorator<T> {

    T decoration(T object);

}
