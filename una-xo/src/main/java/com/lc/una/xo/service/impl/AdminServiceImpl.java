package com.lc.una.xo.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lc.una.base.enums.EStatus;
import com.lc.una.base.holder.RequestHolder;
import com.lc.una.commons.entity.Admin;
import com.lc.una.utils.tools.ResultUtil;
import com.lc.una.utils.tools.StringUtils;
import com.lc.una.xo.global.MessageConf;
import com.lc.una.xo.global.SQLConf;
import com.lc.una.xo.global.SysConf;
import com.lc.una.xo.mapper.AdminMapper;
import com.lc.una.xo.service.AdminService;
import com.lc.una.base.serviceImpl.SuperServiceImpl;
import com.lc.una.xo.vo.AdminVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 管理员表 服务实现类
 *
 * @author LC
 * @date 2018-09-04
 */
@Service
public class AdminServiceImpl extends SuperServiceImpl<AdminMapper, Admin> implements AdminService {

	@Autowired
	AdminService adminService;

	// @Autowired // Could not autowire. No beans of 'AdminMapper' type found.
	// @Resource // 使用这个 后面无提示
	// 上面两种注解程序的编译和运行都是没有问题
	@Resource
	private AdminMapper adminMapper;

	@Override
	public Admin getAdminByUid(String uid) {
		return adminMapper.getAdminByUid(uid);
	}

	@Override
	public List<Admin> getAllAdminList() {
		QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
		// 去除密码
		queryWrapper.select(Admin.class, i -> !i.getProperty().equals(SQLConf.PASS_WORD));
		queryWrapper.eq(SQLConf.STATUS, EStatus.ENABLE);
		List<Admin> list = adminMapper.selectList(queryWrapper);
		return list;
	}

	@Override
	public String getList(AdminVO adminVO) {
		QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
		if (StringUtils.isNotEmpty(adminVO.getKeyword())) {
			queryWrapper.like(SQLConf.USER_NAME, adminVO.getKeyword()).or().like(SQLConf.NICK_NAME, adminVO.getKeyword().trim());
		}
		Page<Admin> page = new Page<>();
		page.setCurrent(adminVO.getCurrentPage());
		page.setSize(adminVO.getPageSize());
		// 去除密码
		queryWrapper.select(Admin.class, i -> !i.getProperty().equals(SQLConf.PASS_WORD));
		queryWrapper.eq(SQLConf.STATUS, EStatus.ENABLE);
		IPage<Admin> pageList = adminService.page(page, queryWrapper);
		List<Admin> list = pageList.getRecords();
		return ResultUtil.successWithData(pageList);
	}

	@Override
	public String addAdmin(AdminVO adminVO) {
		String mobile = adminVO.getMobile();
		String userName = adminVO.getUserName();
		String email = adminVO.getEmail();
		if (StringUtils.isEmpty(userName)) {
			return ResultUtil.errorWithMessage(MessageConf.PARAM_INCORRECT);
		}
		if (StringUtils.isEmpty(email) && StringUtils.isEmpty(mobile)) {
			return ResultUtil.errorWithMessage("邮箱和手机号至少一项不能为空");
		}
		// 默认密码 123456
		String defaultPassword = "123456";
		QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(SQLConf.USER_NAME, userName);
		Admin temp = adminService.getOne(queryWrapper);
		if (temp == null) {
			Admin admin = new Admin();
			admin.setAvatar(adminVO.getAvatar());
			admin.setEmail(adminVO.getEmail());
			admin.setGender(adminVO.getGender());
			admin.setUserName(adminVO.getUserName());
			admin.setNickName(adminVO.getNickName());
			admin.setRoleUid(adminVO.getRoleUid());
			// 设置为未审核状态
			admin.setStatus(EStatus.ENABLE);
			PasswordEncoder encoder = new BCryptPasswordEncoder();
			//设置默认密码 123456
			admin.setPassWord(encoder.encode(defaultPassword));
			adminService.save(admin);
			//TODO 这里需要通过SMS模块，发送邮件告诉初始密码

			return ResultUtil.successWithMessage(MessageConf.INSERT_SUCCESS);
		}
		return ResultUtil.errorWithMessage(MessageConf.ENTITY_EXIST);
	}

	@Override
	public String editAdmin(AdminVO adminVO) {
		Admin admin = adminService.getById(adminVO.getUid());
		Assert.notNull(admin, MessageConf.PARAM_INCORRECT);
		//判断修改的对象是否是admin，admin的用户名必须是admin
		if (admin.getUserName().equals(SysConf.ADMIN) && !adminVO.getUserName().equals(SysConf.ADMIN)) {
			return ResultUtil.errorWithMessage("超级管理员用户名必须为admin");
		}
		QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq(SQLConf.STATUS, EStatus.ENABLE);
		queryWrapper.eq(SQLConf.USER_NAME, adminVO.getUserName());
		List<Admin> adminList = adminService.list(queryWrapper);
		if (adminList != null) {
			for (Admin item : adminList) {
				if (item.getUid().equals(adminVO.getUid())) {
					continue;
				} else {
					return ResultUtil.errorWithMessage("修改失败，用户名存在");
				}
			}
		}

		admin.setUserName(adminVO.getUserName());
		admin.setAvatar(adminVO.getAvatar());
		admin.setNickName(adminVO.getNickName());
		admin.setGender(adminVO.getGender());
		admin.setEmail(adminVO.getEmail());
		admin.setQqNumber(adminVO.getQqNumber());
		admin.setGithub(adminVO.getGithub());
		admin.setGitee(adminVO.getGitee());
		admin.setOccupation(adminVO.getOccupation());
		admin.setUpdateTime(new Date());
		admin.setMobile(adminVO.getMobile());
		admin.setRoleUid(adminVO.getRoleUid());
		// 无法直接修改密码，只能通过重置密码完成密码修改
		admin.setPassWord(null);
		admin.updateById();

		return ResultUtil.successWithMessage(MessageConf.UPDATE_SUCCESS);
	}

	@Override
	public String editMe(AdminVO adminVO) {
		String adminUid = RequestHolder.getAdminUid();
		if (StringUtils.isEmpty(adminUid)) {
			return ResultUtil.errorWithMessage(MessageConf.INVALID_TOKEN);
		}
		Admin admin = new Admin();
		// 【使用Spring工具类提供的深拷贝，减少大量模板代码】
		BeanUtils.copyProperties(adminVO, admin, SysConf.STATUS);
		admin.setUpdateTime(new Date());
		admin.updateById();
		return ResultUtil.successWithMessage(MessageConf.OPERATION_SUCCESS);
	}

	@Override
	public String changePwd(String oldPwd, String newPwd) {
		String adminUid = RequestHolder.getAdminUid();
		if (StringUtils.isEmpty(oldPwd) || StringUtils.isEmpty(newPwd)) {
			return ResultUtil.errorWithMessage(MessageConf.PARAM_INCORRECT);
		}
		Admin admin = adminService.getById(adminUid);
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		boolean isPassword = encoder.matches(oldPwd, admin.getPassWord());
		if (isPassword) {
			admin.setPassWord(encoder.encode(newPwd));
			admin.setUpdateTime(new Date());
			admin.updateById();
			return ResultUtil.successWithMessage(MessageConf.UPDATE_SUCCESS);
		} else {
			return ResultUtil.errorWithMessage(MessageConf.ERROR_PASSWORD);
		}
	}

	@Override
	public String resetPwd(AdminVO adminVO) {
		// 默认密码 123456
		String defaultPassword = "123456";
		// 获取当前用户的管理员uid
		String adminUid = RequestHolder.getAdminUid();
		Admin admin = adminService.getById(adminVO.getUid());
		// 判断是否是admin重置密码【其它超级管理员，无法重置admin的密码】
		if (SysConf.ADMIN.equals(admin.getUserName()) && !admin.getUid().equals(adminUid)) {
			return ResultUtil.errorWithMessage(MessageConf.UPDATE_ADMIN_PASSWORD_FAILED);
		} else {
			PasswordEncoder encoder = new BCryptPasswordEncoder();
			admin.setPassWord(encoder.encode(defaultPassword));
			admin.setUpdateTime(new Date());
			admin.updateById();
			return ResultUtil.successWithMessage(MessageConf.UPDATE_SUCCESS);
		}
	}

	@Override
	public String deleteBatchAdmin(List<String> adminUidList) {
		boolean checkResult = StringUtils.checkUidList(adminUidList);
		if (!checkResult) {
			return ResultUtil.errorWithMessage(MessageConf.PARAM_INCORRECT);
		}
		List<Admin> adminList = new ArrayList<>();
		adminUidList.forEach(item -> {
			Admin admin = new Admin();
			admin.setUid(item);
			admin.setStatus(EStatus.DISABLED);
			admin.setUpdateTime(new Date());
			adminList.add(admin);
		});
		adminService.updateBatchById(adminList);
		return ResultUtil.successWithMessage(MessageConf.DELETE_SUCCESS);
	}
}
