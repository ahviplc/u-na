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

## 5.0 访问我

`下面是此项目的一些访问链接`

`nacos` `账户密码都是nacos`

> http://localhost:8848/nacos/index.html#/login

`远程调用`

> http://localhost:8763/hi-feign

`直接调用`

> http://localhost:8762/hi?name=ahviplc

`引入网关gateway调用`

> http://localhost:5000/consumer/hi-feign

> http://localhost:5000/provider/hi?name=shviplc

`引入Sentinel作为熔断器` `账户密码都是sentinel`

> http://localhost:8748

`访问zipkin的ui界面`

> http://localhost:9411

`访问rnacos后台管理页面`

> http://localhost:10848/rnacos/p/login?redirect_url=%2Frnacos

> 默认用户名密码 admin admin

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

#为什么要定义bootstrap.yml | Nacos 官网
https://nacos.io/blog/faq/nacos-user-question-history10504/

R-NACOS | R-NACOS docs
https://r-nacos.github.io/docs/

GitHub - nacos-group/r-nacos: Nacos server re-implemented in Rust.
https://github.com/nacos-group/r-nacos
```

## 7.0 其他

### 7.1 其他

## 8.0 未完待续

> pass

