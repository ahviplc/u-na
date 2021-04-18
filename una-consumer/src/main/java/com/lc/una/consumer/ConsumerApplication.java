package com.lc.una.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
// 客户端只要添加nacos的依赖，配置nacos的结点信息，启动项目已经具备服务注册发现功能了，不需要@EnableDiscoveryClient注解
@EnableFeignClients
public class ConsumerApplication {


	public static void main(String[] args) {
		SpringApplication.run(ConsumerApplication.class, args);
	}

}
