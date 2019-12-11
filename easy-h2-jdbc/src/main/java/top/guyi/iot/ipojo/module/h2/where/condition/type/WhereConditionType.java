package top.guyi.iot.ipojo.module.h2.where.condition.type;

import top.guyi.iot.ipojo.application.component.ForType;
import top.guyi.iot.ipojo.module.h2.where.condition.WhereConditionItem;

public interface WhereConditionType extends ForType<String> {

    String getSql(WhereConditionItem item);


}
