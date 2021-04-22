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

mogu_picture/src/main/resources/logback-spring.xml · 陌溪/蘑菇博客 - Gitee.com
https://gitee.com/moxi159753/mogu_blog_v2/blob/Nacos/mogu_picture/src/main/resources/logback-spring.xml

gateway-nacos-knife4j整合加访问权限控制(详细教程适合新手入门)_FREE_GIFT_白给怪的博客-CSDN博客
https://blog.csdn.net/qq_43578385/article/details/111865228

knife4j: knife4j是为Java MVC框架集成Swagger生成Api文档的工具,前身是swagger-bootstrap-ui
https://gitee.com/xiaoym/knife4j

mogu_utils · 陌溪/蘑菇博客 - 码云 - 开源中国
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

#### 7.1.2 其他知识点

### 7.2 其他项

```markdown
@ComponentScan(basePackages = {
		"com.lc.una.utils.commons",
		"com.lc.una.utils",
		"com.lc.una.admin",
		"com.lc.una.utils.xo",
		"com.lc.una.utils.xo.mapper"
})
```

## 8.0 about me

```markdown
By LC
寄语:一人一世界,一树一菩提!~LC
Version 1.0.0 From 202104
```

