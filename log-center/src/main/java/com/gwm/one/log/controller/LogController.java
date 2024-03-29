package com.gwm.one.log.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gwm.one.common.plugins.ApiJsonObject;
import com.gwm.one.common.plugins.ApiJsonProperty;
import com.gwm.one.common.vo.ResultVo;
import com.gwm.one.log.service.LogService;
import com.gwm.one.model.common.PageResult;
import com.gwm.one.model.log.Log;
import com.gwm.one.model.log.LogAnnotation;
import com.gwm.one.model.log.constants.LogModule;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Api(value = "日志", tags = {"日志操作接口 LogController"})
public class LogController {

    @Autowired
    private LogService logService;

    @PostMapping("/logs-anon/internal")
    @ApiOperation(value = "存入日志")
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
    @ApiOperation(value = "日志模块")
    public Map<String, String> logModule() {
        return LogModule.MODULES;
    }


    /**
     * 日志分页查询
     *
     * @param params
     * @return
     */
    @PreAuthorize("hasAuthority('log:query')")
    @PostMapping("/findPage")
    @ApiOperation(value = "分页多条件查询日志记录", notes = "参数，pageNum，pageSize，userName")
    public PageResult findLogsPage(
            @ApiJsonObject(name = "分页多条件查询日志记录", value = {
                    @ApiJsonProperty(key = "pageNum", example = "1", description = "pageNum"),
                    @ApiJsonProperty(key = "pageSize", example = "10", description = "pageSize"),
                    @ApiJsonProperty(key = "userName", example = "userName", description = "userName")})
            @RequestBody Map<String, Object> params) {
        Long pageNum = Long.valueOf(params.get("pageNum").toString());
        Long pageSize = Long.valueOf(params.get("pageSize").toString());
        String userName = String.valueOf(params.get("userName").toString());
        QueryWrapper<Log> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username", userName).orderByDesc("id");
        IPage<Log> pageLogs = logService.page(new Page<>(pageNum, pageSize), queryWrapper);

        return PageResult.builder().content(pageLogs.getRecords()).
                pageNum(pageLogs.getCurrent()).
                pageSize(pageLogs.getSize()).
                totalPages(pageLogs.getPages()).
                totalSize(pageLogs.getTotal()).build();
    }

    @LogAnnotation(module = LogModule.DELETE_ROLE)
    @PreAuthorize("hasAuthority('back:log:delete')")
    @DeleteMapping("/delLogsAll")
    @ApiOperation(value = "删除全部日志")
    @ApiResponses({@ApiResponse(code = 200, message = "响应成功"), @ApiResponse(code = 500, message = "操作错误")})
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
