package top.guyi.iot.ipojo.module.h2.entry;

import lombok.Data;
import top.guyi.iot.ipojo.module.h2.type.ColumnTypeConverter;

import java.lang.reflect.Field;

@Data
public class FieldEntry {

    private Field field;
    private String name;
    private Class<?> type;
    private String columnType;
    private String columnName;
    private ColumnTypeConverter converter;

}
