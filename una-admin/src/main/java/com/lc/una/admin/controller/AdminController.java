package com.lc.una.admin.controller;

import cn.hutool.log.StaticLog;
import com.lc.una.admin.service.AdminService;
import com.lc.una.utils.tools.ResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 管理员表 控制层 AdminController
 *
 * @author LC
 * @date 2021年4月21日16:38:22
 */
@Api(value = "una的后端admin模块的admin管理员表", tags = "una的后端admin模块的admin管理员表")
@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

	@ApiOperation(value = "获取管理员列表", notes = "获取管理员列表")
	@GetMapping("/getList")
	public String getList() {
		StaticLog.info("...getList...获取全部管理员列表...");
		return ResultUtil.successWithDataAndMessage(adminService.getList(), "获取全部管理员列表");
	}

	@ApiOperation(value = "通过uid获取管理员", notes = "通过uid获取管理员")
	@GetMapping("/getAdminByUid")
	public String getAdminByUid(@RequestParam(value = "uid", defaultValue = "1", required = true) String uid) {
		return ResultUtil.successWithDataAndMessage(adminService.getAdminByUid(uid), "通过uid获取管理员");
	}

	@ApiOperation(value = "通过uid获取管理员-通过path传参方式", notes = "通过uid获取管理员-通过path传参方式")
	@GetMapping("/{uid}")
	public String getAdminByUidByPath(@PathVariable String uid) {
		return ResultUtil.successWithDataAndMessage(adminService.getAdminByUid(uid), "通过uid获取管理员-通过path传参方式");
	}

	@ApiOperation(value = "新增管理员", notes = "新增管理员")
	@GetMapping("/add")
	public String add() {
		return adminService.addAdmin();
	}
}
