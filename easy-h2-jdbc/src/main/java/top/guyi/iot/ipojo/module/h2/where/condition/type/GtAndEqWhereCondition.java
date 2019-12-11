package top.guyi.iot.ipojo.module.h2.where.condition.type;

import top.guyi.iot.ipojo.application.annotation.Component;
import top.guyi.iot.ipojo.module.h2.where.condition.WhereConditionItem;

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
