
撸一遍[商城代码](https://macrozheng.github.io/mall-learning/#/)。

***

### Day 7

整合 Elasticsearch 实现商品搜索

> Elasticsearch(ES) 是 Lucene 的封装，提供了 REST API 的操作接口。
ES 本质上是一个分布式数据库，允许多台服务器协同工作，每台服务器可以运行多个 ES 实例。单个 ES 实例称为一个节点（node），一组节点构成一个集群（cluster）。
ES 会索引所有字段，经过处理后写入一个``倒排索引（Inverted Index）``。查找数据的时候，直接查找该索引。所以，ES 数据管理的顶层单位就叫做 Index（索引），它是单个数据库的同义词。
Index 里面单条的记录称为 Document（文档），Document 中每个字段称为 Field，Document 可以通过 Type 逻辑分组，Elastic 6.x 版只允许每个 Index 包含一个 Type，7.x 版将会彻底移除 Type(因为同一个 Index 中，不同 Type 中的同名字段在内部是同一个 Lucene 字段来支持的，同名字段类型不一致会导致混乱)

1. 设计表 商品信息表``pms_product`` 商品属性参数表``pms_product_attribute`` 产品参数值表``pms_product_attribute_value``
2. 定义商品 Document 对象 EsProduct，需要索引的字段加上 ``@Field``， 需要中文分词的字段加上中文分词器 ``ik``
3. 继承 ElasticsearchRepository 接口可以获得常用的数据操作方法，在接口中直接指定查询方法名称便可查询，无需进行实现。
4. 实现将数据库商品信息导入 ES 和商品信息简单查询接口

### Day 6

整合 RabbitMQ 实现延时取消订单：

1. 当用户下单时系统会锁库存，同时设置订单超时时间
2. 按订单超时时间发送一个延迟消息给 RabbitMQ，若时间到了用户还未支付，取消订单并释放库存

如果不使用延迟队列，只能采用定时任务轮询数据库，轮询时间较难设置，若轮询时间过大影响精度，过小影响性能。

> RabbitMQ 是 AMQP 协议的标准实现，AMQP 协议的核心思想就是生产者和消费者的解耦，生产者(Producer)不直接发消息给队列(Queue)，而是发给交换机(Exchange)，Exchange 再根据 Binding 和 RoutingKey 转发给 Queue。

> Exchange 三种主要类型：Fanout, Direct, Topic
> - Fanout Exchange 会忽略 RoutingKey 的设置，直接将 Message 广播到所有绑定的 Queue 中
> - Direct Exchange 是 RabbitMQ 默认的 Exchange，完全根据 RoutingKey 来路由消息。设置 Exchange 和 Queue 的 Binding 时需指定 RoutingKey（一般为 Queue Name），发消息时也指定一样的 RoutingKey，消息就会被路由到对应的Queue
> - Topic Exchange 和 Direct Exchange 类似，也需要通过 RoutingKey 来路由消息，区别在于 Direct Exchange 对 RoutingKey 是精确匹配，而 Topic Exchange 支持模糊匹配。分别支持 ``*`` 和 ``#`` 通配符，``*`` 表示匹配一个单词，``#`` 则表示匹配没有或者多个单词

如何实现：
1. 定义**订单取消队列** ``mall.order.cancel`` 和 **订单延迟队列(死信队列)** ``mall.order.cancel.ttl`` 并绑定各自交换机
2. 定义死信队列
    1. ``x-dead-letter-exchange`` 设置死信后发送的交换机 ``mall.order.direct``
    2. ``x-dead-letter-routing-key`` 设置死信路由键 ``mall.order.cancel``
3. 下单时通过交换机向**订单延迟队列** ``mall.order.cancel.ttl`` 发送消息，并设置 ``TTL(Time To Live 过期时间)``
4. 消息在**订单延迟队列**中生存时间超过设置的 TTL 后，就会变成死信(Dead Message)，通过绑定的交换机进入**订单取消队列** ``mall.order.cancel``
5. 通过 ``@RabbitListener(queues = "mall.order.cancel")`` 和 ``@RabbitHandler`` 处理**订单取消队列**中的消息

### Day 5

- 创建用户表、角色表、权限表、用户和角色关系表、角色和权限关系表、用户和权限关系表
- 整合 JWT 和 SpringSecurity 实现用户注册登录认证和Api接口权限验证的功能
    1. 创建 AdminUserDetails，实现 Spring Security 的 UserDetails 接口，重写 getAuthorities() 方法封装用户权限，供后续认证时使用
    2. JwtTokenUtil 封装 JWT 各项操作，JwtAuthenticationTokenFilter 拦截每次请求做认证
    3. 编写 Spring Security 配置类 SecurityConfig，继承 WebSecurityConfigurerAdapter，重写 configure() 方法实现自定义配置
    4. 通过 ``@PreAuthorize("hasAuthority('')")`` 实现对 Api 接口权限的校验
- 改造 Swagger 配置，自动记住登录令牌进行发送

### Day 4

- 安装并启动 Redis 服务
- 整合 Redis，通过 StringRedisTemplate 操作 Redis，实现短信验证码的存储验证

### Day 3

- Service 使用 Junit 测试，Controller 使用 MockMvc 测试
- 集成 Swagger，使用增强 UI
- 修改 MBG 生成类，自动生成 @ApiModelProperty 注释

### Day 2

- 编写统一返回类 CommonResult
- 编写品牌管理 Controller、Service
- 编写 logback 配置文件

### Day 1

- SpringBoot 环境搭建 
- 整合 Mybatis，Mybatis Generator(MBG)
- 根据数据库字段备注生成 Model 注释