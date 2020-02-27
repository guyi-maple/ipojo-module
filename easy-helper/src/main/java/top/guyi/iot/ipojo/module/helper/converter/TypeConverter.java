package top.guyi.iot.ipojo.module.helper.converter;

import top.guyi.iot.ipojo.application.component.ForType;

public interface TypeConverter extends ForType<String> {

    Object convert(Object origin);

}
