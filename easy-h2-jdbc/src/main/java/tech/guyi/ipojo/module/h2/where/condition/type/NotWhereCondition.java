package tech.guyi.ipojo.module.h2.where.condition.type;

import tech.guyi.ipojo.application.annotation.Component;
import tech.guyi.ipojo.module.h2.where.condition.WhereConditionItem;

@Component
public class NotWhereCondition implements WhereConditionType {

    @Override
    public String forType() {
        return "not";
    }

    @Override
    public String getSql(WhereConditionItem item) {
        if (item.getValue() == null){
            return String.format("%s not null",item.getName());
        }
        return String.format("%s <> ?",item.getName());
    }

}
