package com.lc.una.utils.base.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * mapper 父类，注意这个类不要让 mybatis-plus 扫描到！！
 *
 * @author LC
 * @date 2021年4月22日12:33:27
 */
public interface SuperMapper<T> extends BaseMapper<T> {

	// 这里可以放一些公共的方法
}
