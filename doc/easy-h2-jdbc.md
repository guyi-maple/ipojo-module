
## 配置数据源

使用前需要配置数据源，指定H2数据库文件地址等信息。

添加组件，实现 [JdbcDataSourceProvider](../easy-h2-jdbc/src/main/java/top/guyi/iot/ipojo/module/h2/datasource/JdbcDataSourceProvider.java) 接口的<code>provide</code>方法即可配置H2数据源。

``` java
@Component
public class TestDataSourceProvider implements JdbcDataSourceProvider {

    @Override
    public void provide(final Publisher<JdbcDataSource> publisher) {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUser("device");
        dataSource.setPassword("device");
        dataSource.setURL("jdbc:h2:./test");
        publisher.publish(dataSource);
    }
}
```

## 添加实体

* 每个实体类中必须存在唯一一个主键字段，使用注解 [Id](../easy-h2-jdbc/src/main/java/top/guyi/iot/ipojo/module/h2/entity/annotation/Id.java) 标识
* 用于持久化的实体需要实现接口 [Entity](../easy-h2-jdbc/src/main/java/top/guyi/iot/ipojo/module/h2/entity/Entity.java) ，接口泛型为主键字段的类型。需要实现方法<code>idGenerator</code>，提供ID生成器
* 需要持久化的字段需要使用注解 [Column](../easy-h2-jdbc/src/main/java/top/guyi/iot/ipojo/module/h2/entity/annotation/Column.java) 标识
* 持久化字段只支持String、Double (double)、Boolean (boolean)、Float (float)、Integer (int)
* 持久化字段必须存在get/set方法

``` java
public class Device implements Entity<String> {

    @Override
    public IdGenerator<String> idGenerator() {
        return IdGeneratorFactory.stringIdGenerator;
    }

    @Id
    @Column
    private String id;
    @Column
    private String deviceMac;
    @Column
    private Long onlineTime;
    @Column
    private boolean onLine;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeviceMac() {
        return deviceMac;
    }

    public void setDeviceMac(String deviceMac) {
        this.deviceMac = deviceMac;
    }

    public Long getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(Long onlineTime) {
        this.onlineTime = onlineTime;
    }

    public boolean isOnLine() {
        return onLine;
    }

    public void setOnLine(boolean onLine) {
        this.onLine = onLine;
    }
}
```

### Column注解属性

* name - 数据库字段名称，默认使用被注解字段的名称
* type - 数据库字段类型，默认根据被注解字段的类型进行映射，映射关系参见 [ColumnTypeConverter](../easy-h2-jdbc/src/main/java/top/guyi/iot/ipojo/module/h2/type/ColumnTypeConverter.java)
* length - 数据库字段长度， 默认为-1， 表示使用默认长度， 默认长度数据参见 [ColumnTypeConverter](../easy-h2-jdbc/src/main/java/top/guyi/iot/ipojo/module/h2/type/ColumnTypeConverter.java)

### 更改表名

数据库表名默认使用实体名称，如果需要更改可以使用注解 [Table](../easy-h2-jdbc/src/main/java/top/guyi/iot/ipojo/module/h2/entity/annotation/Table.java)

``` java
@Table(name = "test_device")
public class Device implements Entity<String> { }
```

## 添加Repository

数据库操作需要为实体添加Repository。

添加组件，继承 [JdbcRepository](../easy-h2-jdbc/src/main/java/top/guyi/iot/ipojo/module/h2/JdbcRepository.java) ，泛型为实体及实体主键类型

``` java
@Component
public class DeviceRepository extends JdbcRepository<Device,String> {}
```

## 数据操作

### 查询数据

``` java
@Component
public class Query implements InitializingBean {

    @Resource
    private DeviceRepository repository;

    @Override
    public void afterPropertiesSet() {

        // 查询所有数据
        this.repository.findAll();
        // 条件查询 查询单条数据
        this.repository.where("deviceMac","aa:bb:cc:dd").eq().single();
        // 条件查询 查询多条数据
        this.repository.where("deviceMac","aa:bb:cc:dd").eq().query();
        this.repository.where()
                .and("deviceMac","aa:bb:cc:dd").eq()
                .and("onlineTime",1000).lt()
                .page(new PageRequest(0,30));
        
    }

}
```

#### 条件判断

* lt - 小于
* ltAndEq - 小于等于
* gt - 大于
* gtAndEq - 大于等于
* eq - 等于
* not - 不等于

#### 排序

需要对查询结果排序可以使用 <code>order</code> 方法

``` java
this.repository
    .where("deviceMac","aa:bb:cc:dd").eq()
    .orderBy("onlineTime","desc").order()
    .single();
```

#### 分页查询

``` java
this.repository
    .where()
    .and("deviceMac","aa:bb:cc:dd").eq()
    .and("onlineTime",1000).lt()
    .page(new PageRequest(0,30));
```

### 更改数据

``` java
@Component
public class Update implements InitializingBean {

    @Resource
    private DeviceRepository repository;

    @Override
    public void afterPropertiesSet() {
        // 保存数据 ID会根据实体配置的ID生成器生成
        // 当ID不为空时 会使用update方式执行数据操作
        Device device = new Device();
        device.setDeviceMac("aa:bb:cc:dd");
        this.repository.save(device);

        // 删除数据
        this.repository.delete(device.getId());

        // 更新数据
        device.setDeviceMac("aa:bb:cc:ee");
        this.repository.update(device);
        // 更新数据 不忽略为空字段
        device.setDeviceMac(null);
        this.repository.update(device,false);
    }

}
```