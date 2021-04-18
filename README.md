# u-na
> u-na:一个基于微服务架构的前后端分离的寄托希望梦想的内容分享系统.

## Star and Fork
`u-na: u-na:一个基于微服务架构的前后端分离的寄托希望梦想的内容分享系统.`
> https://gitee.com/ahviplc/u-na

`ahviplc/u-na: u-na:一个基于微服务架构的前后端分离的寄托希望梦想的内容分享系统.`
> https://github.com/ahviplc/u-na

## 搭建环境

### aliyun

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

其他端口
pass
```

### docker

```markdown
Install Docker Compose | Docker Documentation
https://docs.docker.com/compose/install/

Nacos 快速开始
https://nacos.io/zh-cn/docs/quick-start.html

Releases · alibaba/nacos · GitHub
https://github.com/alibaba/nacos/releases

Nacos Docker 快速开始
https://nacos.io/zh-cn/docs/quick-start-docker.html
```

#### 安装Docker Compose

> https://docs.docker.com/compose/install/

```sh
1. sudo curl -L "https://github.com/docker/compose/releases/download/1.29.1/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose

2. sudo chmod +x /usr/local/bin/docker-compose
3. Test the installation.
docker-compose --version
```

#### 使用docker部署nacos

> 使用docker部署nacos

```markdown
docker安装nacos - zhangdaiscott - 博客园
https://www.cnblogs.com/jeecg158/p/14029453.html

docker安装nacos_青岚飞雪博客-CSDN博客
https://blog.csdn.net/qq_36215276/article/details/111922453
```

#### 具体安装nacos
> 具体安装nacos
>
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

## 开始

> 代码...

## 搭建项目

```markdown
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
```

## 访问我
`nacos` `账户密码都是nacos`
> http://localhost:8848/nacos/index.html#/login

`远程调用`
> http://localhost:8763/hi-feign

`直接调用`
> http://localhost:8762/hi?name=ahviplc


## 其他

### 其他

## 未完待续

> pass
