package tech.guyi.ipojo.module.h2.where.condition.converter;

import tech.guyi.ipojo.application.annotation.Component;
import tech.guyi.ipojo.module.h2.where.SqlRuntimeException;
import tech.guyi.ipojo.module.h2.where.condition.WhereConditionItem;
import tech.guyi.ipojo.module.h2.where.condition.type.WhereConditionType;

@Component
public class NullWhereConditionTypeConverter implements WhereConditionTypeConverter {

    @Override
    public int order() {
        return 999;
    }

    @Override
    public boolean check(WhereConditionItem item) {
        return item.getValue() == null;
    }

    @Override
    public String convert(WhereConditionType type, WhereConditionItem item) {

        if ("eq".equals(type.forType())){
            return String.format("%s is null",item.getName());
        }else if ("not".equals(type.forType())){
            return String.format("%s not null",item.getName());
        }

        throw new SqlRuntimeException(String.format("条件类型[%s]的判断值不能为null",type.forType()));
    }


}
