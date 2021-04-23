package com.lc.una.base.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lc.una.base.mapper.SuperMapper;
import com.lc.una.base.service.SuperService;


/**
 * SuperService 实现类（ 泛型：M 是  mapper(dao) 对象，T 是实体 ）
 *
 * @param <T>
 * @author LC
 * @date 2018年9月4日10:38:18
 */

public class SuperServiceImpl<M extends SuperMapper<T>, T> extends ServiceImpl<M, T> implements SuperService<T> {

}
