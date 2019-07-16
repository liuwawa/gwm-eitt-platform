package com.cloud.file.controller;

import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cloud.common.vo.ResultVo;
import com.cloud.model.common.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.cloud.common.utils.PageUtil;
import com.cloud.file.config.FileServiceFactory;
import com.cloud.file.dao.FileDao;
import com.cloud.file.model.FileInfo;
import com.cloud.file.service.FileService;
import com.cloud.model.common.Page;
import com.cloud.model.log.LogAnnotation;
import com.cloud.model.log.constants.LogModule;

import javax.annotation.Resource;

@RestController
@RequestMapping("/files")
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
	 * @param fileSource
	 *            FileSource
	 * 
	 * @return
	 * @throws Exception
	 */
	@LogAnnotation(module = LogModule.FILE_UPLOAD, recordParam = false)
	@PostMapping
	public FileInfo upload(@RequestParam("file") MultipartFile file, String fileSource) throws Exception {
		FileService fileService = fileServiceFactory.getFileService(fileSource);
		return fileService.upload(file);
	}

	/**
	 * layui富文本文件自定义上传
	 * 
	 * @param file
	 * @param fileSource
	 * @return
	 * @throws Exception
	 */
//	@LogAnnotation(module = LogModule.FILE_UPLOAD, recordParam = false)
//	@PostMapping("/layui")
//	public Map<String, Object> uploadLayui(@RequestParam("file") MultipartFile file, String fileSource)
//			throws Exception {
//		FileInfo fileInfo = upload(file, fileSource);
//
//		Map<String, Object> map = new HashMap<>();
//		map.put("code", 0);
//		Map<String, Object> data = new HashMap<>();
//		data.put("src", fileInfo.getUrl());
//		map.put("data", data);
//
//		return map;
//	}

	/**
	 * 文件删除
	 * 
	 * @param id
	 */
//	@LogAnnotation(module = LogModule.FILE_DELETE)
//	@PreAuthorize("hasAuthority('file:del')")
//	@DeleteMapping("/{id}")
//	public void delete(@PathVariable String id) {
//		FileInfo fileInfo = fileDao.getById(id);
//		if (fileInfo != null) {
//			FileService fileService = fileServiceFactory.getFileService(fileInfo.getSource());
//			fileService.delete(fileInfo);
//		}
//	}

	@Autowired
	private FileDao fileDao;

	/**
	 * 文件查询
	 * 
	 * @param params
	 * @return
	 */
//	@PreAuthorize("hasAuthority('file:query')")
//	@GetMapping
//	public Page<FileInfo> findFiles(@RequestParam Map<String, Object> params) {
//		int total = fileDao.count(params);
//		List<FileInfo> list = Collections.emptyList();
//		if (total > 0) {
//			PageUtil.pageParamConver(params, true);
//
//			list = fileDao.findData(params);
//		}
//		return new Page<>(total, list);
//	}

	/**
	 * 分页查询文件
	 * @param params
	 * @return
	 */
	@PreAuthorize("hasAuthority('file:query')")
	@PostMapping("/findPages")
	public PageResult findPage(@RequestBody Map<String, Object> params) {
		Long pageIndex = Long.valueOf(params.get("pageNum").toString());
		Long pageSize = Long.valueOf(params.get("pageSize").toString());
		String condition = String.valueOf(params.get("fileName").toString());

		QueryWrapper<FileInfo> queryWrapper = new QueryWrapper<>();

		queryWrapper.like("name",condition);

		IPage<FileInfo> fileInfoPage = fileService.page(new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(pageIndex, pageSize),queryWrapper);
		return PageResult.builder().content(fileInfoPage.getRecords()).
				pageNum(fileInfoPage.getCurrent()).
				pageSize(fileInfoPage.getSize()).
				totalPages(fileInfoPage.getPages()).
				totalSize(fileInfoPage.getTotal()).build();
	}

	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@LogAnnotation(module = LogModule.FILE_DELETE)
	@PreAuthorize("hasAuthority('file:del')")
	@DeleteMapping("/delBatch/{ids}")
	public ResultVo<Object> deleteFile(@PathVariable String ids){
		List<Integer> list = new ArrayList<>();
		String[] idList = ids.split(",");
		for(int i=0;i<idList.length;i++){
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
