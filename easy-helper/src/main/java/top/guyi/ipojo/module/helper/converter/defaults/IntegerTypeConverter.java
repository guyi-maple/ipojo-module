package top.guyi.ipojo.module.helper.converter.defaults;

import tech.guyi.ipojo.application.annotation.Component;
import top.guyi.ipojo.module.helper.converter.TypeConverter;

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
