package com.lc.una.admin.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lc.una.admin.enums.EStatus;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


/**
 * Entity基类
 *
 * @author LC
 * @date 2021年4月22日16:28:42
 */
@Data
@SuppressWarnings("rawtypes")
public class SuperEntity<T extends Model> extends Model {

	/**
	 *
	 */
	private static final long serialVersionUID = -4851055162892178225L;

	/**
	 * 唯一UID
	 */
	@TableId(value = "uid", type = IdType.ASSIGN_UUID)
	private String uid;

	/**
	 * 状态 0：失效  1：生效
	 */
	private int status;

	/**
	 * @TableField 配置需要填充的字段
	 * 创建时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date createTime;

	/**
	 * 更新时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date updateTime;

	public SuperEntity() {
		this.status = EStatus.ENABLE;
		this.createTime = new Date();
		this.updateTime = new Date();
	}
}
