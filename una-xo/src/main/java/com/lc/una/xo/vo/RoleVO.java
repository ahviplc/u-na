package com.lc.una.xo.vo;

import com.lc.una.base.validator.annotion.NotBlank;
import com.lc.una.base.validator.group.Insert;
import com.lc.una.base.validator.group.Update;
import com.lc.una.base.vo.BaseVO;
import lombok.Data;

/**
 * <p>
 * RoleVO
 * </p>
 *
 * @author LC
 * @date 2019年12月20日16:47:02
 */
@Data
public class RoleVO extends BaseVO<RoleVO> {


    /**
     * 角色名称
     */
    @NotBlank(groups = {Insert.class, Update.class})
    private String roleName;

    /**
     * 介绍
     */
    private String summary;

    /**
     * 该角色所能管辖的区域
     */
    private String categoryMenuUids;

}
