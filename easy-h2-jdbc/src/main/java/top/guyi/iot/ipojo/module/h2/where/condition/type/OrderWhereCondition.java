package top.guyi.iot.ipojo.module.h2.where.condition.type;

import top.guyi.iot.ipojo.application.annotation.Component;
import top.guyi.iot.ipojo.module.h2.where.condition.WhereConditionItem;

@Component
public class OrderWhereCondition implements WhereConditionType {

    @Override
    public String forType() {
        return "order ";
    }

    @Override
    public String getSql(WhereConditionItem item) {
        return String.format("order by %s %s",item.getName(),item.getValue());
    }

}
