![](https://img.shields.io/badge/Spring_boot_3-blueviolet?style=for-the-badge)
![](https://img.shields.io/badge/Spring_Security_6-blueviolet?style=for-the-badge)
![](https://img.shields.io/badge/Java_17-yellow?style=for-the-badge)
![](https://img.shields.io/badge/Json_web_token-blue?style=for-the-badge)  

# Spring Security 6 和 JWT 应用的 demo

Spring Boot 应用，利用JWT保护公开的REST API

## 数据库和测试数据
- 在 mysql 中创建了 securejwt 数据库，以及可以访问这个数据库的 noveluser 用户；
- 数据库中存放了三个表：用户表，角色表（用于控制访问权限）和用户-角色对应表
- 以手机号为 Security 中的 username 
- 在主应用中创建所使用的测试数据
- 项目配置中，设置spring.jpa.hibernate.ddl-auto参数为create，即每次应用程序启动时自动创建数据库表结构（仅用于开发环境方便测试）

## JWT 配置参数
- secret 是初始化密钥，用于生成 token 时调用，可自定义；
- expiration 是生成token的过期时间，设置为 10 小时，也可以自定义；

## Config 包和 bean 包
- SpringSecurityConfig：主要是定义每个接口可以由什么权限的角色所访问；
  - /user: 任何用户均可访问
  - /admin: 只有 admin 角色的用户可以访问
  - /superadmi：只有 superadmin 角色的用户可以访问
- ShutdownConfig：和 Security 项目无关，是一个优雅停止服务的配置，用于支持在停止服务前执行一些操作；
  - 生成了一个 TerminateBean 的 bean，这个bean中，使用@PreDestroy注解指定了一个在销毁bean之前要执行的方法

## models 包：
- 映射了用户表和角色表两个实体
- 定义了角色名称的枚举类
- 定义了两个 Repository 接口，分别使用 User 和 Role 两个实体；
- 接口中的方法将根据 JPA 的规则，自动生成 SQL 语句，因此可以直接实例化此接口来使用其方法；
- 这两个方法是根据名称查询用户或角色；

## service 包：
- UserService接口及其实现类 UserServiceImpl提供了四个方法：
  - 新增用户并保存（用户注册功能使用，初始化数据使用）；
  - 新增角色并保存（初始化数据使用）；
  - 用户注册：注册通过后，返回包含 token 的响应对象；
  - 用户认证；认证通过后，返回 token；
- JwtAuthenticationFilter:
  - 对所有的请求进行如下过滤：验证并解析token，并比对当前上下文中的用户是否和 token 中的用户一致
- CustomerUserDetailServiceImpl
  - 实现了一个方法，调用接口中定义的 JPA 方法查找用户，此方法被用在过滤器中；

## utilt 包：
- JwtUtilities类提供了 JWT 的核心方法：
  - 生成新的 token
  - 从 http 请求头中提取 token
  - 验证 tonken
  - 解析 token 中的用户名、过期时间
  - 判断 tonken 是否过期

## dto 包：
- RetisterDto: 封装了提交注册用户请求时所需的 post 数据对象；
- LoginDto：封装了用户登录请求时所需的 post 数据对象
- BearerToken：封装了用户注册成功后包含 token 的响应对象

## controller 包：
- 提供了三个测试用的接口：
  - /user接口：提供了注册接口和登录接口，需使用 post 数据对象，所有用户均可访问
  - /admn 接口：需在请求头中添加"Authorization: Bearer tonken"方可成功访问
  - /superadmin接口：需在请求头中添加"Authorization: Bearer tonken"方可成功访问

## log/test.sh
- 提供了接口的命令行测试方式
- 注册和登录接口所需的 post 数据做成了 .json 文件放在同一目录下