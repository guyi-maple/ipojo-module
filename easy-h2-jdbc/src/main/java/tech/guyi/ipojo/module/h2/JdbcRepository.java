package tech.guyi.ipojo.module.h2;

import lombok.Setter;
import org.osgi.framework.BundleContext;
import tech.guyi.ipojo.application.ApplicationContext;
import tech.guyi.ipojo.application.annotation.Resource;
import tech.guyi.ipojo.application.bean.interfaces.ApplicationStartSuccessEvent;
import tech.guyi.ipojo.application.bean.interfaces.InitializingBean;
import tech.guyi.ipojo.application.utils.ReflectUtils;
import tech.guyi.ipojo.module.h2.entity.Entity;
import tech.guyi.ipojo.module.h2.entry.DbEntity;
import tech.guyi.ipojo.module.h2.entry.FieldEntry;
import tech.guyi.ipojo.module.h2.executor.JdbcExecutor;
import tech.guyi.ipojo.module.h2.type.BeanHandler;
import tech.guyi.ipojo.module.h2.type.BeanListHandler;
import tech.guyi.ipojo.module.h2.where.condition.builder.WhereConditionBuilder;
import tech.guyi.ipojo.module.h2.where.condition.converter.WhereConditionTypeConverter;
import tech.guyi.ipojo.module.h2.utils.TableUtils;
import tech.guyi.ipojo.module.h2.where.SqlRuntimeException;
import tech.guyi.ipojo.module.h2.where.WhereBuilder;
import tech.guyi.ipojo.module.h2.where.condition.WhereConditionItem;
import tech.guyi.ipojo.module.h2.where.condition.type.WhereConditionType;
import tech.guyi.ipojo.module.stream.Mono;
import tech.guyi.ipojo.module.stream.producer.Producer;
import tech.guyi.ipojo.module.stream.publisher.Publisher;
import tech.guyi.ipojo.module.stream.subscriber.Subscriber;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class JdbcRepository<E extends Entity<ID>,ID extends Serializable> implements InitializingBean, ApplicationStartSuccessEvent {

    private BeanHandler<E> beanHandler;
    private BeanListHandler<E> beanListHandler;
    private Mono<JdbcRepository<E,ID>> mono;
    private Publisher<JdbcRepository<E, ID>> _publisher;

    @Override
    public void afterPropertiesSet() {
        this.beanHandler = new BeanHandler<>(this.entityClass(),this.getEntity());
        this.beanListHandler = new BeanListHandler<>(this.entityClass(),this.getEntity());
        this.mono = Mono.create(new Producer<JdbcRepository<E, ID>>() {
            @Override
            public void produce(Publisher<JdbcRepository<E, ID>> publisher) {
                _publisher = publisher;
            }
        });
        this.mono.open();
    }

    @Override
    public void onStartSuccess(ApplicationContext applicationContext, BundleContext bundleContext) throws Exception {
        final JdbcRepository<E,ID> _this = this;
        this.executor.await(new Subscriber<Boolean>() {
            @Override
            public void subscription(Boolean value) {
                executorService.schedule(new Runnable() {
                    @Override
                    public void run() {
                        if (!tableUtils.exist(entity.getTableName())){
                            tableUtils.create(entity.getTableName(),entity.getFields());
                        }
                        for (FieldEntry field : entity.getFields()) {
                            if (!tableUtils.fieldExist(entity.getTableName(),field.getColumnName())){
                                tableUtils.addField(entity.getTableName(),field.getColumnName(),field.getColumnType());
                            }
                        }

                        _publisher.publish(_this);

                        mono = null;
                        _publisher = null;
                    }
                },1, TimeUnit.SECONDS);
            }
        });
    }

    public void await(Subscriber<JdbcRepository<E,ID>> subscriber){
        if (this.mono == null || this.executor.isReady()){
            subscriber.subscription(this);
        }else{
            this.mono.subscription(subscriber);
        }
    }

    @Setter
    @Resource
    private JdbcExecutor executor;
    @Resource
    private Map<String,WhereConditionType> types;
    @Resource
    private List<WhereConditionTypeConverter> converters;

    @Setter
    @Resource
    private ScheduledExecutorService executorService;
    @Setter
    @Resource
    private TableUtils tableUtils;

    public void setConverters(List<WhereConditionTypeConverter> converters){
        this.converters = converters;
        Collections.sort(this.converters, new Comparator<WhereConditionTypeConverter>() {
            @Override
            public int compare(WhereConditionTypeConverter o1, WhereConditionTypeConverter o2) {
                return Integer.compare(o1.order(),o2.order());
            }
        });
    }

    public void setTypes(Map<String,WhereConditionType> types){
        this.types = types;
    }


    protected abstract Class<E> entityClass();

    private DbEntity entity;
    protected DbEntity getEntity(){
        if (entity == null){
            this.entity = DbEntity.from(this.entityClass());
        }
        return this.entity;
    }

    public WhereConditionBuilder<E> where(String name, Object value){
        WhereBuilder<E> builder = this.where();
        return builder.and(name,value);
    }

    public WhereBuilder<E> where(){
        return new WhereBuilder<>(this.getEntity(),this.types,this.converters,this.executor,this.beanHandler,this.beanListHandler);
    }

    public void save(E entity){
        if (entity.getId() != null && this.exists(entity.getId())){
            this.update(entity,false);
            return;
        }

        if (entity.getId() == null){
            entity.setId(entity.idGenerator().get());
        }

        List<WhereConditionItem> values = new LinkedList<>();
        for (FieldEntry entry : this.entity.getFields()) {
            Object value = ReflectUtils.getFieldValue(entity,entry.getField());
            if (value != null){
                WhereConditionItem item = new WhereConditionItem(this.entity);
                item.setValue(value);
                item.setFieldName(entry.getField().getName());
                values.add(item);
            }
        }

        StringBuilder fieldNames = new StringBuilder();
        StringBuilder placeholder = new StringBuilder();
        List<Object> args = new LinkedList<>();

        for (WhereConditionItem item : values) {
            fieldNames.append(item.getName()).append(",");
            placeholder.append("?,");
            args.add(item.getValue());
        }

        String sql = String.format(
                "insert into %s(%s) values(%s)",
                this.entity.getTableName(),
                fieldNames.substring(0,fieldNames.length() - 1),
                placeholder.substring(0,placeholder.length() - 1)
        );

        try {
            this.executor.update(sql,args);
        } catch (SQLException e) {
            throw new SqlRuntimeException(e);
        }
    }

    public boolean update(E entity){
        return this.update(entity,true);
    }

    public boolean update(E entity,boolean ignoreNull){
        if (entity.getId() == null){
            throw new SqlRuntimeException("update object, id field must not null");
        }

        List<WhereConditionItem> values = new LinkedList<>();
        for (FieldEntry entry : this.entity.getFields()) {
            Object value = ReflectUtils.getFieldValue(entity,entry.getField());
            if (entry != entity.getId() && (value != null || !ignoreNull)){
                WhereConditionItem item = new WhereConditionItem(this.entity);
                item.setValue(value);
                item.setFieldName(entry.getField().getName());
                values.add(item);
            }
        }

        StringBuilder setFields = new StringBuilder();
        List<Object> args = new LinkedList<>();
        for (WhereConditionItem item : values) {
            setFields.append(item.getName()).append(" = ?,");
            args.add(item.getValue());
        }

        String sql = String.format(
                "update %s set %s where %s = ?",
                this.entity.getTableName(),
                setFields.substring(0,setFields.length() - 1),
                this.entity.getId().getColumnName()
        );

        args.add(ReflectUtils.getFieldValue(entity,this.entity.getId().getField()));

        try {
            return this.executor.update(sql,args) > 0;
        } catch (SQLException e) {
            throw new SqlRuntimeException(e);
        }
    }

    public List<E> findAll(){
        return this.where().query(this.beanListHandler);
    }

    public E get(ID id){
        return this.where().and()
                .name(this.entity.getId().getName())
                .value(id).eq()
                .single();
    }

    public boolean exists(ID id){
        return this.where().and()
                .name(this.entity.getId().getName())
                .value(id).eq()
                .count() > 0;
    }

    public boolean delete(ID id){
        return this.where().and()
                .name(this.entity.getId().getName())
                .value(id).eq()
                .delete() > 0;
    }

    public void deleteAll(){
        try {
            this.executor.update(String.format("delete from %s", this.entity.getTableName()),Collections.EMPTY_LIST);
        } catch (SQLException e) {
            throw new SqlRuntimeException(e);
        }
    }
}
