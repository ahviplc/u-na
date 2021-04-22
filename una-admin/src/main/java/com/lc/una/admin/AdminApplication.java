package com.lc.una.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
// 客户端只要添加nacos的依赖，配置nacos的结点信息，启动项目已经具备服务注册发现功能了，不需要@EnableDiscoveryClient注解
@EnableDiscoveryClient

// 知识点
// 在 Spring Boot 启动类中添加 @MapperScan 注解，扫描 Mapper 文件夹
// @MapperScan("com.lc.una.utils.xo.mapper")
// 上面【@MapperScan】注解，作用相当于【com/lc/una/admin/config/MybatisPlusConfig.java:30】下@Bean注解的MapperScannerConfigurer
// 二者配置其一即可
// 这里使用了第二种 配置在了【com/lc/una/admin/config/MybatisPlusConfig.java:30】
public class AdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminApplication.class, args);
	}

}
