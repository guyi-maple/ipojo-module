package top.guyi.ipojo.module.h2.where.condition.type;

import tech.guyi.ipojo.application.annotation.Component;
import top.guyi.ipojo.module.h2.where.condition.WhereConditionItem;

@Component
public class GtWhereCondition implements WhereConditionType {

    @Override
    public String forType() {
        return "gt";
    }

    @Override
    public String getSql(WhereConditionItem item) {
        return String.format("%s > ?",item.getName());
    }

}
