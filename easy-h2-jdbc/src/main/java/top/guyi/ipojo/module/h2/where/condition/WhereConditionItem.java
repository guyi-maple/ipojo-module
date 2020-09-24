package top.guyi.ipojo.module.h2.where.condition;

import lombok.Data;
import top.guyi.ipojo.module.h2.entry.DbEntity;

@Data
public class WhereConditionItem {

    private DbEntity entity;
    public WhereConditionItem(DbEntity entity) {
        this.entity = entity;
    }

    private String fieldName;
    private Object value;
    private String type;

    public String getName(){
        return this.entity.getColumnName(fieldName);
    }

}
