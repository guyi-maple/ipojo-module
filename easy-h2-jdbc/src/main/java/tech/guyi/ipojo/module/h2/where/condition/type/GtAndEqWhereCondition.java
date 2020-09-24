package tech.guyi.ipojo.module.h2.where.condition.type;

import tech.guyi.ipojo.application.annotation.Component;
import tech.guyi.ipojo.module.h2.where.condition.WhereConditionItem;

@Component
public class GtAndEqWhereCondition implements WhereConditionType {

    @Override
    public String forType() {
        return "gt_eq";
    }

    @Override
    public String getSql(WhereConditionItem item) {
        return String.format("%s >= ?",item.getName());
    }

}
