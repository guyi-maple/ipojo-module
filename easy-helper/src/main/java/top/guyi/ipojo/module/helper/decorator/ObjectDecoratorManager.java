package top.guyi.ipojo.module.helper.decorator;

import tech.guyi.ipojo.application.annotation.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author guyi
 * 对象装饰器管理者
 */
@Component
public class ObjectDecoratorManager {

    private final Map<Class<?>,ObjectDecorator> decorators = new HashMap<>();

    public <T> void set(Class<T> classes,ObjectDecorator<T> decorator){
        this.decorators.put(classes,decorator);
    }

    public <T> T decoration(T object){
        Class<?> classes = object.getClass();
        if (this.decorators.containsKey(classes)){
            return (T) this.decorators.get(classes).decoration(object);
        }
        for (Map.Entry<Class<?>, ObjectDecorator> entry : this.decorators.entrySet()) {
            if (entry.getKey().isAssignableFrom(classes)){
                return (T) entry.getValue().decoration(object);
            }
        }
        return object;
    }

}
