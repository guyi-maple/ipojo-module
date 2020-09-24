package top.guyi.ipojo.module.h2.where.condition.type;

import tech.guyi.ipojo.application.annotation.Component;
import top.guyi.ipojo.module.h2.where.condition.WhereConditionItem;

@Component
public class LtWhereCondition implements WhereConditionType {

    @Override
    public String forType() {
        return "lt";
    }

    @Override
    public String getSql(WhereConditionItem item) {
        return String.format("%s < ?",item.getName());
    }

}
