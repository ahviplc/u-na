package com.lc.una.admin.annotion.CallMeLog;

import com.lc.una.base.enums.PlatformEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标注该注解的方法需要记录调用我详细相关信息
 * 记录的请求内容如下:
 * 记录的请求内容如下:
 * 请求时间
 * 请求IP
 * 请求Host
 * 请求端口Port
 * 请求地址uri
 * 请求方法
 * 请求参数
 * 请求头
 * 请求类型
 * 请求token
 * 请求类方法签名
 * 请求类方法签名的名称
 * 请求类方法参数
 * 对应的类名 方法名
 * 请求url
 * 执行请求时长
 *
 * @author LC
 * @date 2021年4月28日13:22:30
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CallMeLog {
	/**
	 * 业务名称 具体哪个方法
	 */
	String value() default "";

	/**
	 * 平台，默认为Admin端
	 */
	PlatformEnum platform() default PlatformEnum.ADMIN;
}
