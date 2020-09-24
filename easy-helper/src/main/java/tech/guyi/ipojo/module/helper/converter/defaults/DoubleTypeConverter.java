package tech.guyi.ipojo.module.helper.converter.defaults;

import tech.guyi.ipojo.application.annotation.Component;
import tech.guyi.ipojo.module.helper.converter.TypeConverter;

@Component
public class DoubleTypeConverter implements TypeConverter {

    @Override
    public Object convert(Object origin) {
        return origin == null ? null : Double.parseDouble(origin.toString());
    }

    @Override
    public String forType() {
        return Double.class.getName();
    }

}
