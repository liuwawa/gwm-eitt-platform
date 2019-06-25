package com.cloud.user.controller;


import com.cloud.enums.ResponseStatus;
import com.cloud.model.common.Response;
import com.cloud.model.user.SysGroup;
import com.cloud.user.service.SysGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private SysGroupService sysGroupService;


    /**
     * @param sysGroup 前台传来参数
     * @return 新增结果
     * 新增一个组织(主表)
     */
//    @PreAuthorize("hasAnyAuthority()")
    @PostMapping("/saveGroup")
    public Response saveGroup( SysGroup sysGroup) {
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
    @PostMapping("updateGroup")
    public Response updateById(SysGroup sysGroup) {
        sysGroup.setUpdateTime(new Date());
        boolean b = sysGroup.updateById();
        return null;
    }

    /**
     * @param sysGroup 需要删除的sysGroup
     * @return 删除结果
     */
    //    @PreAuthorize("hasAnyAuthority()")
    @PostMapping("deleteGroup")
    public Response deleteById(SysGroup sysGroup) {
        boolean b = sysGroup.deleteById();
        return null;
    }

//    @PostMapping("selectAll")
//    public Response findAll() {
//
//    }

}

