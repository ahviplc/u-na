package com.lc.una.xo.service;

import com.lc.una.commons.entity.Admin;
import com.lc.una.xo.vo.AdminVO;
import com.lc.una.base.service.SuperService;

import java.util.List;

/**
 * 管理员表 服务类
 *
 * @author LC
 * @date 2018-09-04
 */
public interface AdminService extends SuperService<Admin> {

	/**
	 * 通过UID获取Admin
	 *
	 * @param uid
	 * @return
	 */
	public Admin getAdminByUid(String uid);

	/**
	 * 获取全部管理员列表
	 *
	 * @return
	 */
	public List<Admin> getAllAdminList();

	/**
	 * 获取管理员列表 包含分页信息
	 *
	 * @param adminVO
	 * @return
	 */
	public String getList(AdminVO adminVO);

	/**
	 * 添加管理员
	 *
	 * @param adminVO
	 * @return
	 */
	public String addAdmin(AdminVO adminVO);

	/**
	 * 编辑管理员
	 *
	 * @param adminVO
	 * @return
	 */
	public String editAdmin(AdminVO adminVO);

	/**
	 * 编辑当前管理员信息
	 *
	 * @return
	 */
	public String editMe(AdminVO adminVO);

	/**
	 * 修改密码
	 *
	 * @return
	 */
	public String changePwd(String oldPwd, String newPwd);

	/**
	 * 重置密码
	 *
	 * @param adminVO
	 * @return
	 */
	public String resetPwd(AdminVO adminVO);

	/**
	 * 批量删除管理员
	 *
	 * @param adminUids
	 * @return
	 */
	public String deleteBatchAdmin(List<String> adminUids);
}
