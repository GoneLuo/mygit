package com.luoy.library.common.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author : ChenCong
 * @date : Created in 15:16 2018/2/6
 */
public class MyFileUpload {

    /**
     * 上传file
     *
     * @param file file
     * @param path path
     * @return 返回路径
     */
    public String upload(MultipartFile file, String path) {
        /*得到上传文件原始文件名称*/
        String fileName = file.getOriginalFilename();
        /*获取文件扩展名*/
        String fileExtensionName = fileName.substring(fileName.lastIndexOf("."));
        String uploadName = UUID.randomUUID().toString().replace("-", "") + fileExtensionName;

        /*判断文件file路径是否存在*/
        File fileDir = new File(path);
        if (!fileDir.exists()) {
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        /*待上传文件*/
        File targetFile = new File(path, uploadName);

        try {
            file.transferTo(targetFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return targetFile.getName();
    }
}
