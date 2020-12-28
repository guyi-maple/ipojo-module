## 安装
```xml
<dependency>
    <groupId>tech.guyi.ipojo</groupId>
    <artifactId>easy-helper</artifactId>
    <version>1.0.0.0</version>
</dependency>
```

## 对象包装器 [ObjectDecorator](../easy-helper/src/main/java/tech/guyi/ipojo/module/helper/decorator/ObjectDecorator.java)

添加组件实现接口 <code>ObjectDecorator</code> ，泛型是需要包装的对象类型。

通过包装管理器 [ObjectDecoratorManager](../easy-helper/src/main/java/tech/guyi/ipojo/module/helper/decorator/ObjectDecoratorManager.java) 传入对象，可以调用对应的包装器。

使用前需要通过 <code>ObjectDecoratorManager</code>注册包装器。

``` java
ObjectDecoratorManager.set(Class<?> objectClasses, ObjectDecorator decorator);
```

当不存在对应的对象包装器时，会返回传入的对象。

``` java
@Component
public class MqttOptionDecorator implements ObjectDecorator, InitializingBean {
    
    @Resource
    private ObjectDecoratorManager objectDecoratorManager;

    @Override
    public MqttConnectOptions decoration(MqttConnectOptions options) {
        options.setSocketFactory(new VertxSocketFactory(logger,network.get()));
        return options;
    }

    @Override
    public void afterPropertiesSet() {
        objectDecoratorManager.set(MqttConnectOptions.class,this);
    }
}
```

``` java

@Resource
private ObjectDecoratorManager objectDecoratorManager;

public void test(){
    MqttConnectOptions options = new MqttConnectOptions();
    options = options = this.objectDecoratorManager.decoration(options);
}

```

## 类型转换器

[TypeConverterSelector](../easy-helper/src/main/java/tech/guyi/ipojo/module/helper/converter/TypeConverterSelector.java)

``` java

@Resource
private TypeConverterSelector selector;

public void test(){
    String text = "1.01";
    System.out.println(this.selector(Double.class,text));
}

```

添加组件，实现接口 [TypeConverter](../easy-helper/src/main/java/tech/guyi/ipojo/module/helper/converter/TypeConverter.java) 可以注册新的类型转换器。

``` java
@Component
public class DoubleTypeConverter implements TypeConverter {

    @Override
    public Object convert(Object origin) {
        return origin == null ? null : Double.parseDouble(origin.toString());
    }

    @Override
    public String forType() {
        return Double.class.getName();
    }

}
```