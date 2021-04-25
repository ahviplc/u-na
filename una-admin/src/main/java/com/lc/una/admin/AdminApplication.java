package com.lc.una.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
// 客户端只要添加nacos的依赖，配置nacos的结点信息，启动项目已经具备服务注册发现功能了，不需要@EnableDiscoveryClient注解
@EnableDiscoveryClient

// 知识点
// 在 Spring Boot 启动类中添加 @MapperScan 注解，扫描 Mapper 文件夹
// @MapperScan("com.lc.una.xo.mapper")
// 上面【@MapperScan】注解，作用相当于【com/lc/una/commons/config/mybatis/MybatisPlusConfig.java:34】下@Bean注解的MapperScannerConfigurer
// 二者配置其一即可
// 这里使用了第二种 配置在了【com/lc/una/commons/config/mybatis/MybatisPlusConfig.java:34】
@ComponentScan(basePackages = {
		// 要扫描我【com.lc.una.admin.config.SwaggerConfig】这个配置
		"com.lc.una.admin",
		// 要扫描我【com.lc.una.commons.config.mybatis.MybatisPlusConfig】这个配置(这个配置用于来配置扫描Mapper的路径 发现所有的*.Mapper.java和*mapper.xml)哦
		// 否则报错: Field adminService in com.lc.una.admin.controller.AdminController required a bean of type 'com.lc.una.xo.mapper.AdminMapper' that could not be found.
		"com.lc.una.commons.config",
		// 下面是扫描una项目所有的VO、Service，Dao层
		"com.lc.una.xo.vo",
		"com.lc.una.xo.service"
		// 这个不需要 看line15的说明 不是这样扫描出mapper的 这里只是根据定义的扫描路径，把符合扫描规则的类装配到spring容器中
		// "com.lc.una.xo.mapper"
})
public class AdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminApplication.class, args);
	}

}
