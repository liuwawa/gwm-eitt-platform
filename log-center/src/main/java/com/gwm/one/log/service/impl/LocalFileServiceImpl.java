package com.gwm.one.log.service.impl;

import com.gwm.one.log.dao.FileDao;
import com.gwm.one.log.utils.FileUtil;
import com.gwm.one.model.log.FileInfo;
import com.gwm.one.model.log.FileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

/**
 * 本地存储文件<br>
 * 该实现文件服务只能部署一台<br>
 * 如多台机器间能共享到一个目录，即可部署多台
 *
 * @author lz
 */
@Service("localFileServiceImpl")
public class LocalFileServiceImpl extends AbstractFileService {

    @Autowired
    private FileDao fileDao;

    @Override
    protected FileDao getFileDao() {
        return fileDao;
    }

    @Value("${file.local.urlPrefix}")
    private String urlPrefix;
    /**
     * 上传文件存储在本地的根路径
     */
    @Value("${file.local.path}")
    private String localFilePath;

    @Override
    protected FileSource fileSource() {
        return FileSource.LOCAL;
    }

    @Override
    protected void uploadFile(MultipartFile file, FileInfo fileInfo) throws Exception {
        int index = fileInfo.getName().lastIndexOf(".");
        // 文件扩展名
        String fileSuffix = fileInfo.getName().substring(index);

        String suffix = "/" + LocalDate.now().toString().replace("-", "/") + "/" + fileInfo.getId() + fileSuffix;

        String path = localFilePath + suffix;
        String url = urlPrefix + suffix;
        fileInfo.setPath(path);
        fileInfo.setUrl(url);

        FileUtil.saveFile(file, path);
    }

    @Override
    protected boolean deleteFile(FileInfo fileInfo) {
        return FileUtil.deleteFile(fileInfo.getPath());
    }
}
