package top.guyi.iot.ipojo.module.h2.type;

import org.apache.commons.dbutils.ResultSetHandler;
import top.guyi.iot.ipojo.application.utils.ReflectUtils;
import top.guyi.iot.ipojo.module.h2.entity.Entity;
import top.guyi.iot.ipojo.module.h2.entry.DbEntity;
import top.guyi.iot.ipojo.module.h2.entry.FieldEntry;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class BeanListHandler<E extends Entity> implements ResultSetHandler<List<E>> {

    private Class<E> entityClass;
    private DbEntity dbEntity;

    public BeanListHandler(Class<E> entityClass, DbEntity dbEntity) {
        this.entityClass = entityClass;
        this.dbEntity = dbEntity;
    }

    @Override
    public List<E> handle(ResultSet rs) throws SQLException {
        List<E> list = new LinkedList<>();
        while (rs.next()){
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
                list.add(entity);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

}
