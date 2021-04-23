package com.lc.una.xo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lc.una.base.enums.EStatus;
import com.lc.una.commons.entity.Admin;
import com.lc.una.utils.tools.*;
import com.lc.una.xo.global.MessageConf;
import com.lc.una.xo.global.SQLConf;
import com.lc.una.xo.global.SysConf;
import com.lc.una.xo.mapper.AdminMapper;
import com.lc.una.xo.service.AdminService;
import com.lc.una.base.serviceImpl.SuperServiceImpl;
import com.lc.una.xo.vo.AdminVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
}
