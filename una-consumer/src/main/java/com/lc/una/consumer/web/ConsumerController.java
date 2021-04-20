package com.lc.una.consumer.web;

import cn.hutool.log.StaticLog;
import com.lc.una.consumer.client.ProviderClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "简单服务消费者模块", tags = "简单服务消费者模块")
@RestController
public class ConsumerController {

	@Autowired
	ProviderClient providerClient;

	@ApiOperation(value = "hi-feign", notes = "hi-feign")
	@GetMapping("/hi-feign")
	public String hiFeign() {
		System.out.println("...ConsumerController...hi-feign...");
		// 测试输出log日志 info 和 error
		StaticLog.info("...ConsumerController...hi-feign...");
		StaticLog.error("...ConsumerController...hi-feign...{}...", "假装error了");
		return providerClient.hi("feign");
	}
}
