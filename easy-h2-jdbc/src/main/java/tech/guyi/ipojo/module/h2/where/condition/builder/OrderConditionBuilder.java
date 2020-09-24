package tech.guyi.ipojo.module.h2.where.condition.builder;

import tech.guyi.ipojo.module.h2.entity.Entity;
import tech.guyi.ipojo.module.h2.where.WhereBuilder;
import tech.guyi.ipojo.module.h2.where.condition.type.WhereConditionType;

import java.util.Map;

public class OrderConditionBuilder<E extends Entity> extends WhereConditionBuilder<E> {

    public OrderConditionBuilder(WhereBuilder<E> where, Map<String, WhereConditionType> types, String field, Object value){
        super(where,types,field,value);
    }

    public OrderConditionBuilder(WhereBuilder<E> where, Map<String, WhereConditionType> types) {
        super(where,types);
    }

    @Override
    public int orderNum() {
        return 1;
    }

    @Override
    protected String getConnectSql() {
        return " ";
    }

}
