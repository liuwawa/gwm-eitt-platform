package com.cloud.log.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.model.log.FileInfo;
import org.springframework.web.multipart.MultipartFile;

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
