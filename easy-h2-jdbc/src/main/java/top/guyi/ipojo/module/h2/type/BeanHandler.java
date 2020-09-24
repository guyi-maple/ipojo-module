package top.guyi.ipojo.module.h2.type;

import org.apache.commons.dbutils.ResultSetHandler;
import tech.guyi.ipojo.application.utils.ReflectUtils;
import top.guyi.ipojo.module.h2.entity.Entity;
import top.guyi.ipojo.module.h2.entry.DbEntity;
import top.guyi.ipojo.module.h2.entry.FieldEntry;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BeanHandler<E extends Entity> implements ResultSetHandler<E> {

    private Class<E> entityClass;
    private DbEntity dbEntity;

    public BeanHandler(Class<E> entityClass, DbEntity dbEntity) {
        this.entityClass = entityClass;
        this.dbEntity = dbEntity;
    }

    @Override
    public E handle(ResultSet rs) throws SQLException {
        if (rs.next()){
            try {
                E entity = this.entityClass.cast(this.dbEntity.getClasses().newInstance());
                for (FieldEntry field : dbEntity.getFields()) {
                    ReflectUtils.setFieldValue(
                            entity,
                            field.getField(),
                            field.getConverter()
                                    .getResultSetTypeConverter()
                                    .convert(field.getColumnName(),rs));
                }
                return entity;
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
