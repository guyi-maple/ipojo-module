package tech.guyi.ipojo.module.h2.where.condition.builder;

import tech.guyi.ipojo.module.h2.entity.Entity;
import tech.guyi.ipojo.module.h2.where.WhereBuilder;
import tech.guyi.ipojo.module.h2.where.condition.type.WhereConditionType;

import java.util.Map;

public class AndWhereConditionBuilder<E extends Entity> extends WhereConditionBuilder<E> {

    public AndWhereConditionBuilder(WhereBuilder<E> where, Map<String, WhereConditionType> types, String field, Object value){
        super(where,types,field,value);
    }

    public AndWhereConditionBuilder(WhereBuilder<E> where,Map<String, WhereConditionType> types) {
        super(where,types);
    }

    @Override
    protected String getConnectSql() {
        return "and";
    }

}
