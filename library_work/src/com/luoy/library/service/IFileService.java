package com.luoy.library.service;

import com.mongodb.gridfs.GridFSDBFile;

import java.io.File;

/**
 * 文件上传，下载操作
 *
 * @author : ChenCong
 * @date : Created in 8:44 2018/3/6
 */
public interface IFileService {

    /**
     * 上传文件，返回文件在MongoDB中的objectId
     *
     * @param file file
     * @return 上传成功返回objectId, 失败或文件file不存在返回null
     */
    String upload(File file);

    /**
     * 以新名字的上传文件
     *
     * @param file    文件
     * @param newName 文件的新名字
     * @return 上传成功返回objectId, 失败或文件file不存在返回null
     */
    String upload(String newName, File file);

    /**
     * 通过objectId删除对应文件
     *
     * @param objectId objectId
     */
    void deleteByObjectId(String objectId);

    /**
     * 通过objectId返回一个GridFSDBFile流作为图片显示用
     *
     * @param objectId objectId
     * @return 返回GridFSDBFile
     */
    GridFSDBFile getFileByObjectId(String objectId);

    /**
     * 下载文件到本地
     *
     * @param objectId 文件对应的objectId
     * @param filename 本地目标文件名
     */
    void downloadFile(String objectId, String filename);
}
