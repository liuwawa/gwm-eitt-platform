package com.cloud.user.controller;


import com.cloud.common.vo.ResultVo;
import com.cloud.enums.ResponseStatus;
import com.cloud.model.common.Response;
import com.cloud.model.user.SysGroupExpand;
import com.cloud.user.service.SysGroupExpandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@Slf4j
public class SysGroupExpandController {
    /*@Autowired
    private SysGroupExpandService sysGroupExpandService;

    *//**
     * @param sysGroupExpand 前台传来参数
     * @return 添加结果
     * 编辑一个组织的拓展组织
     *//*
    @PostMapping("/saveGroupExpand")
    public ResultVo saveGroupExpand(@RequestBody SysGroupExpand sysGroupExpand) {
        try {
            if (!sysGroupExpandService.save(sysGroupExpand)) {
                log.info("添加组织拓展信息操作失败，添加的组织id:{}", sysGroupExpand.getGroupId());
                return new ResultVo(ResponseStatus.RESPONSE_GROUPEXPAND_HANDLE_FAILED.code, ResponseStatus.RESPONSE_GROUPEXPAND_HANDLE_FAILED.message, null);
            }
        } catch (Exception e) {
            log.error("添加组织拓展信息，出现异常！");
            return new ResultVo(ResponseStatus.RESPONSE_GROUPEXPAND_HANDLE_ERROR.code, ResponseStatus.RESPONSE_GROUPEXPAND_HANDLE_ERROR.message, null);
        }
        log.info("添加组织拓展信息操作成功，添加的组织id:{}", sysGroupExpand.getGroupId());
        return new ResultVo(ResponseStatus.RESPONSE_GROUPEXPAND_HANDLE_SUCCESS.code, ResponseStatus.RESPONSE_GROUPEXPAND_HANDLE_SUCCESS.message, null);
    }

    *//**
     * @param sysGroupExpand 前台传来的数据
     * @return 修改结果
     * 修改一个组织拓展的数据
     *//*
    @PostMapping("updateGroupExpand")
    public Response updateGroupExpandById(SysGroupExpand sysGroupExpand) {
        boolean result = sysGroupExpand.updateById();
        return null;
    }

    *//**
     * @param sysGroupExpand 前台穿来的数据
     * @return 删除结果
     * 删除指定的拓展数据
     *//*
    @PostMapping("deleteGroupExpand")
    public Response deleteGroupExpandById(SysGroupExpand sysGroupExpand) {
        boolean result = sysGroupExpand.deleteById();
        return null;
    }
*/

}

