package com.lc.una.xo.mapper;

import com.lc.una.commons.entity.Admin;
import com.lc.una.base.mapper.SuperMapper;
import org.apache.ibatis.annotations.Param;

/**
 * 管理员表 Mapper 接口
 *
 * @author LC
 * @date 2018-09-04
 */
public interface AdminMapper extends SuperMapper<Admin> {

	/**
	 * 通过uid获取管理员
	 *
	 * @return
	 */
	public Admin getAdminByUid(@Param("uid") String uid);
}
