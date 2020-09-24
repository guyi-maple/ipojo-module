package top.guyi.ipojo.module.helper.converter;

import tech.guyi.ipojo.application.component.ForType;

public interface TypeConverter extends ForType<String> {

    Object convert(Object origin);

}
