# Docker之旅

[TOC]

## 1. docker进程

docker进程启动、停止、重启，常见的三种case

```bash
# 启动docker
service docker start
# 关机docker
service docker stop
# 重启docker
service docker restart
```

## 2. 镜像操作

镜像作为容器执行的前提条件，一般需要掌握的几个命令无非是搜索，下载，删除，创建

```bash
# 镜像列表
docker images
# 检索镜像, 从镜像仓库中检索
docker search xxx
# 下载镜像
docker pull xxx
# 删除镜像
docker rmi xxx
```

> docker pull ahviplc/lc-es-api:latest

关于创建镜像，有必要稍微详细一点点

```bash
# 通过容器创建镜像
docker commit -m="首次提交" -a="一灰灰Blog" dd85eb055fe8 yh/centos:v0.1
# 镜像历史查询
docker history yh/centos
```

上面的几个参数进行说明

- `-m` 和git的提交一样，后面跟上描述信息
- `-a` 版权声明，这个东西是我创建的，有啥问题，找我
- `dd85eb055fe8` 容器id
- `yhh/quick-os:0.1` 创建的镜像名

根据 Dockerfile 生成镜像 镜像名称为: lc-es-api

```
docker build -t lc-es-api .
```

## 3. 容器操作

接下来就是正菜了，容器的各种操作，启动，关闭，重启，日志查询以及各种进入容器内部搞事情

### 3.1. docker run

万事开头第一步，加载镜像，创建容器

```bash
docker run 镜像名:版本
```

run后面可以跟很多的参数，比如容器暴露端口指定，存储映射，权限等等，由于参数过多，下面只给出几个不同的例子，来具体的演示参数可以怎么加

**case1: 创建并后台执行**

```bash
docker run -i -t -d centos:latest
```

- 其中关键参数为`-d`，指定容器运行与前台或者后台，不加上时前台
- `-i`: 打开STDIN，用于控制台交互
- `-t`: 支持终端登录

**case2: 运行一个带命令在后台不断执行的容器**

```bash
docker run -d centos:latest ping www.baidu.com
```

**case3:
运行一个在后台不断执行的容器，同时带有命令，程序被终止后还能重启继续跑**

```bash
docker run -d --restart=always centos:latest ping www.baidu.com
```

**case4: 指定容器名**

```bash
docker run -d --name=yhh_centos centos:latest
```

**case5: 暴露容器端口80，并与宿主机端口8080绑定**

```bash
docker run -d --name=yhh_centos -p 8080:80 centos:latest
```

**case6: 指定容器与宿主机目录（/home/yihui/html/www）共享**

```bash
docker run -d --name=yhh_centos -v /home/yihui/html/www:/var/www centos:latest
```

### 3.2. 基操

容器创建完毕之后，就是一些基本操作了，启动、停止、重启、删除

```bash
# 查看容器列表， 列出所有的容器
docker ps -a 
# 启动容器，start后面可以跟上容器名，或者容器id
docker start xxx  # (这里的xxx可以是容器名：yhh_centos 也可以是容器id：f57398ab22c5)
# 关闭容器
docker stop xxx
# 重启容器
docker restart xxx
# 删除容器
docker rm xxx
```

在查看容器列表时，如果某个容器的启动参数特别长，直接使用`docker ps
-a`会发现看不到完整的启动命令，这个时候可以带上参数`--no-trunc`来显示完整命令

```bash
docker ps -a --no-trunc
```

### 3.3. 进阶

接下来进入一些容器的高级操作技巧（实际上也并没有特别酷炫）

为了演示一些进阶的内容，这里创建一个容器作为测试

```bash
docker run -it -d --name=yhhos centos
```

#### **3.3.1 容器日志查询**

日志，定位问题的神器

```bash
# 查询xxx容器的日志
docker logs yhhos
```

基本上不太会直接使用上面的命令，因为上面把所有的日志都打印出来了，可以直接晃瞎我们的钛合金x眼

一般日志可以加两个参数 `-f`, `-t`

```bash
docker logs -f -t --since="2019-05-11" --tail=10 yhhos
```

- `--since` : 此参数指定了输出日志开始日期，即只输出指定日期之后的日志。
- `-f` : 查看实时日志
- `-t` : 查看日志产生的日期
- `--tail=10` : 查看最后的10条日志。

#### **3.3.2 日志再次进阶**

```bash
 查看容器执行的日志
``
docker logs [OPTIONS] CONTAINER
    Options:
          --details        显示更多的信息
      -f, --follow         跟踪实时日志
          --since string   显示自某个timestamp之后的日志，或相对时间，如42m（即42分钟）
          --tail string    从日志末尾显示多少行日志， 默认是all
      -t, --timestamps     显示时间戳
          --until string   显示自某个timestamp之前的日志，或相对时间，如42m（即42分钟）
``
例子：
查看日志,跟踪实时日志，只显示最后30行 备注：不使用容器id 使用Names 名称 也是可以的：
$ docker logs -f --tail=30 CONTAINER_ID/NAMES
实际使用
$ docker logs -f --tail=30  lc-es-api-run

查看指定时间后的日志，只显示最后100行：
$ docker logs -f -t --since="2018-02-08" --tail=100 CONTAINER_ID

查看最近30分钟的日志:
$ docker logs --since 30m CONTAINER_ID

查看某时间之后的日志：
$ docker logs -t --since="2018-02-08T13:23:37" CONTAINER_ID

查看某时间段日志：
$ docker logs -t --since="2018-02-08T13:23:37" --until "2018-02-09T12:23:37" CONTAINER_ID
```

#### **3.3.3 文件拷贝**

将容器的某个文件捞出来；或者强塞，一个cp即可

```bash
从宿主机目录拷贝到容器内部
docker cp 宿主机目录 容器名称：容器目录
```

```bash
# 将当前目录的test.md文件拷贝到容器的 /tmp 目录下
docker cp test.md yhhos:/tmp

# 将容器的/tmp/test.md目录拷贝到当前目录下
docker cp yhhos:/tmp/test.md ./out.md
```

#### **3.3.4 进入容器**

进入容器内部，然后就可以为所欲为了...

```bash
docker exec -it yhhos /bin/bash
```

#### **3.3.5 获取容器所有信息**

```bash
docker inspect yhhos
```

#### **3.3.6 打tag 推送镜像 上传镜像到官方Docker Hub 上**

```bash
上传镜像到官方Docker Hub 上
网站登录
Docker Hub
https://registry.hub.docker.com/

Docker Hub
https://registry.hub.docker.com/repositories

在Repositories下 创建如下仓库
ahviplc / lc-es-api

得到
Docker Hub
https://registry.hub.docker.com/repository/docker/ahviplc/lc-es-api

ahviplc/lc-es-api Tags
https://registry.hub.docker.com/r/ahviplc/lc-es-api/tags?page=1&ordering=last_updated

再命令行登录
docker login

按照提示输入用户名和密码，登录成功.
会在 win docker下的win机器 如下目录
C:\Users\theDiyPCOfLC\.docker
下生成 config.json 文件
``
{
	"auths": {
		"https://index.docker.io/v1/": {}
	},
	"HttpHeaders": {
		"User-Agent": "Docker-Client/19.03.8 (windows)"
	},
	"credsStore": "desktop",
	"stackOrchestrator": "swarm"
}
``

先打个tag:
将镜像打了一个标签，相当于重命名一样，让名称尽可能规范
docker tag lc-es-api ahviplc/lc-es-api:latest

删除此tag(和删除一个镜像是同一个命令):
docker rmi ahviplc/lc-es-api:latest
删除此tag 对应的镜像
docker rmi lc-es-api

查看所有镜像
``
docker images
REPOSITORY                 TAG                 IMAGE ID            CREATED             SIZE
ahviplc/lc-es-api          latest              bcf88f025394        2 hours ago         733MB
lc-es-api                  latest              bcf88f025394        2 hours ago         733MB
mysql                      latest              30f937e841c8        10 months ago       541MB
nginx                      latest              6678c7c2e56c        13 months ago       127MB
hello-world                latest              bf756fb1ae65        15 months ago       13.3kB
java                       8                   d23bdf5b1b1b        4 years ago         643MB
``

将镜像push上传到官方Docker Hub 上
docker push ahviplc/lc-es-api
或
docker push ahviplc/lc-es-api:latest

进度条走完即可.

请拉取尝鲜吧
docker pull ahviplc/lc-es-api:latest

以此镜像 执行容器
docker run --name lc-es-api-run -d -i -p 8088:8088 docker.io/ahviplc/lc-es-api:latest

访问我.
公网的哦.
http://106.14.212.65:8088/

http://106.14.212.65:8088/userlist

http://oneplusone.vip:8088/

http://oneplusone.vip:8088/userlist

其es的服务没有启动-故下面链接功能不全.
http://oneplusone.vip:8088/goodslist

此版本
docker pull ahviplc/lc-es-api:latest
可正常访问使用的.

Done.
```

#### **3.3.7 目录挂载**

```bash
目录挂载(必须在容器创建的时候进行挂载)
docker run -di --name=容器名称 -v 宿主机目录：容器目录 镜像IMAGE(lc-es-api)/镜像名称REPOSITORY(lc-es-api)/镜像id对应IMAGE ID(bcf88f025394)

实战 - 创建守护容器 目录挂载 -v 端口映射 -p 容器名称 --name
在Win docker下:
docker run -di -v D:\DevelopSoftKu\DockerDirMount\lc-es-api-dir:/usr/local/lc-es-api-dir -p 8088:8088 --name lc-es-api-run-dir lc-es-api
备注:【D:\DevelopSoftKu\DockerDirMount\lc-es-api-dir】=>【/host_mnt/d/DevelopSoftKu/DockerDirMount/lc-es-api-dir】

在Linux docker下:
docker run -di -v /home/lc-es-api-dir:/usr/local/lc-es-api-dir -p 8088:8088 --name lc-es-api-run-dir lc-es-api

进入容器查看
docker exec -it 44a9e27570bf /bin/bash
进行ls列出和cd切换到对应目录查看,成功.
``
root@44a9e27570bf:/usr/local/lc-es-api-dir# pwd
/usr/local/lc-es-api-dir
root@44a9e27570bf:/usr/local/lc-es-api-dir# cat cat.txt
看我...
``
```

#### 3.3.8 从启动的容器创建新的镜像：docker commit

> docker commit ：从容器创建一个新的镜像

`参数说明`

```markdown
-a : 提交的镜像作者
-c : 使用Dockerfile指令创建镜像
-m : 提交时的说明文字
-p : 在commit时 将容器暂停
再后面就是要生成对应镜像的容器id和镜像名+版本号
```

##### 3.3.8.1 redis

```bash
docker commit -a="LC ahlc@sina.cn" -m="redis image by LC" 86552cf2253f redis-docker-lc:1.0

`打标签tag后push镜像`
docker tag redis-docker-lc:1.0 ahviplc/redis-docker-lc:1.0

docker push ahviplc/redis-docker-lc:1.0
```

##### 3.3.8.2 nacos

```bash
docker commit -a="LC ahlc@sina.cn" -m="nacos image by LC" 0ccbef25dbba nacos-docker-lc:1.0

`打标签tag后push镜像`
docker tag nacos-docker-lc:1.0 ahviplc/nacos-docker-lc:1.0

docker push ahviplc/nacos-docker-lc:1.0
```

##### 3.3.8.3 mysql

```bash
docker commit -a="LC ahlc@sina.cn" -m="mysql image by LC" 37fe23c37aab mysql-docker-lc:1.0

`打标签tag后push镜像`
docker tag mysql-docker-lc:1.0 ahviplc/mysql-docker-lc:1.0

docker push ahviplc/mysql-docker-lc:1.0
```

##### 3.3.8.4 zipkin

```bash
docker commit -a="LC ahlc@sina.cn" -m="zipkin image by LC" 438af27e654b zipkin-docker-lc:1.0

`打标签tag后push镜像`
docker tag zipkin-docker-lc:1.0 ahviplc/zipkin-docker-lc:1.0

docker push ahviplc/zipkin-docker-lc:1.0
```

##### 3.3.8.5 sentinel

```bash
docker commit -a="LC ahlc@sina.cn" -m="sentinel image by LC" 5e6906075d75 sentinel-docker-lc:1.0

`打标签tag后push镜像`
docker tag sentinel-docker-lc:1.0 ahviplc/sentinel-docker-lc:1.0

docker push ahviplc/sentinel-docker-lc:1.0
```

## 4. 实战

### 4.1 安装nacos

#### 4.1.1 常规

##### 4.1.1.1 拉取镜像

> docker pull nacos/nacos-server:latest

##### 4.1.1.2 创建目录

> mkdir -p home/dockerdata/nacos/logs

##### 4.1.1.3 启动

```bash
docker run -d \
-e PREFER_HOST_MODE=ip \
-e MODE=standalone \
-e SPRING_DATASOURCE_PLATFORM=mysql \
-e MYSQL_SERVICE_HOST=localhost \
-e MYSQL_SERVICE_PORT=3306 \
-e MYSQL_SERVICE_USER=root \
-e MYSQL_SERVICE_PASSWORD=ljroot \
-e MYSQL_SERVICE_DB_NAME=nacos_config \
-e TIME_ZONE='Asia/Shanghai' \
-v /home/dockerdata/nacos/logs:/home/nacos/logs \
-p 8848:8848 \
--name nacos \
--restart=always \
nacos/nacos-server:latest

`linux 实际执行 命令`
docker run -d \
-e PREFER_HOST_MODE=ip \
-e MODE=standalone \
-e SPRING_DATASOURCE_PLATFORM=mysql \
-e MYSQL_SERVICE_HOST=172.0.0.1 \
-e MYSQL_SERVICE_PORT=3306 \
-e MYSQL_SERVICE_USER=root \
-e MYSQL_SERVICE_PASSWORD=ljroot \
-e MYSQL_SERVICE_DB_NAME=nacos_config \
-e TIME_ZONE='Asia/Shanghai' \
-v $PWD/nacos/logs:/home/nacos/logs \
-p 8848:8848 \
--name nacos \
--restart=always \
nacos/nacos-server:latest
```

#### 4.1.2 使用docker-compose

> https://github.com/nacos-group/nacos-docker/blob/master/README_ZH.md

##### 4.1.2.1 快速开始

打开命令窗口执行：

- Clone project

  ```
  git clone --depth 1 https://github.com/nacos-group/nacos-docker.git
  cd nacos-docker
  ```

- Standalone Derby

  ```
  docker-compose -f example/standalone-derby.yaml up
  ```

- Standalone Mysql

  ```
  # Using mysql 5.7
  docker-compose -f example/standalone-mysql-5.7.yaml up

  # Using mysql 8
  docker-compose -f example/standalone-mysql-8.yaml up
  ```

- 集群模式

  ```
  docker-compose -f example/cluster-hostname.yaml up 
  ```

- 服务注册示例

  ```
  curl -X PUT 'http://127.0.0.1:8848/nacos/v1/ns/instance?serviceName=nacos.naming.serviceName&ip=20.18.7.10&port=8080'
  ```

- 服务发现示例

  ```
  curl -X GET 'http://127.0.0.1:8848/nacos/v1/ns/instances?serviceName=nacos.naming.serviceName'
  ```

- 推送配置示例

  ```
  curl -X POST "http://127.0.0.1:8848/nacos/v1/cs/configs?dataId=nacos.cfg.dataId&group=test&content=helloWorld"
  ```

- 获取配置示例

  ```
    curl -X GET "http://127.0.0.1:8848/nacos/v1/cs/configs?dataId=nacos.cfg.dataId&group=test"
  ```

- 打开浏览器

  link：http://127.0.0.1:8848/nacos/

##### 4.1.2.2 其他使用方式

打开命令窗口执行：

- Clone 项目 并且进入项目根目录

  ```
  git clone https://github.com/paderlol/nacos-docker.git
  cd nacos-docker
  ```

- 单机

  ```
  docker-compose -f standalone.yaml up
  ```

- 集群

  ```
  docker-compose -f cluster.yaml up 
  ```

- 注册服务

  ```
  curl -X PUT 'http://127.0.0.1:8848/nacos/v1/ns/instance?serviceName=nacos.naming.serviceName&ip=20.18.7.10&port=8080'
  ```

- 注册配置

  ```
  curl -X POST "http://127.0.0.1:8848/nacos/v1/cs/configs?dataId=nacos.cfg.dataId&group=test&content=helloWorld"
  ```

- 访问控制台

  浏览器访问：http://127.0.0.1:8848/nacos/

### 4.2 安装redis

#### 4.2.1 拉取镜像

```bash
docker pull redis
```

#### 4.2.2 启动镜像

```bash
docker run --name redis-docker -p 6379:6379 -v $PWD/data:/data  -d redis:latest redis-server --appendonly yes

命令说明：
-p 6379:6379 : 将容器的6379端口映射到主机的6379端口
-v $PWD/data:/data : 将主机中当前目录下的data挂载到容器的/data
redis-server --appendonly yes : 在容器执行redis-server启动命令，并打开redis持久化配置
```

#### 4.2.3 启动镜像-映射配置文件

**创建文件目录和数据目录**

```bash
mkdir -p /root/docker/redis/conf
在该目录下放置redis.conf配置文件
注：目录可以自定义

mkdir -p /root/docker/redis/data
在该目录放data
```

**获取配置文件redis.conf**，从[官网下载](http://download.redis.io/redis-stable/redis.conf)

**挂载启动命令**

`挂载启动命令写法一`

`映射配置文件`

```bash
docker run --name redis-docker -d -p 6379:6379 --privileged=true --restart always -v /root/docker/redis/conf/redis.conf:/etc/redis/redis.conf -v /root/docker/redis/data:/data redis redis-server /etc/redis/redis.conf --appendonly yes

`win和Linux实际使用`
docker run --name redis-docker -d -p 6379:6379 --privileged=true --restart always -v $PWD/conf/redis.conf:/etc/redis/redis.conf -v $PWD/data:/data redis redis-server /etc/redis/redis.conf --appendonly yes

参数说明:
-d                                                  -> 以守护进程的方式启动容器
-p 6379:6379                                        -> 绑定宿主机端口
--name redis-docker                                 -> 指定容器名称
--restart always                                    -> 开机启动
--privileged=true                                   -> 提升容器内权限
-v /root/docker/redis/conf:/etc/redis/redis.conf    -> 映射配置文件
-v /root/docker/redis/data:/data                    -> 映射数据目录
-v $PWD/data:/data                                  -> 将主机中当前目录下的data挂载到容器的/data
--appendonly yes                                    -> 开启数据持久化
```

`挂载启动命令写法二`

```bash
docker run \
-p 6379:6379 \ # 端口映射 宿主机:容器
-v /usr/local/src/redis/data:/data:rw \ # 映射数据目录 rw 为读写
-v /usr/local/src/redis/conf/redis.conf:/etc/redis/redis.conf:ro \ # 挂载配置文件 ro 为readonly
--privileged=true \ # 给与一些权限
--name redis-docker \ # 容器名称
-d redis:latest  redis-server /etc/redis/redis.conf \ # deamon 运行 服务使用指定的配置文件
--appendonly yes
```

#### 4.2.4 连接redis的几种方式

```bash
docker exec -ti d0b86 redis-cli

docker exec -ti d0b86 redis-cli -h localhost -p 6379 
docker exec -ti d0b86 redis-cli -h 127.0.0.1 -p 6379 
docker exec -ti d0b86 redis-cli -h 172.17.0.3 -p 6379 

// 注意，这个是容器运行的ip，可通过 docker inspect redis_s | grep IPAddress 查看
```

#### 4.2.5 查看容器的ip

```bash
docker inspect redis_s | grep IPAddress
            "SecondaryIPAddresses": null,
            "IPAddress": "172.17.0.3",
                    "IPAddress": "172.17.0.3",
```

#### 4.2.6 使用redis镜像执行redis-cli命令连接到刚启动的容器

主机IP为172.17.0.1

```bash
docker exec -it redis_s redis-cli

127.0.0.1:6379> 
```

如果连接远程：

```bash
docker exec -it redis_s redis-cli -h 192.168.1.100 -p 6379 -a your_password //如果有密码 使用 -a参数

192.168.1.100:6379> 
```

### 4.3 安装zipkin

#### 4.3.1 拉取镜像

> docker pull openzipkin/zipkin:latest

#### 4.3.2 启动

> docker run --name zipkin -d -p 9411:9411 openzipkin/zipkin

> docker run --name zipkin -d -p 9411:9411 ahviplc/zipkin-docker-lc:1.0

#### 4.3.3 运行

> 浏览器打开 http://Linux的IP:9411

### 4.4 安装sentinel

#### 4.4.1 拉取镜像

> docker pull bladex/sentinel-dashboard

#### 4.4.2 运行镜像

```bash
docker run --name sentinel -d -p 8858:8858 -d bladex/sentinel-dashboard
```

#### 4.4.3 访问DASHBOARD

访问地址：http://ip:8858/#/login 账户密码：sentinel/sentinel

### 4.5 安装MySQL

#### 4.5.1 拉取镜像

```bash
docker pull mysql:latest
```

#### 4.5.2 运行容器

安装完成后，我们可以使用以下命令来运行MySQL容器：

```bash
$ docker run -itd --name mysql-docker -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root mysql

参数说明：

-p 3306:3306 ：映射容器服务的 3306 端口到宿主机的 3306 端口，外部主机可以直接通过 宿主机ip:3306 访问到 MySQL 的服务。
MYSQL_ROOT_PASSWORD=123456 ：设置 MySQL 服务 root 用户的密码。
-v $PWD/data:/var/lib/mysql : 将主机中当前目录下的data挂载到容器的/var/lib/mysql
```

#### 4.5.3 运行容器-映射配置文件

```bash
带挂载的运行命令写法一
docker run --name mysql-docker -v $PWD/conf:/etc/mysql/conf.d -v $PWD/logs:/logs -v $PWD/data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=root -d -t -i -p 3306:3306 mysql:latest

带挂载的运行命令写法二
直接执行 建个测试的容器 找出文件的具体路径位置,之后删除此容器,再重新挂载创建
docker run --name mysql-docker -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -d mysql

此命令用来查找Docker内，MySQL配置文件my.cnf的位置
mysql --help | grep my.cnf

接下来，我们需要在宿主机上，创建一个数据和配置文件的挂载路径
创建文件路径
mkdir -p /usr/local/src/mysql/conf && mkdir -p /usr/local/src/mysql/data

创建好宿主机的挂载数据路径后，我们将测试容器里 MySQL 的配置文件复制到该路径。后面要是修改配置直接修改宿主机的配置文件即可
docker cp mysql:/etc/mysql/my.cnf /usr/local/src/mysql/conf

启动前需要将之前的安装测试的删除掉
docker rm -f mysql-docker

挂载启动
docker run \
--name mysql-docker \
-p 3306:3306 \
-e MYSQL_ROOT_PASSWORD=root \
-v /usr/local/src/mysql/conf/my.cnf:/etc/mysql/my.cnf \
-v /usr/local/src/mysql/data:/var/lib/mysql \
--restart=on-failure:3 \
-d mysql

`linux 实际执行 挂载启动`
docker run \
--name mysql-docker \
-p 3306:3306 \
-e MYSQL_ROOT_PASSWORD=root \
-v /docker-data/mysql/conf/my.cnf:/etc/mysql/my.cnf \
-v /docker-data/mysql/data:/var/lib/mysql \
--restart=on-failure:3 \
-d mysql:5.7.34

`win实际执行 挂载启动`
docker run \
--name mysql-docker \
-p 3306:3306 \
-e MYSQL_ROOT_PASSWORD=root \
-v $PWD/mysql:/etc/mysql \    # 里面有my.cnf
-v $PWD/mysql-files:/var/lib/mysql-files \
-v $PWD/data:/var/lib/mysql \
--restart=on-failure:3 \
-d mysql

`实际执行 win docker 执行版命令 挂载启动`
docker run --name mysql-docker -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -v $PWD/mysql:/etc/mysql -v $PWD/data:/var/lib/mysql -v $PWD/mysql-files:/var/lib/mysql-files --restart=on-failure:3 -d mysql
```

#### 4.5.4 进入容器

`通过 root 和密码 123456 访问 MySQL 服务`

```bash
docker exec -it mysql-docker bash
docker exec -it mysql-docker /bin/bash
```

```shell
mysql -uroot -p
mysql -h localhost -u root -p
输入密码root即可

开启远程访问权限
命令：use mysql;

命令：select host,user from user;

命令：ALTER USER 'root'@'%' IDENTIFIED WITH mysql_native_password BY 'root';

命令：flush privileges;
```

### 4.6 安装transmission

#### 4.6.1 拉取镜像

> docker pull gists/transmission:latest

#### 4.6.2 启动

```bash
docker run \
    -d \
    --name transmission \
    -p 9091:9091 \
    -p 51413:51413 \
    -v /docker-data/transmission/data:/data \
    -e USERNAME=admin \
    -e PASSWORD=admin \
    gists/transmission
```

#### 4.6.3 运行

Transmission WEB 控制器 1.6.1
http://192.168.0.4:9091/transmission/web/

### 4.7 安装aria2-pro

#### 4.7.1 拉取镜像

> docker pull p3terx/aria2-pro:latest

#### 4.7.2 启动

```bash
docker run -d \
    --name aria2-pro \
    --restart unless-stopped \
    --network host \
    -e PUID=$UID \
    -e PGID=$GID \
    -e RPC_SECRET=12345 \
    -e RPC_PORT=6800 \
    -e LISTEN_PORT=6888 \
    -v /docker-data/aria2/aria2-config:/config \
    -v /docker-data/aria2/downloads:/downloads \
    p3terx/aria2-pro
```

#### 4.7.3 运行

> 浏览器插件或者其他aria2客户端使用即可

### 4.8 安装h5ai

#### 4.8.1 拉取镜像

> docker pull ilemonrain/h5ai:full

#### 4.8.2 启动

```bash
docker run -d -p 5555:80 -v /docker-data/h5ai:/h5ai --name h5ai ilemonrain/h5ai:full
```

#### 4.8.3 运行

index - powered by h5ai v0.29.0 (https://larsjung.de/h5ai/)
http://192.168.0.4:5555/test/

### 4.9 其他说明

Linux下docker数据目录结构 `中间有删减`

```shell
root@lc-virtual-machine:/docker-data# tree
.
├── mysql
│   ├── conf
│   │   └── my.cnf
│   └── data
│       ├── auto.cnf
│       ├── ca-key.pem
│       ├── ib_logfile1
│       ├── mysql
│       │   ├── columns_priv.frm
│       │   ├── func.frm
│       │   └── user.MYI
│       ├── performance_schema
│       │   ├── accounts.fr
│       │   ├── user_variables_by_thread.frm
│       │   └── variables_by_thread.frm
│       ├── private_key.pem
│       ├── server-key.pem
│       └── sys
│           ├── db.opt
│           ├── x@0024waits_by_user_by_latency.frm
│           └── x@0024waits_global_by_latency.frm
├── nacos
│   └── logs
│       ├── alipay-jraft.log
│       ├── cmdb-main.log
│       ├── config-client-request.log
│       └── tps-control.log
└── redis
    ├── conf
    │   └── redis.conf
    └── data
        ├── appendonly.aof
        └── dump.rdb

11 directories, 319 files
root@lc-virtual-machine:/docker-data# pwd
/docker-data
root@lc-virtual-machine:/docker-data# 
```

## 5. 扩展

`ahviplc's Profile Docker Hub`

> https://hub.docker.com/u/ahviplc

```
Docker 常用命令速查手册 - 一灰灰Blog - 博客园
https://www.cnblogs.com/yihuihui/p/12003244.html

安装nacos
https://github.com/nacos-group/nacos-docker/blob/master/README_ZH.md

Docker安装运行Redis - 静静别跑 - 博客园
https://www.cnblogs.com/zhzhlong/p/9465670.html

Docker安装redis，挂载外部配置和数据 - junecoo - 博客园
https://www.cnblogs.com/tianhuiht/p/11130455.html

Docker 入门到实战教程(七)安装Redis - 详细
https://mp.weixin.qq.com/s?__biz=MzU0MDczNzA1NQ%3D%3D&chksm=fb35e854cc4261422533b7eae1b947df8be36769b37a21b80307f33989210ec28c6a66c79707&idx=1&mid=2247484196&scene=21&sn=fde06a3dee99e314d3d2b1f43b386e29#wechat_redirect

使用docker安装zipkin - 慕尘 - 博客园
https://www.cnblogs.com/baby123/p/12803135.html

Sentinel系列之-Docker安装Sentinel - 灰信网（软件开发博客聚合）
https://www.freesion.com/article/19391435792/

Docker 安装 MySQL | 菜鸟教程
https://www.runoob.com/docker/docker-install-mysql.html

安装docker并使用docker安装mysql - 含有挂载
https://www.cnblogs.com/jiefu/p/12204555.html

MySQL 连接出现 Authentication plugin 'caching_sha2_password' cannot be loaded - 漠里 - 博客园
https://www.cnblogs.com/zhurong/p/9898675.html

docker mysql mysqld: Error on realpath() on '/var/lib/mysql-files' No such file or directory_巷中人的博客-CSDN博客
https://blog.csdn.net/weixin_30338497/article/details/96153193

Docker 入门到实战教程(八)安装Mysql - 详细
https://mp.weixin.qq.com/s?__biz=MzU0MDczNzA1NQ%3D%3D&chksm=fb35e858cc42614e046beb7a7394ff02d0a8aabaf03a98af3c5cc535b0e45db78a5076a46acb&idx=1&mid=2247484200&scene=21&sn=13b9d8d07b660a64b2fc290855a190bb#wechat_redirect

Docker 入门到实战教程(九)安装Nginx - 详细
https://mp.weixin.qq.com/s?__biz=MzU0MDczNzA1NQ%3D%3D&chksm=fb35e85ecc4261485352a7bc1e98155aabb0b9dcac768fb8879a2c8985cf68304599e5990dd3&idx=1&mid=2247484206&scene=21&sn=4939502b0793fed4dc342204537baa05#wechat_redirect

Docker 入门到实战教程(二)安装Docker
https://mp.weixin.qq.com/s?__biz=MzU0MDczNzA1NQ==&mid=2247484172&idx=1&sn=a779dac68c51c7ccece9da3996cf642f&chksm=fb35e87ccc42616a5a1a7f9ceb486e3ced2a16ed9563b19eb203827654b6a615ea1b48026dfb&scene=21#wechat_redirect

Docker 入门到实战教程(三)镜像和容器
https://mp.weixin.qq.com/s?__biz=MzU0MDczNzA1NQ==&mid=2247484177&idx=1&sn=4c4b71a7ef2a64197696b5d7198638c9&chksm=fb35e861cc426177e1ad2aceb6bac76f358e7da4104facd1720a88bb2b29cecabf66184bdf0c&scene=21#wechat_redirect

Docker 入门到实战教程(四)容器链接
https://mp.weixin.qq.com/s?__biz=MzU0MDczNzA1NQ==&mid=2247484182&idx=1&sn=80736f9222a2123ba3a43251359172bf&chksm=fb35e866cc426170927fadec3d13d6bb4ab69c5461c761359332bf623e94d3e226b4950d60fa&scene=21#wechat_redirect

Docker 入门到实战教程(五)构建Docker镜像
https://mp.weixin.qq.com/s?__biz=MzU0MDczNzA1NQ==&mid=2247484187&idx=1&sn=f8ab491a1b51735ffc2bb65697bac7cb&chksm=fb35e86bcc42617df510f070662ef61e65a45d8b8402ee922ec0fa05b42c93a1724af18cf5e5&scene=21#wechat_redirect

Docker 入门到实战教程(六)Docker数据卷
https://mp.weixin.qq.com/s?__biz=MzU0MDczNzA1NQ==&mid=2247484192&idx=1&sn=498d2c893d9650cc7112a6becbacf291&chksm=fb35e850cc426146c8f2898454d2511ed2727453cd9e7f5a94d23b90f1e3d9eec7139a89f659&scene=21#wechat_redirect

Docker Hub
https://registry.hub.docker.com/

Docker Hub
https://registry.hub.docker.com/repositories

Docker 入门到实战教程(十三)Docker Compose_李浩东的博客-CSDN博客
https://blog.csdn.net/qq_34936541/article/details/105463105

Docker教程(九)部署Spring Boot项目
https://mp.weixin.qq.com/s?__biz=MzU0MDczNzA1NQ%3D%3D&chksm=fb35e842cc42615445cf7feb79ad35422cdf533e196ebbbe8b6a2df492256ae1739ff70a6c61&idx=1&mid=2247484210&scene=21&sn=24dfdfea4dda95090b2b27fb5446f5de#wechat_redirect

Hub · DaoCloud
https://hub.daocloud.io/

DaoCloud | Docker 极速下载
http://get.daocloud.io/#install-docker

DaoCloud | Docker 极速下载
http://get.daocloud.io/#install-docker-for-mac-windows

配置 Docker 镜像站
https://www.daocloud.io/mirror

Redis 命令参考 — Redis 命令参考
http://redisdoc.com/index.html

Redis 教程_redis教程
https://www.redis.net.cn/tutorial/3501.html

Redis 命令参考 — Redis 命令参考
http://doc.redisfans.com/index.html

配置 Docker 镜像加速源地址_weixin_44953227的博客-CSDN博客_docker配置多个镜像源
https://blog.csdn.net/weixin_44953227/article/details/109242166

ssh root用户无法登录,密码是对的,解决办法(转) - sunzebo - 博客园
https://www.cnblogs.com/sunzebo/articles/9609457.html

Ubuntu怎么开启/关闭防火墙_不起眼的小孩-CSDN博客_ubuntu 关闭防火墙
https://blog.csdn.net/smileui/article/details/87909393

UBUNTU的默认root密码是多少，修改root密码 - 编程之家
https://www.jb51.cc/ubuntu/355867.html

docker 开启2375端口，提供外部访问docker,idea连接服务器docker_霓虹深处-CSDN博客_idea连接docker2376端口
https://blog.csdn.net/qq_36850813/article/details/89924207

ubuntu开启SSH服务远程登录_jackghq的博客-CSDN博客
https://blog.csdn.net/jackghq/article/details/54974141

Ubuntu 非root用户使用Docker命令提示：connect: permission denied_wick_hp的博客-CSDN博客
https://blog.csdn.net/qq_36488647/article/details/104356685

Docker通过容器生成镜像(通过容器提交（docker commit）成镜像)_捕影世界-CSDN博客_docker 容器生成镜像
https://blog.csdn.net/qmw19910301/article/details/88070159
```

