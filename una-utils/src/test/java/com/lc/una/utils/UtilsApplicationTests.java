package com.lc.una.utils;

import cn.hutool.core.lang.Console;
import com.lc.una.utils.tools.DateUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UtilsApplicationTests {

	@Test
	void contextLoads() {
		Console.log("...test...");
	}

	@Test
	void test() {
		Console.log(DateUtils.getNowDate());
		Console.log(DateUtils.formateDate(DateUtils.getNowDate(), DateUtils.YYYY_MM_DD_HH_MM_SS));
		Console.log(DateUtils.formateDate(DateUtils.getServerStartDate(), DateUtils.YYYY_MM_DD_HH_MM_SS));
	}
}
