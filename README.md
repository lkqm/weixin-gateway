# weixin-gateway
![Maven Central](https://img.shields.io/maven-central/v/com.github.lkqm/weixin-gateway)

微信公众号消息网关, 基于注解分发消息, 提供脚手架项目。

```java
@WxController
public class InvoiceHandler {
    @WxEvent("user_authorize_invoice")
    public void authorEvent(InvoiceAuthEventMessage message) {
        System.out.println("电子发票授权事件");
    }
}
```

支持: JDK1.7 +

脚手架项目: [weixin-gateway-web](https://github.com/lkqm/weixin-gateway/tree/master/weixin-gateway-web)

## 快速开始
1. 引入依赖
    ```xml
    <dependency>
        <groupId>com.github.lkqm</groupId>
        <artifactId>weixin-gateway-spring-boot-starter</artifactId>
        <version>${version}</version>
    </dependency>
    ```
2. 添加配置(application.properties)
    ```properties
    wx.gateway.enabled = ture                      # 是否开启, 关闭后不会自动注入WxPortalController
    wx.gateway.dev = false                         # 开发者模式, 处理消息跳过签名校验
    wx.gateway.prefix = /wx/gateway                # 设置回掉路径前缀, 访问地址: /wx/gateway, /wx/gateway/{key}
    wx.gateway.config.default.appId = @appId       # 配置一个微信配置访问路径key=default, 当key=default访问地址{key}可省略
    wx.gateway.config.default.token = @token
    wx.gateway.config.default.aesKey = @aesKey
    
    wx.gateway.pool.namePrefix = wx-router-        # 线程名称前缀
    wx.gateway.pool.coreSize = 10                  # 核心线程大小
    wx.gateway.pool.maxSize = 100                  # 最大线程大小
    wx.gateway.pool.keepAlive = 5000               # 空闲线程存活时间(ms)
    wx.gateway.pool.queueCapacity=                 # 工作队列大小
    ```
3. 增加相关的业务处理器
    ```java
    @WxController
    public class InvoiceHandler {
        @WxEvent("user_authorize_invoice")
        public void authEvent(InvoiceAuthMessage authMessage, @WxBody String xml, 
                              @WxParam("FromUserName") String fromUser, @WxParam("CreateTime") Integer createTime) {
        }
    }
    ```

## 你需要知道的？
### 消息分发
所有的消息分发处理均是异步, 处理消息或事件类型不能重复声明。
- `@WxController`: 标记类为消息处理类
- `@WxEvent`: 注解用于事件匹配
- `@WxMessage`: 注解用于消息匹配

### 参数注入
响应微信消息的处理方法, 支持注入4种参数, 解析顺序依次向下:

- @WxParam: 方法参数指定注解`@WxParam`, 会自定注入指定xml标记的值(不支持多层嵌套)
- @WxBody: 方法参数指定注解`@WxBody`, 并且类型String, 会自动注入值为微信请求的xml(非密文)
- 自定义参数解析: a.实现接口HandlerMethodArgumentResolver, b.注入容器
- 普通bean: 普通JavaBean, 字段命名需要按照驼峰命名(内部是xml转json, 然后json转对象)

注意: 基本类型参数只会注入默认值

### 多公众号
可以实现多公众号, 控制层路径地址: /wx/gateway/{key}, 配置自定义参数解析可以实现: HandlerMethodArgumentResolver
```java
@Component
public class WxMpServiceArgumentResolver implements HandlerMethodArgumentResolver {
    
    private Map<String, WxMpService> appIdToWxMpServiceMap = new ConcurrentHashMap<>();

    @Override
    public boolean supportsParameter(Class<?> type) {
        return WxMpService.class.isAssignableFrom(type);
    }

    @Override
    public Object resolveArgument(Message message) {
        WxConfig config = message.getWxConfig();
        return appIdToWxMpServiceMap.get(config.getAppId());
    }
}
```






