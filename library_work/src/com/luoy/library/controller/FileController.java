package com.luoy.library.controller;


import com.luoy.library.common.util.MyFileUpload;
import com.luoy.library.service.IFileService;
import com.mongodb.gridfs.GridFSDBFile;

import org.apache.commons.io.FileUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 将MongoDB返回的图片，以流的形式到前台。<br>
 * 提供图片上传、下载等功能<br>
 * 提供附件下载<br>
 *
 * @author : ChenCong
 * @date : Created in 8:36 2018/3/6
 */
@Controller
@RequestMapping(value = {"/file", "/upload"})
public class FileController {


    private static final int BYTE_SIZE = 2048;
    private static final int BYTE_FIRST = -1;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IFileService fileService;
    

    /**
     * 图片显示。通过objectId返回图片流。<br>
     * 用户头像 等级logo等地方均由此方法放回图片流。
     *
     * @param objectId objectId
     * @param response response
     * @throws IOException IOException
     */
    @RequestMapping("/image/{objectId}")
    public void showImage(@PathVariable("objectId") String objectId,
                          HttpServletResponse response) {
        try {
            outputStream(objectId, response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 文件上传
     *
     * @param file    file
     * @param request request
     * @return 文件路径
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public String upload(
            @RequestParam(value = "upload_file", required = false) MultipartFile file,
            HttpServletRequest request) {
        /*获得MultipartHttpRequest*/
        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
        String imageName = multiRequest.getParameter("imageName");
        String path = request.getSession().getServletContext().getRealPath("upload");
        /*上传文件到Tomcat服务器上*/
        String targetName = new MyFileUpload().upload(file, path);
        logger.info("file: " + path + "/" + targetName);
        /*将文件从服务器上上传到mongoDB*/
        String fileName = path + "/" + targetName;
        File fileItem = new File(fileName);
        String objectId = fileService.upload(fileItem);
        /*删除Tomcat服务器上图片*/
        fileItem.delete();

        return objectId;
    }

    /**
     * 向页面输出流的方法
     *
     * @param objectId objectId
     * @param response response
     * @throws IOException IOException
     */
    private void outputStream(String objectId, HttpServletResponse response) throws IOException {
        GridFSDBFile file = fileService.getFileByObjectId(objectId);
        InputStream inputStream = file.getInputStream();
        String fileName = (String) file.get("filename");
        response.setContentType(file.getContentType());
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        OutputStream outputStream = response.getOutputStream();
        byte[] buffer = new byte[BYTE_SIZE];
        int i = BYTE_FIRST;
        while ((i = inputStream.read(buffer)) != BYTE_FIRST) {
            outputStream.write(buffer, 0, i);
        }
        inputStream.close();
        outputStream.flush();
        outputStream.close();
    }
}
