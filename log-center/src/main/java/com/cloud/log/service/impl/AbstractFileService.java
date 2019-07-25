package com.cloud.log.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.log.dao.FileDao;
import com.cloud.model.log.FileInfo;
import com.cloud.model.log.FileSource;
import com.cloud.log.service.FileService;
import com.cloud.log.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
public abstract class AbstractFileService extends ServiceImpl<FileDao, FileInfo> implements FileService {

    protected abstract FileDao getFileDao();

    @Override
    public FileInfo upload(MultipartFile file) throws Exception {
        FileInfo fileInfo = FileUtil.getFileInfo(file);
        // 先根据文件md5查询记录
        FileInfo oldFileInfo = getFileDao().getById(fileInfo.getId());
        // 如果已存在文件，避免重复上传同一个文件
        if (oldFileInfo != null) {
            return oldFileInfo;
        }

        if (!fileInfo.getName().contains(".")) {
            throw new IllegalArgumentException("缺少后缀名");
        }

        uploadFile(file, fileInfo);

        fileInfo.setSource(fileSource().name());// 设置文件来源
        getFileDao().save(fileInfo);// 将文件信息保存到数据库

        log.info("上传文件：{}", fileInfo);

        return fileInfo;
    }

    /**
     * 文件来源
     *
     * @return
     */
    protected abstract FileSource fileSource();

    /**
     * 上传文件
     *
     * @param file
     * @param fileInfo
     */
    protected abstract void uploadFile(MultipartFile file, FileInfo fileInfo) throws Exception;

//	@Override
//	public void delete(FileInfo fileInfo) {
//		deleteFile(fileInfo);
//		getFileDao().delete(fileInfo.getId());
//		log.info("删除文件：{}", fileInfo);
//	}

    /**
     * 删除文件资源
     *
     * @param fileInfo
     * @return
     */
    protected abstract boolean deleteFile(FileInfo fileInfo);
}
