package com.lc.una.admin.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * <p>
 * 角色信息表
 * </p>
 *
 * @author LC
 * @since 2021年4月22日12:27:58
 */
@Data
@TableName("t_una_role")
public class Role extends SuperEntity<Role> {

	private static final long serialVersionUID = 1L;

	/**
	 * 角色名称
	 */
	private String roleName;

	/**
	 * 介绍
	 */
	@TableField(updateStrategy = FieldStrategy.IGNORED)
	private String summary;

	/**
	 * 该角色所能管辖的区域
	 */
	@TableField(updateStrategy = FieldStrategy.IGNORED)
	private String categoryMenuUids;
}
