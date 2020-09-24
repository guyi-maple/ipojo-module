package top.guyi.ipojo.module.helper.converter.defaults;

import tech.guyi.ipojo.application.annotation.Component;
import top.guyi.ipojo.module.helper.converter.TypeConverter;

@Component
public class BooleanBTypeConverter implements TypeConverter {

    @Override
    public Object convert(Object origin) {
        return origin != null && Boolean.parseBoolean(origin.toString());
    }

    @Override
    public String forType() {
        return boolean.class.getName();
    }

}
