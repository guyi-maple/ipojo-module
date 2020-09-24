# ipojo-module

为 [ipojo](https://github.com/guyi-maple/ipojo) 提供拓展，使用时需要依赖<code>ipojo</code>

## 子模块

* coap-server Coap协议服务端封装， 用法类似SpringMVC
* easy-h2-jdbc 针对H2数据库的ORM框架，用法类似Hibernate
* easy-helper 简易工具
* stream 响应式流

详细使用方式，请参见 [文档](https://github.com/guyi-maple/ipojo-module/blob/master/doc/index.md)

## 安装

#### coap-server

``` xml
<dependency>
    <groupId>tech.guyi.ipojo.module</groupId>
    <artifactId>coap-server</artifactId>
    <version>1.0.0.2-SNAPSHOT</version>
</dependency>
```

#### easy-h2-jdbc

``` xml
<dependency>
    <groupId>tech.guyi.ipojo.module</groupId>
    <artifactId>easy-h2-jdbc</artifactId>
    <version>1.0.0.2-SNAPSHOT</version>
</dependency>
```

#### easy-helper

``` xml
<dependency>
    <groupId>tech.guyi.ipojo.module</groupId>
    <artifactId>easy-helper</artifactId>
    <version>1.0.0.2-SNAPSHOT</version>
</dependency>
```

#### stream

``` xml
<dependency>
    <groupId>tech.guyi.ipojo.module</groupId>
    <artifactId>stream</artifactId>
    <version>1.0.0.2-SNAPSHOT</version>
</dependency>
```

## 私服配置

项目未发布到中央仓库，使用需要配置私服

``` xml
<repositories>
    <repository>
        <id>iot</id>
        <url>http://nexus.guyi-maple.top/content/repositories/iot/</url>
    </repository>
</repositories>
```