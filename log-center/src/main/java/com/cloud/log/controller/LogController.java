package com.cloud.log.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cloud.common.vo.ResultVo;
import com.cloud.log.service.LogService;
import com.cloud.model.common.Page;
import com.cloud.model.common.PageResult;
import com.cloud.model.log.Log;
import com.cloud.model.log.LogAnnotation;
import com.cloud.model.log.constants.LogModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class LogController {

    @Autowired
    private LogService logService;

    @PostMapping("/logs-anon/internal")
    public void saveLog(@RequestBody Log log) {
        logService.saveLog(log);
    }

    /**
     * 日志模块
     *
     * @return
     */
    @PreAuthorize("hasAuthority('log:query')")
    @GetMapping("/logs-modules")
    public Map<String, String> logModule() {
        return LogModule.MODULES;
    }

    /**
     * 日志查询
     *
     * @param params
     * @return
     */
    @PreAuthorize("hasAuthority('log:query')")
    @GetMapping("/logs")
    public Page<Log> findLogs(@RequestParam Map<String, Object> params) {
        return logService.findLogs(params);
    }
    /**
     * 日志分页查询
     *
     * @param params
     * @return
     */
    @PreAuthorize("hasAuthority('log:query')")
    @PostMapping("/findPage")
    public PageResult findLogsPage(@RequestBody Map<String, Object> params) {
        Long pageNum = Long.valueOf(params.get("pageNum").toString());
        Long pageSize = Long.valueOf(params.get("pageSize").toString());
        String userName = String.valueOf(params.get("userName").toString());
        QueryWrapper<Log> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username",userName).orderByDesc("id");
        IPage<Log> pageLogs = logService.page(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageNum,pageSize),queryWrapper);

        return PageResult.builder().content(pageLogs.getRecords()).
                pageNum(pageLogs.getCurrent()).
                pageSize(pageLogs.getSize()).
                totalPages(pageLogs.getPages()).
                totalSize(pageLogs.getTotal()).build();
    }

    @LogAnnotation(module = LogModule.DELETE_ROLE)
    @PreAuthorize("hasAuthority('back:sms:delete')")
    @DeleteMapping("/delAll")
    public ResultVo deleteAll() {
        try {
            logService.delAllLog();
            return ResultVo.builder().msg("删除成功").data(null).code(200).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.builder().msg("删除失败").data(null).code(500).build();
        }
    }

}
