
## 对象包装器 [ObjectDecorator](../easy-helper/src/main/java/top/guyi/iot/ipojo/module/helper/decorator/ObjectDecorator.java)

添加组件实现接口 <code>ObjectDecorator</code> ，泛型是需要包装的对象类型。

通过包装管理器 [ObjectDecoratorManager](../easy-helper/src/main/java/top/guyi/iot/ipojo/module/helper/decorator/ObjectDecoratorManager.java) 传入对象，可以调用对应的包装器。

当不存在对应的对象包装器或包装器返回空值时，会返回传入的对象。

``` java
@Component
public class MqttOptionDecorator implements ObjectDecorator {
    @Override
    public MqttConnectOptions decoration(MqttConnectOptions options) {
        options.setSocketFactory(new VertxSocketFactory(logger,network.get()));
        return options;
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