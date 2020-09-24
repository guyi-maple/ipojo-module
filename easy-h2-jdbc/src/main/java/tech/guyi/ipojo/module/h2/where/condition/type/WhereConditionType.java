package tech.guyi.ipojo.module.h2.where.condition.type;

import tech.guyi.ipojo.application.component.ForType;
import tech.guyi.ipojo.module.h2.where.condition.WhereConditionItem;

public interface WhereConditionType extends ForType<String> {

    String getSql(WhereConditionItem item);


}
