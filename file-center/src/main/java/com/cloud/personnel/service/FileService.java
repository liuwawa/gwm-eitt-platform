package com.cloud.personnel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import com.cloud.personnel.model.FileInfo;

public interface FileService extends IService<FileInfo> {

	/**
	 * 上传文件
	 *
	 * @param file
	 * @return
	 * @throws Exception
	 */
	FileInfo upload(MultipartFile file) throws Exception;

	/**
	 * 删除文件
	 *
	 * @param fileInfo
	 */
//	void delete(FileInfo fileInfo);

}
