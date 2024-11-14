package com.lc.una.consumer.web;

import cn.hutool.log.StaticLog;
import com.lc.una.consumer.client.ProviderClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope // 在控制器类中加入该注解使当前类下的配置支持Nacos的动态刷新功能
public class ConsumerController {

	@Autowired
	ProviderClient providerClient;

	@GetMapping("/hi-feign")
	public String hiFeign() {
		System.out.println("...ConsumerController...hi-feign...");
		// 测试输出log日志 info 和 error
		StaticLog.info("...ConsumerController...hi-feign...");
		StaticLog.error("...ConsumerController...hi-feign...{}...", "假装error了");
		return providerClient.hi("feign");
	}
}
