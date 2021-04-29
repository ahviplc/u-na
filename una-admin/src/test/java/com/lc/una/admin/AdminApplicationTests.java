package com.lc.una.admin;

import cn.hutool.core.lang.Console;
import com.lc.una.utils.tools.RedisUtil;
import com.lc.una.utils.tools.RedisUtils2U;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ScanOptions;

import java.util.Map;

@SpringBootTest
class AdminApplicationTests {

	@Autowired
	RedisUtil redisUtil;

	@Test
	void contextLoads() {
	}

	@Test
	void TestRedisUtil() {
	    // set添加元素
		redisUtil.sAdd("una-test", "una-from-lc", "una-from-lc2");
		// 设置指定 key 的值
		redisUtil.set("una-set", "just-set");
	}

	/**
	 * 更多测试在:
	 * https://github.com/JustryDeng/CommonRepository/tree/master/Abc_RedisUtil_Demo/src/test/java/com/pingan/redisutil
	 */
	@Test
	void TestRedisUtils2U() {
		// hPut(...) => key -> ds, entryKey -> name, entryValue -> 邓沙利文
		RedisUtils2U.HashOps.hPut("ds", "name", "邓沙利文");
		// hGetAll(...) => key -> ds, result -> {name=邓沙利文}
		RedisUtils2U.HashOps.hGetAll("ds");

		// hPut(...) => key -> ds, entryKey -> name, entryValue -> 邓二洋
		RedisUtils2U.HashOps.hPut("ds", "name", "邓二洋");
		// hGetAll(...) => key -> ds, result -> {name=邓二洋}
		Map<Object, Object> dsMap = RedisUtils2U.HashOps.hGetAll("ds");
		Console.log(dsMap);

		/// prepare data
		RedisUtils2U.HashOps.hPut("ds", "ne", "v0");
		RedisUtils2U.HashOps.hPut("ds", "name", "v1");
		RedisUtils2U.HashOps.hPut("ds", "name123", "v2");
		RedisUtils2U.HashOps.hPut("ds", "name456", "v3");
		RedisUtils2U.HashOps.hPut("ds", "nameAbc", "v4");
		RedisUtils2U.HashOps.hPut("ds", "nameXyz", "v5");
		/// test
		// hScan(...) => key -> ds, options -> {}, cursor -> [{"ne":"v0"},{"name":"v1"},{"name123":"v2"},{"name456":"v3"},{"nameAbc":"v4"},{"nameXyz":"v5"}]
		RedisUtils2U.HashOps.hScan("ds", ScanOptions.NONE);
		// hScan(...) => key -> ds, options -> {"pattern":"name*"}, cursor -> [{"name":"v1"},{"name123":"v2"},{"name456":"v3"},{"nameAbc":"v4"},{"nameXyz":"v5"}]
		RedisUtils2U.HashOps.hScan("ds", ScanOptions.scanOptions().match("name*").build());
		// hScan(...) => key -> ds, options -> {"pattern":"*a*"}, cursor -> [{"name":"v1"},{"name123":"v2"},{"name456":"v3"},{"nameAbc":"v4"},{"nameXyz":"v5"}]
		RedisUtils2U.HashOps.hScan("ds", ScanOptions.scanOptions().match("*a*").build());
		// hScan(...) => key -> ds, options -> {"pattern":"n??e"}, cursor -> [{"name":"v1"}]
		RedisUtils2U.HashOps.hScan("ds", ScanOptions.scanOptions().match("n??e").build());
	}
}
