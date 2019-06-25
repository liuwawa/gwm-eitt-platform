package com.cloud.user.controller;


import com.cloud.common.vo.ResultVo;
import com.cloud.enums.ResponseStatus;
import com.cloud.model.common.Response;
import com.cloud.model.user.SysGrouping;
import com.cloud.user.service.SysGroupingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 组织分组表 前端控制器
 * </p>
 *
 * @author liuek
 * @since 2019-06-21
 */
@RestController
@RequestMapping("/grouping")
@Slf4j
public class SysGroupingController {
    @Autowired
    private SysGroupingService sysGroupingService;

    /**
     * @param sysGrouping 需要添加的分组
     * @return 操作结果
     * 添加分组
     */
    @PostMapping("saveGrouping")
    public ResultVo saveGrouping(@RequestBody SysGrouping sysGrouping) {
        if (!sysGroupingService.save(sysGrouping)) {
            log.error("添加分组操作失败");
            return new ResultVo(ResponseStatus.RESPONSE_GROUPING_HANDLE_FAILED.code, ResponseStatus.RESPONSE_GROUPING_HANDLE_FAILED.message, null);
        }
        log.info("添加分组操作成功，添加的分组名称为" + sysGrouping.getGroupingName());
        return new ResultVo(ResponseStatus.RESPONSE_GROUPING_HANDLE_SUCCESS.code, ResponseStatus.RESPONSE_GROUPING_HANDLE_SUCCESS.message, null);
    }

    /**
     *
     * @param id 需要获取详情的id
     * @return 状态和结果数据
     *  根据id查询出该分组的详情
     */
    @GetMapping("groupingDetail")
    public ResultVo<SysGrouping> getGroupingDetailById(Integer id) {
        SysGrouping groupingDetail = sysGroupingService.getById(id);
        return new ResultVo(ResponseStatus.RESPONSE_GROUPING_HANDLE_SUCCESS.code, ResponseStatus.RESPONSE_GROUPING_HANDLE_SUCCESS.message, groupingDetail);
    }
}

