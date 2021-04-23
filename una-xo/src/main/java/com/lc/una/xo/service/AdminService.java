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
     * 获取管理员列表
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
     * 通过UID获取Admin
     *
     * @param uid
     * @return
     */
    public Admin getAdminByUid(String uid);

    public List<Admin> getAllAdminList();
}
