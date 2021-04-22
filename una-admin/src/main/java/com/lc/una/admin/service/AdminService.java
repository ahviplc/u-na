package com.lc.una.admin.service;

import com.lc.una.admin.entity.Admin;

import java.util.List;

public interface AdminService {

	/**
	 * 通过UID获取Admin
	 *
	 * @param uid
	 * @return
	 */
	public Admin getAdminByUid(String uid);

	/**
	 * 获取管理员列表
	 *
	 * @return
	 */
	public List<Admin> getList();


	/**
	 * 添加管理员
	 *
	 * @return
	 */
	public String addAdmin();
}
