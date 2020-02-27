package top.guyi.iot.ipojo.module.helper.converter.defaults;

import top.guyi.iot.ipojo.application.annotation.Component;
import top.guyi.iot.ipojo.module.helper.converter.TypeConverter;

@Component
public class BooleanTypeConverter implements TypeConverter {

    @Override
    public Object convert(Object origin) {
        return origin == null ? null : Boolean.parseBoolean(origin.toString());
    }

    @Override
    public String forType() {
        return Boolean.class.getName();
    }

}
