package tech.guyi.ipojo.module.h2.where.condition.builder;

import tech.guyi.ipojo.module.h2.entity.Entity;
import tech.guyi.ipojo.module.h2.where.WhereBuilder;
import tech.guyi.ipojo.module.h2.where.condition.type.WhereConditionType;

import java.util.Map;

public class OrWhereConditionBuilder<E extends Entity> extends WhereConditionBuilder<E> {

    public OrWhereConditionBuilder(WhereBuilder<E> where, Map<String, WhereConditionType> types, String field, Object value){
        super(where,types,field,value);
    }

    public OrWhereConditionBuilder(WhereBuilder<E> where,Map<String, WhereConditionType> types) {
        super(where,types);
    }

    @Override
    protected String getConnectSql() {
        return "or";
    }

}
