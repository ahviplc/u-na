package com.lc.una.admin.controller;

import cn.hutool.log.StaticLog;
import com.lc.una.utils.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 管理员表
 *
 * @author LC
 * @date 2021年4月21日16:38:22
 */
@Api(value = "una的后端admin模块的admin管理员表", tags = "una的后端admin模块的admin管理员表")
@RestController
@RequestMapping("/admin")
public class AdminController {

	@ApiOperation(value = "获取管理员列表", notes = "获取管理员列表")
	@GetMapping("/getList")
	public String getList(@RequestParam(value = "name", defaultValue = "LC", required = false) String name) {
		StaticLog.info("...getList...获取管理员列表...");
		List<String> userList = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			userList.add("user-" + i);
		}
		return ResultUtil.successWithDataAndMessage( userList, "...getList...获取管理员列表...");
	}
}
