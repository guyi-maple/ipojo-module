package top.guyi.iot.ipojo.module.h2.where.condition.type;

import top.guyi.iot.ipojo.application.annotation.Component;
import top.guyi.iot.ipojo.module.h2.where.condition.WhereConditionItem;

@Component
public class EqWhereCondition implements WhereConditionType {

    @Override
    public String forType() {
        return "eq";
    }

    @Override
    public String getSql(WhereConditionItem item) {
        if (item.getValue() == null){
            return String.format("%s is null",item.getName());
        }
        return String.format("%s = ?",item.getName());
    }

}
