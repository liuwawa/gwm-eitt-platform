package com.cloud.user.controller;


import com.cloud.model.common.Response;
import com.cloud.model.user.SysGroup;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * <p>
 * 组织表 前端控制器
 * </p>
 *
 * @author liuek
 * @since 2019-06-21
 */
@RestController
@RequestMapping("/group")
public class SysGroupController {
    /**
     *
     * @param sysGroup 前台传来参数
     * @return 新增结果
     * 新增一个组织(主表)
     *
     */
    @PostMapping("saveGroup")
    public Response saveGroup(SysGroup sysGroup){
        sysGroup.setCreateTime(new Date());
        boolean result = sysGroup.insert();
        if(result == false){
        }
        return null;
    }

    /**
     *
     * @param sysGroup 需要修改的组织
     * @return 修改结果
     * 修改组织表
     *
     */
    @PostMapping("updateGroup")
    public Response updateById(SysGroup sysGroup){
        sysGroup.setUpdateTime(new Date());
        boolean b = sysGroup.updateById();
        return null;
    }

    /**
     *
     * @param sysGroup 需要删除的sysGroup
     * @return 删除结果
     */
    @PostMapping("deleteGroup")
    public Response deleteById(SysGroup sysGroup){
        boolean b = sysGroup.deleteById();
        return null;
    }


}

