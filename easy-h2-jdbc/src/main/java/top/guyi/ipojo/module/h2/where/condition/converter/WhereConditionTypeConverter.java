package top.guyi.ipojo.module.h2.where.condition.converter;

import top.guyi.ipojo.module.h2.where.condition.WhereConditionItem;
import top.guyi.ipojo.module.h2.where.condition.type.WhereConditionType;

public interface WhereConditionTypeConverter {

    int order();

    boolean check(WhereConditionItem item);

    String convert(WhereConditionType type, WhereConditionItem item);

}
