package top.guyi.ipojo.module.h2.entry;

import lombok.Data;
import tech.guyi.ipojo.application.utils.StringUtils;
import top.guyi.ipojo.module.h2.entity.annotation.Column;
import top.guyi.ipojo.module.h2.entity.annotation.Id;
import top.guyi.ipojo.module.h2.entity.annotation.Table;
import top.guyi.ipojo.module.h2.entity.Entity;
import top.guyi.ipojo.module.h2.type.ColumnTypeConverter;
import top.guyi.ipojo.module.h2.where.SqlRuntimeException;

import java.lang.reflect.Field;
import java.util.*;

@Data
public class DbEntity {

    private FieldEntry id;
    private Class<? extends Entity> classes;
    private String tableName;
    private List<FieldEntry> fields;

    private Map<String,FieldEntry> fieldMap = new LinkedHashMap<>();
    public void format(){
        for (FieldEntry field : this.fields) {
            this.fieldMap.put(field.getName(),field);
        }
    }

    public String getColumnName(String fieldName){
        FieldEntry field = this.fieldMap.get(fieldName);
        if (field == null){
            return fieldName;
        }
        return field.getColumnName();
    }

    public static DbEntity from(Class<? extends Entity> entityClass){
        DbEntity entity = new DbEntity();
        entity.setClasses(entityClass);

        Table table = entityClass.getAnnotation(Table.class);
        if (table != null && !StringUtils.isEmpty(table.name())){
            entity.setTableName(table.name());
        }else{
            entity.setTableName(StringUtils.humpToUnderline(entityClass.getSimpleName()));
        }

        List<FieldEntry> fields = new LinkedList<>();
        for (Field field : entityClass.getDeclaredFields()) {
            Column column = field.getAnnotation(Column.class);
            if (column != null){
                FieldEntry entry = new FieldEntry();
                entry.setField(field);
                entry.setName(field.getName());
                entry.setType(field.getType());

                if (StringUtils.isEmpty(column.name())){
                    entry.setColumnName(StringUtils.humpToUnderline(field.getName()));
                }else {
                    entry.setColumnName(column.name());
                }

                ColumnTypeConverter converter = ColumnTypeConverter.getByType(field.getType());
                if (converter == null){
                    throw new SqlRuntimeException(String.format("不支持的字段类型[%s][%s](%s)",entityClass,field.getName(),field.getType()));
                }

                if (StringUtils.isEmpty(column.type())){
                    if (column.length() != -1){
                        entry.setColumnType(converter.getColumnTypeValue(column.length()));
                    }else{
                        entry.setColumnType(converter.getColumnTypeValue());
                    }
                }else{
                    entry.setColumnType(column.type());
                }
                entry.setConverter(converter);

                fields.add(entry);

                if (field.getAnnotation(Id.class) != null){
                    entity.id = entry;
                }
            }
        }

        entity.setFields(fields);

        entity.format();

        return entity;
    }

}
