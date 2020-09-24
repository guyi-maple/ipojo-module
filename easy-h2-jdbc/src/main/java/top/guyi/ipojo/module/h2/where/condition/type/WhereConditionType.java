package top.guyi.ipojo.module.h2.where.condition.type;

import tech.guyi.ipojo.application.component.ForType;
import top.guyi.ipojo.module.h2.where.condition.WhereConditionItem;

public interface WhereConditionType extends ForType<String> {

    String getSql(WhereConditionItem item);


}
