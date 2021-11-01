# SpringBootTemplate
Spring Boot 项目通用模板。

如果这个对你有些许帮助，不妨点一下 Star 🌟🌟，谢谢。



## 技术栈

### 前端

| 名称               | 说明                                            | 官网                                                         |
| ------------------ | ----------------------------------------------- | ------------------------------------------------------------ |
| Bootstrap          | Twitter 推出的前端组件库                        | [Bootstrap (getbootstrap.com)](https://getbootstrap.com/)    |
| Bootstrap Table    | 增强 Bootstrap 的表格                           | [Bootstrap Table (bootstrap-table.com)](https://bootstrap-table.com/) |
| BootstrapValidator | 基于 Bootstrap 的表单验证插件，**不更新维护了** | [nghuuphuoc/bootstrapvalidator (github.com)](https://github.com/nghuuphuoc/bootstrapvalidator/) |
| jQuery             | JavaScript 的封装库                             | [jQuery](https://jquery.com/)                                |
| ECharts            | 一个基于 JavaScript 的开源可视化图表库          | [Apache ECharts](https://echarts.apache.org/zh/index.html)   |



### 后端

| 名称             | 说明                                  | 官网                                                         |
| ---------------- | ------------------------------------- | ------------------------------------------------------------ |
| AdminLTE         | 基于 Bootstrap 的开源后台管理模板主题 | [Free Bootstrap Admin Template](https://adminlte.io/)        |
| Gentelella Admin | 基于 Bootstrap 的开源后台管理模板主题 | [ColorlibHQ/gentelella (github.com)](https://github.com/ColorlibHQ/gentelella) |
| Spring Boot      | Spring 框架的扩展，简化构建配置       | [Spring Boot](https://spring.io/projects/spring-boot)        |
| Spring Security  | Spring 家族的安全管理框架             | [Spring Security](https://spring.io/projects/spring-security) |
| Thymeleaf        | Java 模板引擎                         | [Thymeleaf](https://www.thymeleaf.org/)                      |



### 工具

| 名称     | 说明                                                         | 官网                                                         |
| -------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| CHINER   | 以前叫 **PDMan**，是一款开源免费的数据库模型建模工具         | [pdman (gitee.com)](https://gitee.com/robergroup/pdman/)     |
| Postman  | Google 开发的一款功能强大的测试 API 与请求的工具             | [Postman](https://www.postman.com/)                          |
| Lombok   | 提供一些注解，消除冗长的样板式代码                           | [Project Lombok](https://projectlombok.org/)                 |
| FastJson | 阿里巴巴的开源 JSON 解析库，用于 Java Bean 和 JSON 字符串之间的转换 | [alibaba/fastjson (github.com)](https://github.com/alibaba/fastjson) |
| Hutool   | 一个小而全的 Java 工具类库                                   | [Hutool — 🍬A set of tools that keep Java sweet.](https://www.hutool.cn/) |





## 说明

### 配置文件

Spring Boot 支持两种格式的配置文件：默认的 `application.properties` 和 `application.yml`。application.properties 优先级高于 application.yml。

所以，如果项目中同时存在 application.properties 和 application.yml 配置文件，则 application.yml 会先加载，然后再加载 application.properties，后加载的 application.properties 文件会覆盖 application.yml 文件。

**多环境配置文件**

通常一套程序会被应用和安装到几个不同的环境，比如：开发，测试，生产等。在 SpringBoot 项目中部署多环境配置文件，文件名需要满足 application-{profile}.properties 的格式，其中 {profile} 对应的是环境标识，例如：

* 开发环境：application-dev.yml
* 生产环境：application-prod.yml
* 测试环境：application-test.yml



> 参考：[springboot多环境配置文件，如何包含多个yml配置文件？看这一篇就够了_我是Cj，一个正义之人-CSDN博客_springboot 多个yml](https://blog.csdn.net/xiaorui51/article/details/108452181)





### 资源加载

常用的 CDN 网站：

1. [jsDelivr - A free, fast, and reliable CDN for open source](https://www.jsdelivr.com/)
2. [Staticfile CDN](https://www.staticfile.org/)
3. [BootCDN - Bootstrap 中文网开源项目免费 CDN 加速服务](https://www.bootcdn.cn/)
4. [字节跳动静态资源公共库 (bytedance.com)](http://cdn.bytedance.com/)
5. [静态资源托管库 (baomitu.com)](https://cdn.baomitu.com/)



常用的 Maven 仓库：

1. [Maven Repository: Search/Browse/Explore (mvnrepository.com)](https://mvnrepository.com/)
2. [阿里云云效 Maven (aliyun.com)](https://developer.aliyun.com/mvn/guide)



常用的图标 / 字体库：

1. [Google Fonts | 谷歌字体中文版 | GoogleFonts](http://www.googlefonts.cn/)
2. [Nerd Fonts - Iconic font aggregator, glyphs/icons collection, & fonts patcher](https://www.nerdfonts.com/font-downloads)
3. [iconfont-阿里巴巴矢量图标库](https://www.iconfont.cn/)
4. [ByteDance IconPark (oceanengine.com)](https://iconpark.oceanengine.com/home)



### 补充

* "版本任你发，我用 Java 8"。现在 JDK 16 都发布了，目前的 LTS 是 Java 11，建议换用 Java 11。
* Spring Security 与 JWT([JSON Web Tokens - jwt.io](https://jwt.io/)) 搭配使用，可实现的功能更强。
* 模板引擎 Thymeleaf 性能不好，建议使用 Beetl：[Beetl 官网 (ibeetl.com)](http://ibeetl.com/)。根据其官网说明：Beetl 远超过主流 java 模板引擎性能(引擎性能 5-6 倍于 freemaker，2 倍于 JSP)！！！
* 
