package com.lc.una.admin.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.log.StaticLog;
import com.lc.una.admin.annotion.AuthorityVerify.AuthorityVerify;
import com.lc.una.admin.annotion.CallMeLog.CallMeLog;
import com.lc.una.admin.global.MessageConf;
import com.lc.una.base.enums.PlatformEnum;
import com.lc.una.base.exception.ThrowableUtils;
import com.lc.una.base.validator.group.GetList;
import com.lc.una.base.validator.group.Insert;
import com.lc.una.commons.entity.Admin;
import com.lc.una.utils.tools.ResultUtil;
import com.lc.una.xo.service.AdminService;
import com.lc.una.xo.vo.AdminVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

	@CallMeLog(value = "通过uid获取管理员-通过path传参方式", platform = PlatformEnum.ADMIN)
	@AuthorityVerify(value = "LC")
	@ApiOperation(value = "获取管理员列表-包含分页信息", notes = "获取管理员列表-包含分页信息")
	@PostMapping("/getList")
	public String getList(@Validated({GetList.class}) @RequestBody AdminVO adminVO, BindingResult result) {
		StaticLog.info("...getList...获取管理员列表...");
		// 参数校验
		ThrowableUtils.checkParamArgument(result);
		return adminService.getList(adminVO);
	}

	@CallMeLog(value = "获取全部管理员列表", platform = PlatformEnum.ADMIN)
	@ApiOperation(value = "获取全部管理员列表", notes = "获取全部管理员列表")
	@PostMapping("/getAllAdminList")
	public String getAllAdminList() {
		return ResultUtil.successWithDataAndMessage(adminService.getAllAdminList(), "获取全部管理员列表");
	}

	@CallMeLog(value = "通过uid获取管理员", platform = PlatformEnum.ADMIN)
	@ApiOperation(value = "通过uid获取管理员", notes = "通过uid获取管理员")
	@GetMapping("/getAdminByUid")
	public String getAdminByUid(@RequestParam(value = "uid", defaultValue = "1", required = true) String uid) {
		Admin adminFromDB = adminService.getAdminByUid(uid);
		// 如果adminFromDB为空 代表数据库中不存在
		if (ObjectUtil.isNull(adminFromDB)) {
			return ResultUtil.errorWithMessage(MessageConf.ENTITY_NOT_EXIST);
		}
		return ResultUtil.successWithDataAndMessage(adminFromDB, "通过uid获取管理员");
	}

	@CallMeLog(value = "通过uid获取管理员-通过path传参方式", platform = PlatformEnum.ADMIN)
	@ApiOperation(value = "通过uid获取管理员-通过path传参方式", notes = "通过uid获取管理员-通过path传参方式")
	@GetMapping("/uid/{uid}")
	public String getAdminByUidByPath(@PathVariable String uid) {
		Admin adminFromDB = adminService.getAdminByUid(uid);
		// 如果adminFromDB为空 代表数据库中不存在
		if (ObjectUtil.isNull(adminFromDB)) {
			return ResultUtil.errorWithMessage(MessageConf.ENTITY_NOT_EXIST);
		}
		return ResultUtil.successWithDataAndMessage(adminFromDB, "通过uid获取管理员-通过path传参方式");
	}

	@CallMeLog(value = "新增管理员", platform = PlatformEnum.ADMIN)
	@ApiOperation(value = "新增管理员", notes = "新增管理员")
	@PostMapping("/add")
	public String add(@Validated({Insert.class}) @RequestBody AdminVO adminVO, BindingResult result) {
		// 参数校验
		ThrowableUtils.checkParamArgument(result);
		return adminService.addAdmin(adminVO);
	}
}
