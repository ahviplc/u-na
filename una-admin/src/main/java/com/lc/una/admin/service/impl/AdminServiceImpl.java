package com.lc.una.admin.service.impl;

import com.lc.una.admin.entity.Admin;
import com.lc.una.admin.mapper.AdminMapper;
import com.lc.una.admin.service.AdminService;
import com.lc.una.utils.tools.ResultUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

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
	public List<Admin> getList() {
		List<Admin> resultList = adminMapper.selectList(null);
		return resultList;
	}

	@Override
	public String addAdmin() {
		Admin admin = new Admin();
		admin.setUserName("ahviplc");
		admin.setEmail("ahlc@sina.cn");
		admin.setPassWord("123456");
		adminMapper.insert(admin);
		return ResultUtil.successWithMessage("新增管理员成功");
	}
}
