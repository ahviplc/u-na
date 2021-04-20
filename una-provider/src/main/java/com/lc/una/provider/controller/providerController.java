package com.lc.una.provider.controller;

import cn.hutool.log.StaticLog;
import com.lc.una.utils.DateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "简单服务提供者模块", tags = "简单服务提供者模块")
@RestController
public class providerController {

	@Value("${server.port}")
	String port;
	@Value("${spring.cloud.nacos.discovery.namespace}")
	String whichActive;

	@ApiOperation(value = "hi", notes = "hi")
	@GetMapping("/hi")
	public String hi(@RequestParam(value = "name", defaultValue = "LC", required = false) String name) {
		System.out.println("...providerController...hi...");
		// 测试输出log日志 info 和 error
		StaticLog.info("...providerController...hi...");
		StaticLog.error("...providerController...hi...{}...", "假装error了");
		return "hello " + name + ", i'm provider ,my port:" + port + " whichActive:" + whichActive;
	}

	@ApiOperation(value = "nowTime", notes = "nowTime-当前时间")
	@GetMapping("/nowTime")
	public String nowTime() {
		return DateUtils.formateDate(DateUtils.getNowDate(), DateUtils.YYYY_MM_DD_HH_MM_SS);
	}
}
