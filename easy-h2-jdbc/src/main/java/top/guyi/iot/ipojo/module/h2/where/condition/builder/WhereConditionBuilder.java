package top.guyi.iot.ipojo.module.h2.where.condition.builder;

import top.guyi.iot.ipojo.application.utils.StringUtils;
import top.guyi.iot.ipojo.module.h2.entity.Entity;
import top.guyi.iot.ipojo.module.h2.where.SqlRuntimeException;
import top.guyi.iot.ipojo.module.h2.where.WhereBuilder;
import top.guyi.iot.ipojo.module.h2.where.condition.WhereConditionItem;
import top.guyi.iot.ipojo.module.h2.where.condition.converter.WhereConditionTypeConverter;
import top.guyi.iot.ipojo.module.h2.where.condition.type.WhereConditionType;

import java.util.List;
import java.util.Map;

public abstract class WhereConditionBuilder<E extends Entity> {

    private WhereConditionItem item;
    private WhereBuilder<E> where;
    private Map<String, WhereConditionType> types;

    public WhereConditionBuilder(WhereBuilder<E> where,Map<String, WhereConditionType> types) {
        this.types = types;
        this.where = where;
    }
    public WhereConditionBuilder(WhereBuilder<E> where,Map<String, WhereConditionType> types,String field,Object value) {
        this(where,types);
        this.name(field);
        this.value(value);
    }

    public int orderNum(){
        return 0;
    }

    public WhereConditionItem getItem(){
        if (this.item == null){
            this.item = new WhereConditionItem(this.where.getEntity());
        }
        return this.item;
    }

    protected abstract String getConnectSql();

    private void check(){
        if (StringUtils.isEmpty(this.getItem().getFieldName())){
            throw new SqlRuntimeException("条件字段名不能为空");
        }
    }

    public WhereConditionBuilder<E> name(String fieldName){
        this.getItem().setFieldName(fieldName);
        return this;
    }

    public WhereConditionBuilder<E> value(Object value){
        this.getItem().setValue(value);
        return this;
    }

    public WhereBuilder<E> custom(String type){
        this.check();
        this.getItem().setType(type);
        return this.where;
    }

    public WhereBuilder<E> eq(){
        return this.custom("eq");
    }

    public WhereBuilder<E> lt(){
        this.check();
        return this.custom("lt");
    }

    public WhereBuilder<E> gt(){
        this.check();
        return this.custom("gt");
    }

    public WhereBuilder<E> not(){
        this.check();
        return this.custom("not");
    }

    public WhereBuilder<E> gtAndEq(){
        this.check();
        return this.custom("gt_eq");
    }

    public WhereBuilder<E> ltAndEq(){
        this.check();
        return this.custom("lt_eq");
    }

    public WhereBuilder<E> order(){
        this.check();
        return this.custom("order");
    }

    public String getSql(List<WhereConditionTypeConverter> converters){
        String sql = "";
        WhereConditionType type = this.types.get(this.getItem().getType());
        if (type == null){
            throw new SqlRuntimeException(String.format("不存在的条件类型[%s]",this.getItem().getType()));
        }
        for (WhereConditionTypeConverter converter : converters) {
            if (converter.check(this.getItem())){
                sql = converter.convert(type,this.getItem());
            }
        }
        return String.format("%s %s",this.getConnectSql(),sql);
    }


}
