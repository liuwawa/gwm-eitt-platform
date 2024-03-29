package com.gwm.one.personnel.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.OSSClient;
import com.gwm.one.personnel.dao.FileDao;
import com.gwm.one.personnel.model.FileInfo;
import com.gwm.one.personnel.model.FileSource;

/**
 * 阿里云存储文件
 * 
 * @author lz
 *
 */
@Service("aliyunFileServiceImpl")
public class AliyunFileServiceImpl extends AbstractFileService {

	@Autowired
	private FileDao fileDao;

	@Override
	protected FileDao getFileDao() {
		return fileDao;
	}

	@Override
	protected FileSource fileSource() {
		return FileSource.ALIYUN;
	}

	@Autowired
	private OSSClient ossClient;

	@Value("${personnel.aliyun.bucketName}")
	private String bucketName;
	@Value("${personnel.aliyun.domain}")
	private String domain;

	@Override
	protected void uploadFile(MultipartFile file, FileInfo fileInfo) throws Exception {
		ossClient.putObject(bucketName, fileInfo.getName(), file.getInputStream());
		fileInfo.setUrl(domain + "/" + fileInfo.getName());
	}

	@Override
	protected boolean deleteFile(FileInfo fileInfo) {
		ossClient.deleteObject(bucketName, fileInfo.getName());
		return true;
	}

}
