package com.lc.una.admin.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lc.una.admin.entity.Admin;
import org.apache.ibatis.annotations.Param;

/**
 * 管理员表 Mapper 接口
 *
 * @author LC
 * @since 2021年4月22日14:52:28
 */
public interface AdminMapper extends BaseMapper<Admin> {
	/**
	 * 通过uid获取管理员
	 *
	 * @return
	 */
	public Admin getAdminByUid(@Param("uid") String uid);
}
