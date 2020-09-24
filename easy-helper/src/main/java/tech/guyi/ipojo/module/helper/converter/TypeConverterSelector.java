package tech.guyi.ipojo.module.helper.converter;

import tech.guyi.ipojo.application.annotation.Component;
import tech.guyi.ipojo.application.annotation.Resource;

import java.util.Map;

@Component
public class TypeConverterSelector {

    @Resource
    private Map<String,TypeConverter> converters;

    public <T> T convert(Class<T> classes,Object value){
        if (this.converters.containsKey(classes.getName())){
            return classes.cast(this.converters.get(classes.getName()).convert(value));
        }
        throw new RuntimeException(String.format("not found type converter [%s]",classes));
    }

    public Object convertOrReturn(Class<?> classes,Object value){
        if (this.converters.containsKey(classes.getName())){
            return this.converters.get(classes.getName()).convert(value);
        }
        return value;
    }

}
