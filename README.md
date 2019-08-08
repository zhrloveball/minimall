
好记性不如烂笔头，撸一遍[18k star 商城代码](https://macrozheng.github.io/mall-learning/#/)。

***

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