package top.guyi.iot.ipojo.module.h2.where.condition.converter;

import top.guyi.iot.ipojo.application.annotation.Component;
import top.guyi.iot.ipojo.module.h2.where.condition.WhereConditionItem;
import top.guyi.iot.ipojo.module.h2.where.condition.type.WhereConditionType;

@Component
public class AnyWhereConditionTypeConverter implements WhereConditionTypeConverter {

    @Override
    public int order() {
        return 999;
    }

    @Override
    public boolean check(WhereConditionItem item) {
        return true;
    }

    @Override
    public String convert(WhereConditionType type,WhereConditionItem item) {
        return type.getSql(item);
    }

}
