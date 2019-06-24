package com.cloud.user.controller;


import com.cloud.model.common.Response;
import com.cloud.model.user.SysGroupExpand;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 组织拓展表 前端控制器
 * </p>
 *
 * @author liuek
 * @since 2019-06-21
 */
@RestController
@RequestMapping("/groupExpand")
public class SysGroupExpandController {
    /**
     *
     * @param sysGroupExpand 前台传来参数
     * @return 添加结果
     * 添加一个拓展组织
     *
     */
    @PostMapping("saveGroupExpand")
    public Response saveGroupExpand(SysGroupExpand sysGroupExpand) {
        boolean result = sysGroupExpand.insert();
        return null;
    }

    /**
     *
     * @param sysGroupExpand 前台传来的数据
     * @return 修改结果
     * 修改一个组织拓展的数据
     *
     */
    @PostMapping("updateGroupExpand")
    public Response updateGroupExpandById(SysGroupExpand sysGroupExpand){
        boolean result = sysGroupExpand.updateById();
        return null;
    }

    /**
     *
     * @param sysGroupExpand 前台穿来的数据
     * @return 删除结果
     * 删除指定的拓展数据
     *
     */
    @PostMapping("deleteGroupExpand")
    public Response deleteGroupExpandById(SysGroupExpand sysGroupExpand){
        boolean result = sysGroupExpand.deleteById();
        return null;
    }





}

