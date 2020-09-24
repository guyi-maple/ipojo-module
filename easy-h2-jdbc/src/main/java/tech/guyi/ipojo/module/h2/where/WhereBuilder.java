package tech.guyi.ipojo.module.h2.where;

import lombok.Getter;
import org.apache.commons.dbutils.ResultSetHandler;
import tech.guyi.ipojo.module.h2.entity.Entity;
import tech.guyi.ipojo.module.h2.entry.DbEntity;
import tech.guyi.ipojo.module.h2.entry.page.Page;
import tech.guyi.ipojo.module.h2.entry.page.PageRequest;
import tech.guyi.ipojo.module.h2.executor.JdbcExecutor;
import tech.guyi.ipojo.module.h2.executor.handler.SingleIntegerResultHandler;
import tech.guyi.ipojo.module.h2.type.BeanHandler;
import tech.guyi.ipojo.module.h2.type.BeanListHandler;
import tech.guyi.ipojo.module.h2.where.condition.builder.AndWhereConditionBuilder;
import tech.guyi.ipojo.module.h2.where.condition.builder.OrWhereConditionBuilder;
import tech.guyi.ipojo.module.h2.where.condition.builder.OrderConditionBuilder;
import tech.guyi.ipojo.module.h2.where.condition.builder.WhereConditionBuilder;
import tech.guyi.ipojo.module.h2.where.condition.converter.WhereConditionTypeConverter;
import tech.guyi.ipojo.module.h2.where.condition.type.WhereConditionType;

import java.sql.SQLException;
import java.util.*;

public class WhereBuilder<E extends Entity> {

    private static SingleIntegerResultHandler singleIntegerResultHandler = new SingleIntegerResultHandler();

    @Getter
    private DbEntity entity;

    private Map<String, WhereConditionType> types;
    private List<WhereConditionTypeConverter> converters;
    private JdbcExecutor executor;
    private BeanHandler<E> beanHandler;
    private BeanListHandler<E> beanListHandler;

    public WhereBuilder(DbEntity entity,
                        Map<String, WhereConditionType> types,
                        List<WhereConditionTypeConverter> converters,
                        JdbcExecutor executor,
                        BeanHandler<E> beanHandler,
                        BeanListHandler<E> beanListHandler) {
        this.entity = entity;
        this.types = types;
        this.converters = converters;
        this.executor = executor;
        this.beanHandler = beanHandler;
        this.beanListHandler = beanListHandler;
    }

    private List<WhereConditionBuilder> conditionBuilders = new LinkedList<>();

    private List<Object> getArgs(){
        List<Object> args = new ArrayList<>();
        for (WhereConditionBuilder condition : this.conditionBuilders) {
            args.add(condition.getItem().getValue());
        }
        return args;
    }

    public WhereConditionBuilder<E> and(){
        WhereConditionBuilder<E> builder = new AndWhereConditionBuilder<>(this,types);
        this.conditionBuilders.add(builder);
        return builder;
    }

    public WhereConditionBuilder<E> and(String name,Object value){
        WhereConditionBuilder<E> builder = new AndWhereConditionBuilder<>(this,types,name,value);
        this.conditionBuilders.add(builder);
        return builder;
    }

    public WhereConditionBuilder or(){
        WhereConditionBuilder<E> builder = new OrWhereConditionBuilder<>(this,types);
        this.conditionBuilders.add(builder);
        return builder;
    }

    public WhereConditionBuilder or(String name,Object value){
        WhereConditionBuilder<E> builder = new OrWhereConditionBuilder<>(this,types,name,value);
        this.conditionBuilders.add(builder);
        return builder;
    }

    public String getSql(){
        StringBuilder conditions = new StringBuilder();
        Collections.sort(this.conditionBuilders, new Comparator<WhereConditionBuilder>() {
            @Override
            public int compare(WhereConditionBuilder o1, WhereConditionBuilder o2) {
                return Integer.compare(o1.orderNum(),o2.orderNum());
            }
        });
        for (WhereConditionBuilder condition : this.conditionBuilders) {
            conditions.append(" ").append(condition.getSql(this.converters)).append(" ");
        }
        return String.format(
                " from %s where 1 = 1 %s ",
                this.entity.getTableName(),
                conditions.toString()
        );
    }

    public int delete() {
        String sql = String.format("delete %s",this.getSql());
        try {
            return this.executor.update(sql,this.getArgs());
        } catch (SQLException e) {
            throw new SqlRuntimeException(e);
        }
    }

    public int count() {
        String sql = String.format("select count(1) %s",this.getSql());
        try {
            return this.executor.query(sql,singleIntegerResultHandler,this.getArgs());
        } catch (SQLException e) {
            throw new SqlRuntimeException(e);
        }
    }

    public <R extends Entity> List<R> query(ResultSetHandler<List<R>> handler) {
        String sql = String.format("select * %s",this.getSql());
        try {
            return this.executor.query(sql,handler,this.getArgs());
        } catch (SQLException e) {
            throw new SqlRuntimeException(e);
        }
    }

    public List<E> query() {
        return this.query(this.beanListHandler);
    }

    public <R extends Entity> R single(ResultSetHandler<R> handler) {
        String sql = String.format("select * %s limit 0,1",this.getSql());
        try {
            return this.executor.query(sql,handler,this.getArgs());
        } catch (SQLException e) {
            throw new SqlRuntimeException(e);
        }
    }

    public E single() {
        return this.single(this.beanHandler);
    }

    public WhereConditionBuilder<E> orderBy(String name,String order){
        WhereConditionBuilder<E> builder = new OrderConditionBuilder<>(this,types,name,order);
        this.conditionBuilders.add(builder);
        return builder;
    }

    public Page<E> page(PageRequest request){
        return this.page(request,this.beanListHandler);
    }

    public <R extends Entity> Page<R> page(PageRequest request,ResultSetHandler<List<R>> handler){
        String sql = String.format("select * %s limit %s,%s",this.getSql(),request.getStart(),request.getEnd());
        try {
            List<R> content = this.executor.query(sql,handler,this.getArgs());
            long total = this.count();
            return new Page<>(request.getPage(),request.getPageSize(),total,content);
        } catch (SQLException e) {
            throw new SqlRuntimeException(e);
        }
    }

}
