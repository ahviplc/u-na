package com.lc.una.provider.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class providerController {


	@Value("${server.port}")
	String port;
	@Value("${spring.cloud.nacos.discovery.namespace}")
	String whichActive;

	@GetMapping("/hi")
	public String hi(@RequestParam(value = "name", defaultValue = "LC", required = false) String name) {
		System.out.println("...providerController...hi...");
		return "hello " + name + ", i'm provider ,my port:" + port + " whichActive:" + whichActive;

	}
}
