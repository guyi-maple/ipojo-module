package top.guyi.ipojo.module.helper.converter.defaults;

import tech.guyi.ipojo.application.annotation.Component;
import top.guyi.ipojo.module.helper.converter.TypeConverter;

@Component
public class DoubleBTypeConverter implements TypeConverter {

    @Override
    public Object convert(Object origin) {
        return origin == null ? 0 : Double.parseDouble(origin.toString());
    }

    @Override
    public String forType() {
        return double.class.getName();
    }

}
