# u-na

> u-na:一个基于微服务架构的前后端分离的寄托希望梦想的内容分享系统.

## 1.0 Star and Fork

`u-na: u-na:一个基于微服务架构的前后端分离的寄托希望梦想的内容分享系统.`

> https://gitee.com/ahviplc/u-na

`ahviplc/u-na:u-na:一个基于微服务架构的前后端分离的寄托希望梦想的内容分享系统.`

> https://github.com/ahviplc/u-na

## 2.0 技术栈

```markdown
springboot
maven
git

对应使用的组件如下：
注册中心：nacos，替代方案eureka、consul、zookeeper
配置中心: nacos ，替代方案sc config、consul config
服务调用:feign，替代方案：resttempate
熔断：sentinel、，替代方案：Resilience4j
熔断监控：sentinel dashboard
负载均衡:sc loadbalancer
网关：spring cloud gateway
链路：spring cloud sleuth+zipkin，替代方案：skywalking等。
未完待续...
```

## 3.0 搭建项目运行环境

### 3.1 aliyun

> 使用 lj-linux

```markdown
已暴露的端口
80 8088 3389 22
8090-8099
tomcat 8080
mysql 3360
oracle 1521
nacos 8848
redis 6379
gateway 5000
sentinel 8748
zipkin 9411

`una所有组件所有模块端口`
mysql 3360
nacos 8848
sentinel 8748
zipkin 9411
una-utils 114
una-gateway 5000
una-provider 8762
una-consumer 8763
una-admin 8090

其他端口
pass
```

### 3.2 docker

#### 3.2.1 安装Docker Compose

> https://docs.docker.com/compose/install/

```sh
1. sudo curl -L "https://github.com/docker/compose/releases/download/1.29.1/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose

2. sudo chmod +x /usr/local/bin/docker-compose
3. Test the installation.
docker-compose --version
```

### 3.3 nacos

`作为注册中心和配置中心`

#### 3.3.1 使用docker部署nacos

`使用docker部署nacos`

> https://www.cnblogs.com/jeecg158/p/14029453.html

#### 3.3.2 安装nacos

`具体安装nacos`

> https://www.cnblogs.com/jeecg158/p/14029453.html

```markdown
1. docker search nacos
2. docker pull nacos/nacos-server
3. docker images
4. docker run -d -p 8848:8848 --env MODE=standalone --name nacos-server-run docker.io/nacos/nacos-server
5. docker logs -f --tail=30 82feb08be237
6. 验证是否成功
   http://localhost:8848/nacos
   默认用户名密码都是： nacos
7. done.
```

#### 3.3.3 手动下载安装运行nacos

`从github下载,启动也可`

> https://github.com/alibaba/nacos/releases

`下载完 导入 对应 nacos-config 数据库并配置 然后以下执行命令 单机模式启动`

> .\startup.cmd -m standalone

### 3.4 sentinel

`作为熔断器`

`从github下载`

> https://github.com/alibaba/Sentinel/releases

`下载完 启动 需在 git bash 命令窗口下执行 端口8748`

> java -Dserver.port=8748 -Dcsp.sentinel.dashboard.server=localhost:8748
> -Dproject.name=sentinel-dashboard -jar sentinel-dashboard-1.8.1.jar

`默认使用8080端口`

> java -jar .\sentinel-dashboard-1.8.1.jar

`una-consumer应用名,服务的/hi-feign接口，增加一个流控规则`

```markdown
在 `GET:http://una-provider/hi` 点击 +流控 按钮

单机阈值设置为 1

qps为1，快速访问 http://localhost:5000/consumer/hi-feign，会有失败的情况 会报以下错误:
Blocked by Sentinel: ParamFlowException

也可以在 Spring cloud gateway 上使用sentinel.
流控规则为qps=1，
快速访问 http://localhost:5000/consumer/hi-feign
快速访问 http://localhost:5000/provider/hi
在qps>1的情况下，会报以下的错误：
Blocked by Sentinel: FlowException
可见gateway上的sentinel的配置已经生效.
```

### 3.5 Spring Cloud Sleuth+zipkin

`实现链路追踪`

`下载地址如下，并启动：`

```sh
curl -sSL https://zipkin.io/quickstart.sh | bash -s
java -jar zipkin.jar
```

`访问zipkin的ui 界面，地址为localhost:9411`

## 4.0 搭建项目代码

`代码构建`

### 4.1 引入日志

`@Slf4j`

`配合使用 hutool 的 StaticLog`

`日志文件配置参考`

> una-consumer/src/main/resources/logback-spring.xml

`会对应产生以下目录和日志文件`

```markdown
u-na/logs/una-consumer
u-na/logs/una-consumer/una-consumer-error.log
u-na/logs/una-consumer/una-consumer-info.log
```

### 4.2 引入knife4j

> knife4j 集成Swagger生成Api文档的增强解决方案

`进行相关配置之后,访问 ip + 网关模块端口5000 + doc.html`

`ip:port/doc.html`

> http://localhost:5000/doc.html

`要是某些模块增加swagger密码访问的权限,需要输入用户名和密码,默认都是:` `admin`

### 4.3 引入工具类模块

`una-utils:常用工具类模块`

### 4.4 引入banner

`una-utils`

> http://patorjk.com/software/taag/#p=testall&f=Graffiti&t=u-na

`una-provider`

> http://patorjk.com/software/taag/#p=display&f=ANSI%20Shadow&t=u-na

`una-gateway`

> http://patorjk.com/software/taag/#p=display&f=Bloody&t=u-na

`una-consumer`

> http://patorjk.com/software/taag/#p=display&f=Cards&t=u-na

### 4.5 引入AOP

> una-admin/src/main/java/com/lc/una/admin/annotion/AuthorityVerify/AuthorityVerify.java

> una-admin/src/main/java/com/lc/una/admin/annotion/CallMeLog/CallMeLog.java

### 4.6 引入docker maven

`使用开源项目io.fabric8之docker-maven-plugin插件打包Docker镜像 要是有私服
也可推送到私服`

> una-gateway/Dockerfile

> una-provider/Dockerfile

> una-consumer/Dockerfile

> una-admin/Dockerfile

#### 4.6.1 相关pom.xml文件的maven插件

```xml
<!--docker maven-->
<plugin>
    <groupId>io.fabric8</groupId>
    <artifactId>docker-maven-plugin</artifactId>
    <version>0.35.0</version>
    <!--全局配置-->
    <configuration>
        <!--配置远程docker守护进程url-->
        <!--http://192.168.0.6:2375 也可-->
        <dockerHost>tcp://192.168.0.6:2375</dockerHost>
        <!--认证配置,用于私有registry认证-->
        <!--<authConfig>
            <username>admin</username>
            <password>admin</password>
        </authConfig>-->
        <!--镜像相关配置,支持多镜像-->
        <images>
            <!-- 单个镜像配置 -->
            <image>
                <!--镜像名(含版本号)-->
                <name>ahviplc/${project.name}:${project.version}</name>
                <!--registry地址,用于推送,拉取镜像-->
                <registry>192.168.0.6</registry>
                <!--镜像build相关配置-->
                <build>
                    <!--使用dockerFile文件-->
                    <dockerFile>${project.basedir}/Dockerfile</dockerFile>
                </build>
            </image>
        </images>
    </configuration>
</plugin>
```

#### 4.6.2 执行相关命令

```bash
`java项目打包同时打包Docker镜像命令`
首先 进入u-na根目录
cd u-na
然后执行命令:
mvn clean package

`cd到有mavend插件ocker-maven-plugin的项目根目录执行下面命令` `打包Docker镜像`
比如:
cd una-admin
也就是根目录含有Dockerfile的项目
然后执行命令:
mvn docker:build

`要是有私服 也可推送到私服`
mvn docker:push

`运行docker镜像命令`
docker run -dit --name una-gateway-run -p 5000:5000 -v /docker-data/una/una-gateway/logs:/una/deploy/app/logs ahviplc/una-gateway:1.0.0

docker run -dit --name una-provider-run -p 8762:8762 -v /docker-data/una/una-provider/logs:/una/deploy/app/logs ahviplc/una-provider:1.0.0

docker run -dit --name una-consumer-run -p 8763:8763 -v /docker-data/una/una-consumer/logs:/una/deploy/app/logs ahviplc/una-consumer:1.0.0

docker run -dit --name una-admin-run -p 8090:8090 -v /docker-data/una/una-admin/logs:/una/deploy/app/logs ahviplc/una-admin:1.0.0

参数说明:
其中关键参数为-d，指定容器运行与前台或者后台，不加上时前台
-i: 打开STDIN，用于控制台交互
-t: 支持终端登录

`扩展使用$PWD`
cd /docker-data 目录下 执行下面命令也可 其他项目类似:
docker run -dit --name una-gateway-run -p 5000:5000 -v $PWD/una/una-gateway/logs:/una/deploy/app/logs ahviplc/una-gateway:1.0.0

测试访问:
http://192.168.0.6:5000/provider/nowTime

http://192.168.0.6:8762/hi

docker版的nacos访问: `账户密码都是nacos`
http://192.168.0.6:8848/nacos/#/login
```

### 4.7 引入docker-compose

```
1. 创建docker的una网络
docker network create una
`默认网桥模式`

2. 执行start
docker-compose -p una-admin -f ../yaml/una-admin.yml up -d

3. 执行down
docker-compose -p una-admin -f ../yaml/una-admin.yml down

4. 批量操作
una-docs/docker-compose/bin/unaStartup.sh
una-docs/docker-compose/bin/unaShutdown.sh
```

## 5.0 访问我

`下面是此项目的一些访问链接`

`nacos` `账户密码都是nacos`

> http://localhost:8848/nacos/index.html#/login

`远程调用` `服务消费者模块una-consumer`

> http://localhost:8763/hi-feign

`直接调用` `服务提供者模块una-provider`

> http://localhost:8762/hi?name=ahviplc

> http://localhost:8762/nowTime

`引入网关gateway调用` `网关端口5000`

`服务消费者模块una-consumer`

> http://localhost:5000/consumer/hi-feign

`服务提供者模块una-provider`

> http://localhost:5000/provider/hi?name=shviplc

> http://localhost:5000/provider/nowTime

`后端admin模块`

`不使用网关` `端口8090`

> http://localhost:8090/admin/getList

`使用网关` `网关端口5000` `具体访问路径取决于una-gateway网关配置`

> http://localhost:5000/una-admin/admin/getList

`引入Sentinel作为熔断器` `账户密码都是sentinel`
`只有网关una-gateway模块和简单消费者una-consumer模块配置了`

> http://localhost:8748

`访问zipkin的ui界面`

> http://localhost:9411

`访问una的Api接口文档`

> http://localhost:5000/doc.html

> http://localhost:5000/doc.html#/una-admin/una%E7%9A%84%E5%90%8E%E7%AB%AFadmin%E6%A8%A1%E5%9D%97%E7%9A%84admin%E7%AE%A1%E7%90%86%E5%91%98%E8%A1%A8/getListUsingPOST

`actuator` `使用actuator进行健康监控`
`目前只有una-admin模块有引入其依赖(因为只有una-admin模块引入了una-xo模块-此una-xo模块内包una-utils模块
una-utils模块的pom.xml中有引入此依赖)`

> http://localhost:8090/actuator

> http://localhost:8090/actuator/health

## 6.0 参考资料

```markdown
Install Docker Compose | Docker Documentation
https://docs.docker.com/compose/install/

docker安装nacos - zhangdaiscott - 博客园
https://www.cnblogs.com/jeecg158/p/14029453.html

docker安装nacos_青岚飞雪博客-CSDN博客
https://blog.csdn.net/qq_36215276/article/details/111922453

Nacos 快速开始
https://nacos.io/zh-cn/docs/quick-start.html

Releases · alibaba/nacos · GitHub
https://github.com/alibaba/nacos/releases

Nacos Docker 快速开始
https://nacos.io/zh-cn/docs/quick-start-docker.html

Spring Boot
https://spring.io/projects/spring-boot

Spring Cloud
https://spring.io/projects/spring-cloud

Spring Cloud Alibaba
https://spring.io/projects/spring-cloud-alibaba

SpringCloud 2020版本教程1：使用nacos作为注册中心和配置中心 - 方志朋的博客
https://www.fangzhipeng.com/springcloud/2021/04/02/sc-2020-nacos.html

SpringCloudLearning/sc-2020-chapter1 at master · forezp/SpringCloudLearning · GitHub
https://github.com/forezp/SpringCloudLearning/tree/master/sc-2020-chapter1

SpringCloud 2020版本教程2：使用spring cloud gateway作为服务网关 - 方志朋的博客
https://www.fangzhipeng.com/springcloud/2021/04/03/sc-2020-gateway.html

SpringCloudLearning/sc-2020-chapter2 at master · forezp/SpringCloudLearning
https://github.com/forezp/SpringCloudLearning/tree/master/sc-2020-chapter2

gateway 不拦截指定路径_Spring Cloud Alibaba 路由网关(Gateway)_董天成的博客-CSDN博客
https://blog.csdn.net/weixin_42393621/article/details/112404828

SpringCloud 2020版本教程3：使用sentinel作为熔断器 - 方志朋的博客
https://www.fangzhipeng.com/springcloud/2021/04/04/sc-2020-sentinel.html

SpringCloudLearning/sc-2020-chapter3 at master · forezp/SpringCloudLearning
https://github.com/forezp/SpringCloudLearning/tree/master/sc-2020-chapter3

Releases · alibaba/Sentinel
https://github.com/alibaba/Sentinel/releases

SpringCloud 2020版本教程4：使用spring cloud sleuth+zipkin实现链路追踪 - 方志朋的博客
https://www.fangzhipeng.com/springcloud/2021/04/05/sc-2020-sleuth.html

SpringCloudLearning/sc-2020-chapter4 at master · forezp/SpringCloudLearning · GitHub
https://github.com/forezp/SpringCloudLearning/tree/master/sc-2020-chapter4

Sentinel · alibaba/spring-cloud-alibaba Wiki
https://github.com/alibaba/spring-cloud-alibaba/wiki/Sentinel

网关限流 · alibaba/Sentinel Wiki
https://github.com/alibaba/Sentinel/wiki/%E7%BD%91%E5%85%B3%E9%99%90%E6%B5%81

spring中 allowBeanDefinitionOverriding(spring.main.allow-bean-definition-overriding)
https://blog.csdn.net/liubenlong007/article/details/87885567

在线yaml转properties-在线properties转yaml-ToYaml.com
https://www.toyaml.com/index.html

Maven实战（六）--- dependencies与dependencyManagement的区别_信息技术提高班第九期-CSDN博客
https://blog.csdn.net/liutengteng130/article/details/46991829

SLF4J Documentation
http://www.slf4j.org/docs.html

hutool-log/src/test/java/cn/hutool/log/test/StaticLogTest.java · dromara/hutool - 码云 - 开源中国
https://gitee.com/dromara/hutool/blob/v5-master/hutool-log/src/test/java/cn/hutool/log/test/StaticLogTest.java

mogu_picture/src/main/resources/logback-spring.xml · 陌 溪/蘑菇博客 - Gitee.com
https://gitee.com/moxi159753/mogu_blog_v2/blob/Nacos/mogu_picture/src/main/resources/logback-spring.xml

gateway-nacos-knife4j整合加访问权限控制(详细教程适合新手入门)_FREE_GIFT_白给怪的博客-CSDN博客
https://blog.csdn.net/qq_43578385/article/details/111865228

knife4j: knife4j是为Java MVC框架集成Swagger生成Api文档的工具,前身是swagger-bootstrap-ui
https://gitee.com/xiaoym/knife4j

mogu_utils · 陌 溪/蘑菇博客 - 码云 - 开源中国
https://gitee.com/moxi159753/mogu_blog_v2/tree/Nacos/mogu_utils

Intellij IDEA运行报Command line is too long的解决办法 - SegmentFault 思否
https://segmentfault.com/a/1190000022547084

gateway-route-filters | Spring Cloud Gateway
https://cloud.spring.io/spring-cloud-gateway/2.0.x/single/spring-cloud-gateway.html#gateway-route-filters

MyBatis-Plus
https://mp.baomidou.com/

GitHub - baomidou/mybatis-plus: An powerful enhanced toolkit of MyBatis for simplify development
https://github.com/baomidou/mybatis-plus

MybatisX 快速开发插件 | MyBatis-Plus
https://mp.baomidou.com/guide/mybatisx-idea-plugin.html

MybatisX: MybatisX 快速开发插件
https://gitee.com/baomidou/MybatisX

MyBatis-Plus 使用xml文件 - 枫树湾河桥 - 博客园
https://www.cnblogs.com/fswhq/p/13634195.html

配置mybatis-plus的xml遇到的坑_humorrr的博客-CSDN博客
https://blog.csdn.net/qq_36561105/article/details/109034284

java中XO -> PO(entity) VO BO DTO DAO POJO DO 概念-CSDN博客
https://blog.csdn.net/qq_44801116/article/details/105211678

intellij idea全局查找和替换_分享传递价值-CSDN博客_idea全局替换
https://blog.csdn.net/fanrenxiang/article/details/80168215

@ComponentScan 详解_dxyzhbb的博客-CSDN博客_componentscan
https://blog.csdn.net/dxyzhbb/article/details/109533399

6-SpringAOP-什么是面向切面编程？ | 515code-实验室 | 下面是对应视频
https://www.515code.com/posts/egs95cdu/

6-【2020实战】SpringBoot零基础入门04-AOP面向切面编程与日志框架_哔哩哔哩 (゜-゜)つロ 干杯~-bilibili
https://www.bilibili.com/video/BV1KT4y1G7hs

aop execution 表达式解析 - JAVA-ANDROID - 博客园
https://www.cnblogs.com/Struts-pring/p/9881716.html

6-IntelliJ IDEA自动导入包去除星号（import xxx.*）-【导入同一个包的类超过这个数值自动变为 * 】
https://www.pianshen.com/article/13521945425/

6-与上面一起使用效果更好-intelliJ IDEA自动优化导入包-【Optimize imports on the fly：自动去掉一些没有用到的包 Add unambiguous imports on the fly：自动帮我们优化导入的包】  
https://blog.csdn.net/assassinsshadow/article/details/73730817

Spring Boot 整合redis的两种方式_saltsoul的博客-CSDN博客
https://blog.csdn.net/saltsoul/article/details/97097817

一个走心的RedisUtil工具类_JustryDeng-CSDN博客_redisutils工具类方法
https://blog.csdn.net/justry_deng/article/details/104890620/

CommonRepository/Abc_RedisUtil_Demo at master · JustryDeng/CommonRepository · GitHub - 一个走心的RedisUtil工具类 demo
https://github.com/JustryDeng/CommonRepository/tree/master/Abc_RedisUtil_Demo

SpringBoot重点详解--使用Actuator进行健康监控_pengjunlee的博客-CSDN博客
https://blog.csdn.net/pengjunlee/article/details/80235390

应用监控之 SpringBoot Actuator 使用及配置_JTraveler的博客-CSDN博客
https://blog.csdn.net/weixin_44176169/article/details/105402127

java注解-ElementType详解_SingleShu的博客-CSDN博客
https://blog.csdn.net/sw5131899/article/details/54947192

Maven 插件之 spotify docker-maven-plugin 的使用 - 星朝 - 博客园
https://www.cnblogs.com/jpfss/p/10945324.html

GitHub - spotify/docker-maven-plugin: INACTIVE: A maven plugin for Docker
https://github.com/spotify/docker-maven-plugin

GitHub - spotify/dockerfile-maven: MATURE: A set of Maven tools for dealing with Dockerfiles
https://github.com/spotify/dockerfile-maven

6-很不错-借鉴了-K8S入门系列(10)-使用开源项目io.fabric8之docker-maven-plugin插件打包Docker镜像至私服_云烟成雨csdn的博客-CSDN博客
https://blog.csdn.net/qq_43437874/article/details/106913747

GitHub - fabric8io/docker-maven-plugin: Maven plugin for running and creating Docker images
https://github.com/fabric8io/docker-maven-plugin
```

## 7.0 其他

### 7.1 扩展知识点

#### 7.1.1 spring cloud gateway 配置项的那些事

```yaml
spring:
  application:
    name: una-gateway
  cloud:
    nacos:
      username: nacos
      password: nacos
      discovery:
        server-addr: 127.0.0.1:8848
        service: una-gateway
        namespace: dev
      config:
        server-addr: localhost:8848
        file-extension: yaml
        # 指定分组
        group: dev
        # 指定命名空间
        namespace: dev
    gateway:
      discovery:
        locator:
          enabled: false # 让gateway可以发现nacos中的微服务 开启后会自动去掉一层路径,且routes会失效
                         # 如果完全注释routes,knife4j会失效
                         # 是否与服务注册于发现组件进行结合，通过 serviceId 转发到具体的服务实例。
                         # 默认为 false，设为 true 便开启通过服务中心的自动根据 serviceId 创建路由的功能
          # 表示将请求路径的服务名配置改成小写  因为服务注册的时候，向注册中心注册时将服务名转成大写的了
          lowerCaseServiceId: true
      routes: # 路由数组  指当请求满足什么样的条件的时候，转发到哪个微服务上
        - id: una-provider # 当前路由标识，要求唯一 （默认值uuid，一般不用，需要自定义）
          uri: lb://una-provider # 请求最终要被转发的地址   lb指的是从nacos中按照名称获取微服务,并遵循负载均衡策略
          predicates: # 断言 判断条件，返回值是boolean 转发请求要返回的条件 （可以写多个）
            - Path=/una-provider/** # 当请求路径满足path指定的规则时，此路由信息才会正常转发
          filters: # 过滤器（在请求传递过程中，对请求做一些手脚）
            - StripPrefix=1 # 在请求转发之前去掉一层路径
```

#### 7.1.2 关于抛出异常的那些事

```markdown
1.0 如果需要控制台具体抛出打印错误异常信息
声明下面日志工厂类 获取log
static Log log = LogFactory.get();
然后【log.error("系统统一异常处理2：", exception);】
即可
或者
import lombok.extern.slf4j.Slf4j;
@Slf4j
log.error("系统统一异常处理：", exception);
即可
2.0 如果只是获取错误异常信息 优雅 不直接抛出错误异常信息
直接【StaticLog.error("系统统一异常处理：{}", exception);】即可
```

#### 7.1.3 其他知识点

### 7.2 其他项

#### 7.2.1 从nacos获取配置信息

`测试用例在以下代码行`

```markdown
com/lc/una/consumer/ConsumerApplicationTests.java:24
```

## 8.0 项目目录

- u-na 是一个基于微服务架构的前后端分离的寄托希望梦想的内容分享系统.
- 用`nacos`作为服务发现组件
- una-gateway：网关服务（`只需启动una-provider和una-consumer即可测试nacos和网关是否成功搭建`）
- una-provider: 一个最简单的分布式服务提供者模块
- una-consumer: 一个最简单的分布式服务消费者模块
- una-admin: 提供admin端API接口服务
- una-web：提供web端API接口服务（`待做`）
- una-xo: 是存放 VO、Service，Dao层的
- una-commons：公共模块，主要用于存放Entity实体类、Feign远程调用接口、以及公共config配置
- una-base: 是一些Base基类
- una-utils: 是常用工具类
- una-docs: 是一些文档文件夹
- 其他未完待续

## 9.0 about me

```markdown
By LC
寄语:一人一世界,一树一菩提!~LC
Version 1.0.0 From 202104
```

