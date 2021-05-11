# 指定基础镜像，在其上进行定制 要是没有 会从docker hub上ahviplc的仓库拉取
FROM ahviplc/java-8:latest

# 维护者信息
MAINTAINER LC ahlc@sina.cn

# 这里的 /tmp 目录就会在运行时自动挂载为匿名卷，任何向 /tmp 中写入的信息都不会记录进容器存储层
VOLUME /tmp

# 创建工作目录
RUN mkdir -p /una/deploy/app

# 切换工作目录
WORKDIR /una/deploy/app

# 容器开放端口
EXPOSE 8763

# 解释说明
# ADD target/una-consumer-1.0.0.jar /app.jar
# 上面含义是复制上下文目录下的target/una-consumer-1.0.0.jar 到容器里 起别名为 app.jar
# 要是pom.xml配置了【<finalName>app</finalName>】 所以会自动打包成 app.jar
# 故要改成下面的写法 复制上下文目录下的target/app.jar 到容器里 起别名也叫为 app.jar
# 也就是 ADD target/app.jar /app.jar
# 这里
# ADD target/${project.name}-${project.version}.jar /una/deploy/app/app.jar
# 复制上下文目录下的target/una-consumer-1.0.0.jar 这里是复制到容器里的目录【/una/deploy/app/】下 起别名也叫为 app.jar
ADD target/${project.name}-${project.version}.jar /una/deploy/app/app.jar

# 指定容器启动程序及参数   <ENTRYPOINT> "<CMD>"
ENTRYPOINT ["java","-jar","/una/deploy/app/app.jar"]
