package com.luoy.library.service.impl;

import com.luoy.library.common.util.MongoDbUtil;
import com.luoy.library.service.IFileService;
import com.mongodb.gridfs.GridFSDBFile;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * @author : ChenCong
 * @date : Created in 8:44 2018/3/6
 */
@Service("fileService")
public class FileServiceImpl implements IFileService {

    /**
     * 上传文件，返回文件在MongoDB中的objectId
     *
     * @param file file
     * @return 上传成功返回objectId, 失败返回null
     */
    @Override
    public String upload(File file) {
        if (!file.exists()) {
            return null;
        }
        return MongoDbUtil.uploadFileToGridFs(file);
    }

    /**
     * 以新名字的上传文件<br>
     * 上传文件时重命名文件，再上传
     *
     * @param file    文件
     * @param newName 文件的新名字
     * @return 上传成功返回objectId, 失败或文件file不存在返回null
     */
    @Override
    public String upload(String newName, File file) {
        return MongoDbUtil.uploadFileToGridFs(newName, file);
    }

    /**
     * 通过objectId删除对应文件
     *
     * @param objectId objectId
     */
    @Override
    public void deleteByObjectId(String objectId) {
        MongoDbUtil.deleteByObjectId(objectId);
    }

    /**
     * 通过objectId返回一个GridFSDBFile流作为图片显示用
     *
     * @param objectId objectId
     * @return 返回GridFSDBFile
     */
    @Override
    public GridFSDBFile getFileByObjectId(String objectId) {
        return MongoDbUtil.getGridFSFileByObjectId(objectId);
    }

    /**
     * 下载文件到本地
     *
     * @param objectId 文件对应的objectId
     * @param filename 本地目标文件
     */
    @Override
    public void downloadFile(String objectId, String filename) {
        MongoDbUtil.downloadFile(objectId, filename);
    }
}
