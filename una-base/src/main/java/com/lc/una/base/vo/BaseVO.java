package com.lc.una.base.vo;

import com.lc.una.base.validator.annotion.IdValid;
import com.lc.una.base.validator.group.Delete;
import com.lc.una.base.validator.group.Update;
import lombok.Data;

/**
 * BaseVO   view object 表现层 基类对象
 *
 * @author: LC
 * @create: 2019-12-03-22:38
 */
@Data
public class BaseVO<T> extends PageInfo<T> {

    /**
     * 唯一UID
     */
    @IdValid(groups = {Update.class, Delete.class})
    private String uid;

    private Integer status;
}
