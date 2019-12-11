package top.guyi.iot.ipojo.module.h2.where.condition.converter;

import top.guyi.iot.ipojo.module.h2.where.condition.WhereConditionItem;
import top.guyi.iot.ipojo.module.h2.where.condition.type.WhereConditionType;

public interface WhereConditionTypeConverter {

    int order();

    boolean check(WhereConditionItem item);

    String convert(WhereConditionType type, WhereConditionItem item);

}
