package com.gwm.one.log.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gwm.one.common.plugins.ApiJsonObject;
import com.gwm.one.common.plugins.ApiJsonProperty;
import com.gwm.one.common.vo.ResultVo;
import com.gwm.one.log.config.FileServiceFactory;
import com.gwm.one.log.service.FileService;
import com.gwm.one.model.common.PageResult;
import com.gwm.one.model.log.FileInfo;
import com.gwm.one.model.log.LogAnnotation;
import com.gwm.one.model.log.constants.LogModule;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/files")
@Api(value = "文件", tags = {"文件操作接口 FileController"})
public class FileController {

    @Autowired
    private FileServiceFactory fileServiceFactory;

    @Resource(name = "localFileServiceImpl")
    private FileService fileService;

    /**
     * 文件上传<br>
     * 根据fileSource选择上传方式，目前仅实现了上传到本地<br>
     * 如有需要可上传到第三方，如阿里云、七牛等
     *
     * @param file
     * @param fileSource FileSource
     * @return
     * @throws Exception
     */
    @LogAnnotation(module = LogModule.FILE_UPLOAD, recordParam = false)
    @PostMapping
    @ApiOperation(value = "文件上传")
    public FileInfo upload(@RequestParam("file") MultipartFile file, String fileSource) throws Exception {
        FileService fileService = fileServiceFactory.getFileService(fileSource);
        return fileService.upload(file);
    }


    /**
     * 分页查询文件
     *
     * @param params
     * @return
     */
    @PreAuthorize("hasAuthority('file:query')")
    @PostMapping("/findPages")
    @ApiOperation(value = "分页多条件查询文件记录", notes = "参数，pageNum，pageSize，fileName")
    public PageResult findPage(
            @ApiJsonObject(name = "分页多条件查询文件记录", value = {
                    @ApiJsonProperty(key = "pageNum", example = "1", description = "pageNum"),
                    @ApiJsonProperty(key = "pageSize", example = "10", description = "pageSize"),
                    @ApiJsonProperty(key = "fileName", example = "name", description = "name")})
            @RequestBody Map<String, Object> params) {
        Long pageIndex = Long.valueOf(params.get("pageNum").toString());
        Long pageSize = Long.valueOf(params.get("pageSize").toString());
        String condition = String.valueOf(params.get("fileName").toString());

        QueryWrapper<FileInfo> queryWrapper = new QueryWrapper<>();

        queryWrapper.like("name", condition);
        queryWrapper.orderByDesc("createTime");
        IPage<FileInfo> fileInfoPage = fileService.page(new Page<>(pageIndex, pageSize), queryWrapper);
        return PageResult.builder().content(fileInfoPage.getRecords()).
                pageNum(fileInfoPage.getCurrent()).
                pageSize(fileInfoPage.getSize()).
                totalPages(fileInfoPage.getPages()).
                totalSize(fileInfoPage.getTotal()).build();
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @LogAnnotation(module = LogModule.FILE_DELETE)
    @PreAuthorize("hasAuthority('file:del')")
    @DeleteMapping("/delBatch/{ids}")
    @ApiOperation(value = "批量删除", notes = "传入以，拼接的id字符串")
    @ApiResponses({@ApiResponse(code = 200, message = "响应成功"), @ApiResponse(code = 500, message = "操作错误")})
    public ResultVo<Object> deleteFile(@PathVariable String ids) {
        List<Integer> list = new ArrayList<>();
        String[] idList = ids.split(",");
        for (int i = 0; i < idList.length; i++) {
            list.add(Integer.valueOf(idList[i]));
        }
        try {
            fileService.removeByIds(list);
            return ResultVo.builder().msg("删除成功").data(null).code(200).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVo.builder().msg("删除失败").data(null).code(500).build();
        }
    }


}
