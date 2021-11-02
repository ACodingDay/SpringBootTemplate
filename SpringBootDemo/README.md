# Spring Boot & Bootstrap

## 前置知识

Spring Boot：WEB 应用的搭建与开发；

Bootstrap：[Bootstrap中文网 (bootcss.com)](https://www.bootcss.com/)，前端页面的开发；

Maven：[Maven – Welcome to Apache Maven](http://maven.apache.org/)，JAR 包的依赖管理；

RequireJS：[RequireJS 中文网 (requirejs-cn.cn)](https://www.requirejs-cn.cn/)，简洁优雅的管理 JS 脚本和 CSS 样式；

Spring Security：系统角色权限的控制；

Spring AOP：切入式的日志管理；

JasperReports：[JasperReports教程™ (yiibai.com)](https://www.yiibai.com/jasper_reports/)，报表的制作和发布；

MariaDB：[MariaDB 教程_w3cschool](https://www.w3cschool.cn/mariadb/)，MySQL 数据库的一个分支；

Postman：[Download Postman | Get Started for Free](https://www.postman.com/downloads/)，开发过程中调试接口；

Echarts：[ECharts 教程_w3cschool](https://www.w3cschool.cn/echarts_tutorial/)，图形图表 JS 组件。



## Bootstrap

中文文档：[简介 · Bootstrap v4 中文文档 v4.6 | Bootstrap 中文网 (bootcss.com)](https://v4.bootcss.com/docs/getting-started/introduction/)

用法案例：[Bootstrap 4 Cheat Sheet - The ultimate list of Bootstrap classes (hackerthemes.com)](https://hackerthemes.com/bootstrap-cheatsheet/)

IDEA 插件：[bootstrap4-snippets/intellij at intellij-6.1.0 · 1tontech/bootstrap4-snippets · GitHub](https://github.com/1tontech/bootstrap4-snippets/tree/intellij-6.1.0/intellij)

CDN 加速：[BootCDN - Bootstrap 中文网开源项目免费 CDN 加速服务](https://www.bootcdn.cn/)

后台模板 AdminLTE：[Free Bootstrap Admin Template | AdminLTE.IO](https://adminlte.io/)

* Github 地址：[GitHub - ColorlibHQ/AdminLTE: AdminLTE - Free admin dashboard template based on Bootstrap 4](https://github.com/ColorlibHQ/AdminLTE)

* 中文文档：[AdminLTE 3 | 仪表盘 (3vshej.cn)](https://3vshej.cn/AdminLTE/AdminLTE-3.x/)

后台模板 Gentelella - Admin：[Gentelella Alela! | (colorlib.com)](https://colorlib.com/polygon/gentelella/index.html)

* Github 地址：[GitHub - ColorlibHQ/gentelella: Free Bootstrap 4 Admin Dashboard Template](https://github.com/ColorlibHQ/gentelella)

* 文档：[Gentelella Alela! | (colorlibhq.github.io)](https://colorlibhq.github.io/gentelella/)



## Spring Boot

### 配置文件

Spring 的优化版，“约定优于配置”，只需要少量的配置，其余的使用默认的就行。

项目初始化时，就存在 `application.properties` 默认配置文件，但我们一般使用 `application.yml` 这种配置文件。

如果在配置文件中遇到 `Cannot resolve configuration property 'spring.thymeleaf.content-type' `，说明不能正常解析这个属性，那么可能过时了，或者可能废弃了。

> 参考：[yml报错：deprecated configuration property spring.thymeleaf.content-type_古柏树下的博客-CSDN博客](https://blog.csdn.net/sinat_29774479/article/details/82622436)


多环境配置：

* 开发环境：application-dev.yml
* 生产环境：application-prod.yml
* 测试环境：application-test.yml


`application-dev.yml` 配置文件：

```yaml
server:
  port: 8080
spring:
  application:
    name: SpringBootDemo
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    name: defaultDataSource
    password: 123456
    url: jdbc:mysql://localhost:3307/demo?useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC
    username: root
  thymeleaf:
    cache: true
    check-template: true
    check-template-location: true
    servlet:
      content-type: text/html
    enabled: true
    encoding: UTF-8
    excluded-view-names: ''
    mode: HTML5
    prefix: classpath:/templates/
    suffix: .html
  jpa:
    show-sql: true
    hibernate:
      ddl-auto: update
logging:
  level:
    root: info
    com.yyt.springBootDemo: warn
  file:
    name: log/system.log
```

在主文件 `application.yml` 中配置如下：

```yaml
spring:
  profiles:
  	# 或者 prod
    active: dev
```


> 参考：[springboot配置之Profile多环境支持 - 西西嘛呦 - 博客园 (cnblogs.com)](https://www.cnblogs.com/xiximayou/p/12248340.html)


工具网站：

[在线properties转yaml-ToYaml.com](https://www.toyaml.com/index.html)



### 日志

Spring Boot 默认使用 LogBack 日志系统，日志的级别有7个：TRACE，DEBUG，INFO，WARN，ERROR，FATAL，OFF。

日志级别从低到高为: TRACE < DEBUG < INFO（默认） < WARN < ERROR < FATAL< OFF 。


一个项目可能有很多模块，设置让 `com.yyt.springBootDemo` 这个模块的输出日志为 warn 级别。

配置如下：

```yaml
# 日志级别配置
logging:
  level:
    # 让 root 日志只输出 info 及以上级别的信息
    root: info
    # package 日志以 warn 级别输出
    com.yyt.springBootDemo: warn
  # 日志输出保存到文件中
  file:
  	# 设置文件，可以是绝对路径，也可以是相对路径
  	name: log/system.log
```


> 参考：[一文了解SpringBoot的日志管理 | 带你读《SpringBoot实战教程》之七-阿里云开发者社区 (aliyun.com)](https://developer.aliyun.com/article/762856)



### AOP

"**在 AOP 方式中，可以反过来将日志服务模块化，并以声明的方式将它们应用到需要日志的组件上。**带来的优势就是 Java 类不需要知道日志服务的存在，也不需要考虑相关的代码。"

简单设计操作日志的表结构：`syslog`

| 编号 | 名称         | 字段          | 类型          | 说明                           |
| ---- | ------------ | ------------- | ------------- | ------------------------------ |
| 1    | UUID         | uuid          | char(32)      | 主键                           |
| 2    | 账号名称     | username      | varchar(100)  | 登录用户                       |
| 3    | 操作时间     | operateTime   | char(19)      | 格式：yyyy-MM-dd HH:mm:ss      |
| 4    | 操作类型     | operateType   | varchar(20)   | 增 / 删 / 查 / 改              |
| 5    | 操作简述     | operateDesc   | char(100)     | 简单描述                       |
| 6    | 操作详情     | operateDetail | text          | 以 JSON 格式返回操作影响的数据 |
| 7    | 操作系统类型 | osType        | varchar(20)   | 电脑的操作系统                 |
| 8    | 浏览器类型   | browserType   | varchar(30)   | 浏览器类型+版本号              |
| 9    | IP 地址      | ipAddr        | varbinary(16) | 兼容存储 IPv4 和 IPv6          |

关于 MySQL 存储 IP 地址用什么字段类型，搜索了一番。

* 因为一个 IPv 4 最小需要 7 个字符，最大需要 15 个字符，所以使用 VARCHAR(15) 即可；对于 IPv 6 最多 45 个字符，使用 VARCHAR(64) 也足够了。

* 使用 int 类型存储 IP 比 varchar 类型存储 IP 地址性能要提升很多，减少不少空间。因为 varchar 是可变长形，需要多余的一个字节存储长度。另外 int 型在逻辑运算上要比 varchar 速度快。而且 MySQL 提供了转换函数：INET_ATON() 和 INET_NTOA()、INET6_ATON() 和 INET6_NTOA()。
* "you can store them in a binary field with a length of 128 bits (16 bytes, `BINARY(16)` or `VARBINARY(16)`). "

> 参考：[sql - Most efficient way to store IP Address in MySQL - Stack Overflow](https://stackoverflow.com/questions/2542011/most-efficient-way-to-store-ip-address-in-mysql)



Spring Boot 导入 AOP 依赖、[Hutool — 🍬A set of tools that keep Java sweet.](https://www.hutool.cn/) 工具类、[User-agent-utils | bitwalker.eu](https://www.bitwalker.eu/software/user-agent-utils) 工具类：

```xml
<!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-aop -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
    <version>2.3.7.RELEASE</version>
</dependency>

<!-- https://mvnrepository.com/artifact/eu.bitwalker/UserAgentUtils -->
<dependency>
    <groupId>eu.bitwalker</groupId>
    <artifactId>UserAgentUtils</artifactId>
    <version>1.21</version>
</dependency>

<dependency>
    <groupId>cn.hutool</groupId>
    <artifactId>hutool-all</artifactId>
    <version>5.7.15</version>
</dependency>
```



> 参考1：[利用spring aop优雅地实现日志功能 - 知乎 (zhihu.com)](https://zhuanlan.zhihu.com/p/258479389)
>
> 参考2：[如何优雅地在 Spring Boot 中使用自定义注解，AOP 切面统一打印出入参日志 | 修订版 - 云+社区 - 腾讯云 (tencent.com)](https://cloud.tencent.com/developer/article/1445631)



### JPA

Java 持久层 API，使得应用程序以统一的方式访问持久层，JPA 与 Hibernate 的关系，就像 JDBC 与 JDBC 驱动的关系，JPA 是一套 ORM 规范，而 Hibernate 实现了这个规范。


Spring Data JPA 是 Spring 提供的一套简化 JPA 开发的框架，可以在不写接口实现的情况下，实现对数据库的访问和操作，同时提供了很多除了 CRUD 之外的功能，如分页、排序、复杂查询等。它的实现有很多，除了 Hibernate，还有 TopLink、OpenJPA......


配置如下：

```yaml
spring:
  application:
    name: SpringBootDemo
  # 数据库配置
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    name: defaultDataSource
    password: 123456
    url: jdbc:mysql://localhost:3307/demo?useUnicode=true&characterEncoding=utf-8&serverTimezone=UTC
    username: root
  thymeleaf:
    cache: true
    check-template: true
    check-template-location: true
    servlet:
      content-type: text/html
    enabled: true
    encoding: UTF-8
    excluded-view-names: ''
    mode: HTML5
    prefix: classpath:/templates/
    suffix: .html
  # JPA 配置
  jpa:
    show-sql: true
    hibernate:
      ddl-auto: update
```


注意，数据库要预先建好，因为 hibernate 只会建表，不会建库。

其中，关于 hibernate 的 `ddl-auto` 参数说明：

* ddl-auto：create —— 每次运行该程序，没有表格会新建表格，表内有数据会清空；
* ddl-auto：create-drop —— 每次程序结束的时候会清空表；
* ddl-auto：update —— 每次运行程序，没有表格会新建表格，表内有数据不会清空，只会更新；
* ddl-auto： validate —— 运行程序会校验数据与数据库的字段类型是否相同，不同会报错。


> 参考：[Spring Boot - JPA配置使用_mxjesse的博客-CSDN博客_jpa配置](https://blog.csdn.net/mxjesse/article/details/80558970)



### 数据库连接池

Druid：阿里巴巴开发的数据库连接池。


### 注解

#### 控制层

`@Controller`：创建的类上添加注解 @Controller 表示这是一个控制层类，返回的数据的方式即可以是 JSON/XML 数据，也可以是页面模板（jsp 页面、html 页面）；

`@RestController`：创建的类上添加注解 @RestController 表示这是一个控制层类，返回数据的方式只能是 JSON/XML 数据。

@RestController 在 Spring MVC 中就是 @Controller 和 @ResponseBody 注解的集合。@RestController 注解是从 Spring 4.0 版本开始添加进来的，主要用于更加方便的构建 RESTful Web 服务。



#### 业务层

`@Resource`：默认 byName 注入的方式，**适用于接口只能有一个实现类**，名称是 @Service 注解中标定的名称，如果通过 byName 的方式匹配不到，再按 byType 的方式去匹配。如 @Resource(name="companyService")，这里的 "companyService" 是业务层实现类里面的 @Service 注解中标定的名称。

`@Qualifier`：byName 注入的方式，但名称是类名，**适用于接口有多个实现类的场景**，如 @Qualifier(name="CompanyService")。

`@Autowired`：byType 注入方式，**要求接口只能有一个实现类**，Spring 会按 byType 的方式寻找接口的实现类，如果有多个实现类，Spring 不知道要引入哪个类，自然会报错。



#### URL 映射

`RequestMapping`：表示将特定的 URL 映射到指定的方法；如：RequestMapping("/xxx")

`GetMapping`：只接收 GET 方式的 URL 映射，相当于 RequestMapping(value="/xxx/yyy", method=RequestMethod.GET)

`PostMapping`：直接收 POST 方式的 URL 映射，相当于 @RequestMapping(value="/xxx/yyy", method=RequestMethod.POST)

注意：URL 映射中带不带 “/” 都行，规范要求来说应该要带，不加 ”/“ 就会在当前路径下找。



#### 参数接收

`JavaBean 对象`：没有任何注解，直接通过 JavaBean 对象来封装表单参数或者是请求 URL 路径中的参数。

`@RequestParam`:用于获取 Request 参数，也就是 URL 中的最后一部分，形如：?xx=a&yy=b。

`@PathVariable`：用于获取 URL 变量，一般 `{}` 中的变量名与方法中的形参名一致，用于 RESTful 设计风格。

`@RequestBody`：直接以 String 方式接收前端以 POST 方式传过来的 JSON 数据，在需要传递大量参数时很有用。类似于 JavaBean 对象，差别在于：这种方式传递参数，不需要 JavaBean 对象来封装参数，适用于多太条件查询场景，一般用 Map 集合来处理接收的参数。



### 页面跳转

在 `resources` 目录下的 `public` 文件夹中的页面是公开权限的，能随意访问的页面（例如：网站主页）。

`static` 文件夹中的资源一般是 CSS、JS、图片这种公共静态资源；其他页面中引用此文件夹下的资源都是直接访问即可。

> 参考：[SpringBoot中项目中static目录下的前端css/js不起作用_明天天明~的博客-CSDN博客](https://blog.csdn.net/qq_41950447/article/details/112465106)


`templates` 文件夹中的资源是需要 controller 控制跳转的，不可以直接访问的页面，直接访问会显示Whitelabel Error Page（例如：登录后才能访问的）。

```java
@RequestMapping("/showP1")
public String showPublicHtml(){
    // 测试返回公共文件夹 public 中的页面
    return "redirect:/demo.html";
}

@RequestMapping("/showP2")
public String showPrivateHtml(){
    // 测试返回安全文件夹 templates 中的页面
    return "/mydemo1.html";
}
```


### RESTful

这是一种设计风格，不是硬性要求。如果不是从资源考虑，对缓存的要求也不高，那就不要采用，因为多条件查询的场景下很麻烦。

* PostMapping 对应 增
* GetMapping 对应 查
* PutMapping 对应 改
* DeleteMapping 对应 删


> 参考：[RESTful API接口设计标准及规范；_时光偏执的博客-CSDN博客](https://blog.csdn.net/qq_41606973/article/details/86352787)


RESTful 风格的接口与传统的接口有些许区别：

* 传递形式：不能带参数名称，直接在 URL 后面带参数值就行。
* 中文转码：Postman 不能直接接传中文值，需要右键转码。



## 后台模板

大部分的后台模板由于插件较多，包都比较大，其实模板的核心就是两个文件：`模板的自身样式`、`模板的 JS 脚本`、图标资源、示例网页。



基于 Bootstrap 的后台模板主要为下面几部分：

* CSS
  * Bootstrap 的样式
  * 图标文件，一般使用 font-awesome
  * 模板的自身样式
* JS
  * JQuery
  * Bootstrap 脚本
  * 模板的自身脚本


​    

## Thymeleaf

Thymeleaf 是一种用于Web和独立环境的现代服务器端的 Java 模板引擎。

官网：[Thymeleaf](https://www.thymeleaf.org/)

除了添加必要的依赖外，还要在 HTML 页面将第二行的 `<html lang="en">` 标签改为 `<html xmlns:th="http://www.thymeleaf.org">`，这样就可以使用 Thymeleaf 的语法和规范。

```xml
<!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-thymeleaf -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
    <version>2.5.4</version>
</dependency>
```


> 参考：[Thymeleaf一篇就够了-阿里云开发者社区 (aliyun.com)](https://developer.aliyun.com/article/769977)



## Bootstrap-table

Bootstrap 的表格功能不够强大，需要一个 `bootstrap-table` 插件增强它。

官网：[Bootstrap Table · An extended table to the integration with some of the most widely used CSS frameworks. (Supports Bootstrap, Semantic UI, Bulma, Material Design, Foundation) (bootstrap-table.com)](https://bootstrap-table.com/)


> 参考1：[Bootstrap Table API 中文版（完整翻译文档）_Sclifftop - 保安大队长-CSDN博客](https://blog.csdn.net/S_clifftop/article/details/77937356)



运行时，出现错误：
```js
Uncaught TypeError: Cannot read properties of undefined (reading 'locales')     bootstrap-table-zh-CN.min.js:10 
at bootstrap-table-zh-CN.min.js:10
at bootstrap-table-zh-CN.min.js:10
at bootstrap-table-zh-CN.min.js:10
```



> 参考2：[Bootstrap中Cannot read property 'locales' of undefined at bootstrap-table-zh-CN.min.js:10的问题_v790873的博客-CSDN博客](https://blog.csdn.net/v790873/article/details/102770950)



数据加载完了，但还是一直显示“正在努力地加载数据中，请稍候”这些字样。

> 参考3：[bootstrap-table 一直显示“正在努力地加载数据中，请稍候”的问题_liuhualiang的专栏-CSDN博客](https://blog.csdn.net/liuhualiang/article/details/113172326)



## RequireJS

* 异步加载，不会阻塞页面，AMD 规范；
* 按需加载，不需要的模块就不加载；
* 依赖管理，能确保在所有的依赖模块都加载以后再执行相关的文件；
* 版本管理，只需要改动一处地方，则所有涉及到的页面就都改了；
* 多源准备，同一个 JS 组件，可以配置多个 CDN 加速、本地文件。





## Layui.layer

LayUI 已经不维护了，网址没了。

[layui: 一套开源的 Web UI 解决方案。 (gitee.com)](https://gitee.com/sentsin/layui)



主流的三种验证组件：jquery-validate、bootstrap-validator、formValidator。

* jquery-validate：功能强大，但是风格与 bootstrap 不搭；
* bootstrap-validator：官网：[Getting Started - BootstrapValidator (votintsev.ru)](http://bootstrapvalidator.votintsev.ru/getting-started/)，github：[nghuuphuoc/bootstrapvalidator: For anyone who want to use the previous version (BootstrapValidator) (github.com)](https://github.com/nghuuphuoc/bootstrapvalidator/)，开源的表单验证神器，基于 Bootstrap 3，已经不支持了；
* formValidator：官网：[The best validation library for JavaScript - FormValidation](https://formvalidation.io/)，收费，而且支付方式不方便，bootstrap-validator 开发者推出的收费版本。





## 文件上传

各种上传组件：[15 Best Free jQuery File Upload Scripts For Multiple File Upload 2021 - Colorlib](https://colorlib.com/wp/jquery-file-upload-scripts/)



Spring Boot 工程嵌入的 tomcat 限制了请求的文件大小，默认的上传文件限制大小为 1 MB，单次请求的文件总共不能超过 10 MB，超过就会出现以下错误：

```java
org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException: The field file exceeds its maximum permitted size of 1048576 bytes.
```



官方文档说明表示，每个文件的配置最大为 1 MB，单次请求的文件的总数不能大于 10 MB。需要在配置文件:`application.properties`中修改这两个配置：

```yaml
spring:
  application:
    name: SpringBootDemo
  servlet:
    multipart:
      enabled: true
      max-file-size: -1
      max-request-size: -1
```





## 分页查询

Specification：用于封装 JPA Criteria 查询条件。

JpaRepository 中可以通过面向对象的方式进行查询，如 `findByComnameAndContactname`，但是条件一多、查询稍微特殊一点，这种方式就有点反人类了。

步骤：

1. 在数据仓库 Repository 接口中增加继承的接口 `JpaSpecificationExecutor`；
2. 在业务层中增加复杂查询的接口；
3. 在业务层实现类中，基于 Specification 增加一个复杂查询的方法；
4. 在控制层中新增多条件查询分页的接口；
5. 在公司列表页面增加查询条件表单；
6. 在公司列表 JS 文件中增加代码片段:
   * 修改接口的地址；
   * 在数据表格 bootstrap-table 中增加参数传递；
   * 给搜索按钮设置点击事件。





## Apache ECharts

官网：[Apache ECharts](https://echarts.apache.org/zh/index.html)

步骤：

1. 服务端返回一个或多个 Map 集合，每一个 Map 集合中再存放一个 List 集合（如果只有一个图表，为了方便，可以只返回一个 List 集合）。
2. List 集合中的数据格式：为了统一，需要定义一个实体（不与数据表对应），该实体包括两个属性：name 与 value。
3. 利用 Ajax 从服务端获取数据，遍历获取得到的 json 数据，根据不同类型图表对数据的要求，再进行组装，最后赋值给 ECharts。



配置文档：[Documentation - Apache ECharts](https://echarts.apache.org/zh/option.html#title)





## Spring Security

Spring Security是为基于Spring的应用程序提供声明式安全保护的安全性框架。

Spring Security提供了完整的安全性解决方案，包括用户认证（ Authentication ）和用户权限（ Authorization ）两部分。

1. 认证：验证用户名和密码是否合法；
2. 授权：验证某用户是否有权限执行某个操作。

Spring security 没有角色的概念，只有账号和权限，角色这个概念是我们在实际生产环境为了方便而增加的。

Spring Security 中权限对比的唯一依据就是权限的名称，而不是 url，所以在定义权限表时定义 url 列是多余无用的。



默认账号是 user，密码是控制台出现的字符串。



```java
@SpringBootApplication
@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
public class SpringBootDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoApplication.class, args);
    }

}
```

IDEA 会出现提示：**Attributes should be specified via @SpringBootApplication** ，因为 @SpringBootApplication 中已经包含 @EnableAutoConfiguration，不需要重复定义。所以，改成这样：

```java
@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
public class SpringBootDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoApplication.class, args);
    }

}
```



JPA 的 CascadeType 属性 和 FetchType属性：

> 参考：[JPA CascadeType 通俗解释 – Pluveto](https://www.pluvet.com/2020/07/05/jpa-cascadetype-通俗解释/)





### 数据库表

权限表：sysauth

| 编号 | 名称         | 字段名    | 类型         | 说明                                     |
| ---- | ------------ | --------- | ------------ | ---------------------------------------- |
| 1    | UUID         | uuid      | char(32)     | 主键                                     |
| 2    | 权限名称     | powername | varchar(200) | 唯一性                                   |
| 3    | 树形节点名称 | treename  | varchar(20)  | 用于显示在树形节点上的名称，用于后台区分 |
| 4    | 本身ID       | id        | int          |                                          |
| 5    | 父ID         | pid       | int          |                                          |

角色表：sysrole

| 编号 | 名称     | 字段名   | 类型         | 说明             |
| ---- | -------- | -------- | ------------ | ---------------- |
| 1    | UUID     | uudi     | char(32)     | 主键             |
| 2    | 角色名称 | rolename | varchar(30)  | 角色名称（唯一） |
| 3    | 角色描述 | roledesc | varchar(200) | 角色描述         |

账户表：sysuser

| 编号 | 名称       | 字段名      | 类型         | 说明                   |
| ---- | ---------- | ----------- | ------------ | ---------------------- |
| 1    | UUID       | uuid        | char(32)     | 主键                   |
| 2    | 用户名称   | username    | varchar(100) | 唯一                   |
| 3    | 用户密码   | password    | varchar(100) |                        |
| 4    | 用户邮箱   | useremail   | varchar(100) | 唯一                   |
| 5    | 用户手机号 | usermobile  | char(11)     | 唯一                   |
| 6    | 角色名称   | sysrolename | varchar(30)  | 对应角色表中的角色名称 |
| 7    | 角色ID     | sysroleid   | char(32)     | 对应角色表中的uuid     |



为了查询数据方便，在账户实体类、角色实体类中增加映射关系：

1. 账户实体类中需要与角色配置 Hibernate 一对一映射，这样在获取到账户时，就获得了角色。
2. 角色实体类中需要与权限配置 Hibernate 多对多映射，这样可以方便获取角色对应的全部权限。



### 树形插件

* zTree 官网：[Home [zTree -- jQuery 树插件\] (treejs.cn)](http://www.treejs.cn/v3/main.php#_zTreeInfo)

  CDN 加速：[zTree.v3 (v3.5.42) - jquery tree plugin | BootCDN - Bootstrap 中文网开源项目免费 CDN 加速服务](https://www.bootcdn.cn/zTree.v3/)

  美化：[zTree 的一种美肤方案 - 知乎 (zhihu.com)](https://zhuanlan.zhihu.com/p/51537293)

* jsTree 下载：[jsTree](https://www.jstree.com/)



## jasper 报表

其官网：[Jaspersoft Community](https://community.jaspersoft.com/)

JasperReports 是一个开源的报表生成工具。模板设计器推荐使用 Jaspersoft Studio。因为是基于 Eclipse 软件开发的，所以主界面布局很像 Eclipse，也分为商业版和社区版，也有安装版和便携版。下载地址：[Jaspersoft® Studio | Jaspersoft Community](https://community.jaspersoft.com/project/jaspersoft-studio/releases)

流程：

1. 用 Jaspersoft Studio 链接数据库，获取需要使用的数据。
2. 设计好 xxx.jrxml 文件后，编译为 xxx.jasper 文件。
3. Spring Boot 引入相关依赖。



### 依赖

```xml
<!-- https://mvnrepository.com/artifact/net.sf.jasperreports/jasperreports -->
<dependency>
    <groupId>net.sf.jasperreports</groupId>
    <artifactId>jasperreports</artifactId>
    <version>6.17.0</version>
    <!-- 切断对 itext 的依赖 -->
    <exclusions>
        <exclusion>
            <groupId>com.lowagie</groupId>
            <artifactId>itext</artifactId>
        </exclusion>
    </exclusions>
</dependency>
<!-- https://mvnrepository.com/artifact/org.apache.poi/poi -->
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi</artifactId>
    <version>5.0.0</version>
</dependency>
<!-- https://mvnrepository.com/artifact/org.apache.poi/poi-ooxml -->
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi-ooxml</artifactId>
    <version>5.0.0</version>
</dependency>
<!-- 以下可用可不用 -->
<!-- https://mvnrepository.com/artifact/com.itextpdf/itextpdf -->
<dependency>
    <groupId>com.itextpdf</groupId>
    <artifactId>itextpdf</artifactId>
    <version>5.5.13.2</version>
</dependency>
<!-- https://mvnrepository.com/artifact/com.itextpdf/itext-asian -->
<dependency>
    <groupId>com.itextpdf</groupId>
    <artifactId>itext-asian</artifactId>
    <version>5.2.0</version>
</dependency>
```



运行时总是出现错误：**尚未解决**

```java
java.lang.ClassNotFoundException: net.sf.jasperreports.compilers.ReportExpressionEvaluationData
```





> 参考1：[JasperReport 报表在SpringMVC WEB项目中的应用(JavaBean作为数据源)_嗡汤圆的博客-CSDN博客](https://blog.csdn.net/tzdwsy/article/details/50595313)
>
> 参考2：[iText5实现Java生成PDF文件完整版_张小洛的BOLG-CSDN博客_itextpdf](https://blog.csdn.net/weixin_37848710/article/details/89522862)
>
> 参考3：[Apache POI基本介绍---入门级_凉拌~玛卡巴卡-CSDN博客_apache poi](https://blog.csdn.net/qq_44316726/article/details/105495959)

