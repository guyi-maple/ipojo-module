package tech.guyi.ipojo.module.h2.where.condition.type;

import tech.guyi.ipojo.application.annotation.Component;
import tech.guyi.ipojo.module.h2.where.condition.WhereConditionItem;

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
