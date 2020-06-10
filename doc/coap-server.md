
## 注册Coap接口

Coap-Server基于<code>eclipse.californium</code>提供了类似SpringMVC形式的接口注册。

通过注解 [@CoapMapping](../coap-server/src/main/java/top/guyi/iot/ipojo/module/coap/annotation/CoapMapping.java) 可以很简单的注册一个Coap接口

``` java
@CoapMapping(path = "test/one")
public YouResponseEntity test(YouRequestEntity entity){
    System.out.println(entity);
    return new YouResponseEntity();
}
```

在任意一个组件里添加上面的方法，就可以注册一个路径为<code>/test/one</code>，请求方式为<code>POST</code>的Coap接口。

Coap-Server会从Coap请求中获取JSON并序列化为<code>YouRequestEntity</code>传入方法。

如果你不需要自动序列化，需要自行从<code>CoapExchange</code>中获取数据，可以在方法签名中加入<code>CoapExchange</code>。

``` java
@CoapMapping(path = "test/one")
public YouResponseEntity test(CoapExchange exchange){
    System.out.println(exchange);
    return new YouResponseEntity();
}
```

#### 设置接口请求方式

<code>@CoapMapping</code>支持4种请求方式：

* POST <code>@CoapMapping(path = "...")</code>
* GET <code>@CoapMapping(path = "...", method = CoapMethod.GET)</code>
* PUT <code>@CoapMapping(path = "...", method = CoapMethod.PUT)</code>
* DELETE <code>@CoapMapping(path = "...", method = CoapMethod.DELETE)</code>

## [CoapCurrent](../coap-server/src/main/java/top/guyi/iot/ipojo/module/coap/utils/CoapCurrent.java)

#### 获取请求者IP

``` java

@Resource
priavte CoapCurrent current;

@CoapMapping(path = "test/one")
public YouResponseEntity test(YouRequestEntity entity){
    System.out.println(entity);
    System.out.println(current.clientIp());
    return new YouResponseEntity();
}

```

#### 获取CoapExchange

``` java

@Resource
priavte CoapCurrent current;

@CoapMapping(path = "test/one")
public YouResponseEntity test(YouRequestEntity entity){
    System.out.println(entity);
    System.out.println(current.exchange());
    return new YouResponseEntity();
}

```