package top.guyi.iot.ipojo.module.helper.converter.defaults;

import top.guyi.iot.ipojo.application.annotation.Component;
import top.guyi.iot.ipojo.module.helper.converter.TypeConverter;

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
