package com.lc.una.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.log.StaticLog;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequestMapping("/utils")
public class UtilsApplication {

	public static String msg = StrUtil.format("我是una-utils:常用工具类, 启动我干嘛? 非要看我, 我的端口{}, full url:{}", 114, "http://localhost:114/utils/ut");

	public static void main(String[] args) {
		SpringApplication.run(UtilsApplication.class, args);
		StaticLog.info(msg);
	}

	@GetMapping("/ut")
	public String ut() {
		return msg;
	}
}
