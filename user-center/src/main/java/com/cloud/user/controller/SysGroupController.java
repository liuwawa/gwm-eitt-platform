package com.cloud.user.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cloud.enums.ResponseStatus;
import com.cloud.model.common.Response;
import com.cloud.model.user.SysGroup;
import com.cloud.user.service.SysGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

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
    @Autowired
    private SysGroupService sysGroupService;


    /**
     * @param sysGroup 前台传来参数
     * @return 新增结果
     * 新增一个组织(主表)
     */
//    @PreAuthorize("hasAnyAuthority()")
    @PostMapping("/saveGroup")
    public Response saveGroup(SysGroup sysGroup) {
        Response response = new Response();
        try {
            boolean save = sysGroupService.save(sysGroup);
            if (!save) {
                response.setErrorCode(ResponseStatus.RESPONSE_OPERATION_ERROR.code);
                response.setMessage(ResponseStatus.RESPONSE_OPERATION_ERROR.message);
                return response;
            }
        } catch (Exception e) {
            response.setErrorCode(ResponseStatus.RESPONSE_OPERATION_ERROR.code);
            response.setMessage(ResponseStatus.RESPONSE_OPERATION_ERROR.message);
            return response;
        }
        response.setErrorCode(ResponseStatus.RESPONSE_SUCCESS.code);
        response.setMessage(ResponseStatus.RESPONSE_SUCCESS.message);
        return response;
    }

    /**
     * @param sysGroup 需要修改的组织
     * @return 修改结果
     * 修改组织表
     */
    //    @PreAuthorize("hasAnyAuthority()")
    @PostMapping("/updateGroup")
    public Response updateById(SysGroup sysGroup) {
        Response response = new Response();

        try {
            sysGroup.setUpdateTime(new Date());
            boolean update = sysGroupService.updateById(sysGroup);
            if (!update) {
                response.setErrorCode(ResponseStatus.RESPONSE_OPERATION_ERROR.code);
                response.setMessage(ResponseStatus.RESPONSE_OPERATION_ERROR.message);
                return response;
            }
        } catch (Exception e) {
            response.setErrorCode(ResponseStatus.RESPONSE_OPERATION_ERROR.code);
            response.setMessage(ResponseStatus.RESPONSE_OPERATION_ERROR.message);
            return response;
        }
        return response;
    }

    /**
     * @param sysGroup 需要删除的sysGroup
     * @return 删除结果
     */
    //    @PreAuthorize("hasAnyAuthority()")
    @PostMapping("/deleteGroup")
    public Response deleteById(Integer groupId) {
        Response response = new Response();
        try {
           delGroupBatch(groupId);
        } catch (Exception e) {
            response.setErrorCode(ResponseStatus.RESPONSE_OPERATION_ERROR.code);
            response.setMessage(ResponseStatus.RESPONSE_OPERATION_ERROR.message);
            return response;
        }
        return response;
    }

    /**批量删除部门子级*/
    public void delGroupBatch(Integer groupId) {
        //删除该部门
        sysGroupService.removeById(groupId);
        //查询下级
        QueryWrapper<SysGroup> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("groupParentId",groupId);
        List<SysGroup> list = sysGroupService.list(queryWrapper);

        if(list!=null||list.size()!=0){
            //递归删除
            for (SysGroup sysGroup: list) {
                delGroupBatch(sysGroup.getGroupId());
            }

        }else{
            return;
        }

    }
}

