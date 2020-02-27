package top.guyi.iot.ipojo.module.helper.converter.defaults;

import top.guyi.iot.ipojo.application.annotation.Component;
import top.guyi.iot.ipojo.module.helper.converter.TypeConverter;

@Component
public class IntegerTypeConverter implements TypeConverter {

    @Override
    public Object convert(Object origin) {
        return origin == null ? null : origin instanceof Double ? ((Double) origin).intValue() : Integer.parseInt(origin.toString());
    }

    @Override
    public String forType() {
        return Integer.class.getName();
    }

}
