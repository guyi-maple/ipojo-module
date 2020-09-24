package tech.guyi.ipojo.module.helper.converter.defaults;

import tech.guyi.ipojo.application.annotation.Component;
import tech.guyi.ipojo.module.helper.converter.TypeConverter;

@Component
public class IntTypeConverter implements TypeConverter {

    @Override
    public Object convert(Object origin) {
        return origin == null ? null : origin instanceof Double ? ((Double) origin).intValue() : Integer.parseInt(origin.toString());
    }

    @Override
    public String forType() {
        return int.class.getName();
    }

}
