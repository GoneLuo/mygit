package com.luoy.library.common.util;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import org.apache.logging.log4j.util.PropertiesUtil;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.UUID;

/**
 * MongoDB 系列操作封装
 *
 * @author : ChenCong
 * @date : Created in 14:28 2018/3/6
 */
public final class MongoDbUtil {

    private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

    private MongoDbUtil() {

    }

    private static final String HOST = "127.0.0.1";
    private static final Integer PORT = 27017;
    private static final String DATABASE = "library";

    /**
     * 获得MongoClient
     */
    private static class MongoInstance {
        public static final MongoClient CLIENT;

        static {
            CLIENT = new MongoClient(HOST, PORT);
        }
    }

    /**
     * destroy pool
     */
    public static void destroy() {
        MongoInstance.CLIENT.close();
    }

    /**
     * 得到MongoDatabase，database名称来源于mongodb.properties
     *
     * @return 返回mongoDatabase
     */
    public static MongoDatabase getDatabase() {
        return MongoInstance.CLIENT.getDatabase(DATABASE);
    }

    /**
     * 通过name得到MongoDatabase
     *
     * @param databaseName database 名称
     * @return 返回mongoDatabase
     */
    public static MongoDatabase getDatabase(String databaseName) {
        return MongoInstance.CLIENT.getDatabase(databaseName);
    }

    /**
     * upload file
     *
     * @param filename filename
     * @param in       InputStream
     * @return 返回objectId
     */
    private static String uploadFileToGridFS(String filename, InputStream in) {
        GridFSBucket bucket = GridFSBuckets.create(getDatabase());
        ObjectId objectId = bucket.uploadFromStream(filename, in);
        return objectId.toHexString();
    }

    /**
     * 上传文件到mongoDB，如果close为true，则关闭流
     *
     * @param filename 文件名
     * @param in       文件流
     * @param close    是否关闭文件流
     * @return 返回上传返回的objectID
     */
    private static String uploadFileTOGridFS(String filename, InputStream in, boolean close) {
        String returnId = null;
        try {
            returnId = uploadFileToGridFS(filename, in);
        } finally {
            if (close) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.info("关闭inputStream失败：", e.getMessage());
                }
            }
        }
        return returnId;
    }

    /**
     * 上传文件至mongoDB，成功则返回objectId，失败返回null
     *
     * @param filename 文件名
     * @param file     流
     * @return 成功返回objectId, 失败返回null
     */
    public static String uploadFileToGridFs(String filename, File file) {
        InputStream inputStream = null;
        String returnId = null;
        try {
            inputStream = new FileInputStream(file);
            returnId = uploadFileTOGridFS(filename, inputStream, true);
        } catch (FileNotFoundException e) {
            logger.info("上传文件失败：" + filename, e.getMessage());
        }
        return returnId;
    }

    /**
     * 上传文件至mongoDB，filename = file.getName()
     *
     * @param file file
     * @return 执行uploadFileToGridFs(String filename, File file)，返回objectId
     */
    public static String uploadFileToGridFs(File file) {
        return uploadFileToGridFs(file.getName(), file);
    }


    /**
     * 上传文件至mongoDB，文件名由UUID生成<br>
     * eg:dog.png => 385da8f3f0c44f919e0e993c864229be.png
     *
     * @param file 文件file
     * @return 返回objectId
     */
    public static String uploadFileToGridFSByUUID(File file) {
        if (!file.exists()) {
            return null;
        }
        String fileName = file.getName();
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
        String fileUUIDName = UUID.randomUUID().toString().replace("-", "") + "." + fileExtensionName;
        return uploadFileToGridFs(fileUUIDName, file);
    }

    /**
     * 文件下载
     *
     * @param objectId     文件所对应的id
     * @param outputStream 流
     */
    public static void downloadFile(String objectId, OutputStream outputStream) {
        GridFSBucket bucket = GridFSBuckets.create(getDatabase());
        bucket.downloadToStream(new ObjectId(objectId), outputStream);
    }

    /**
     * 通过objectId下载文件
     *
     * @param objectId objectId
     * @param file     下载到本地的文件
     */
    public static void downloadFile(String objectId, File file) {
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            downloadFile(objectId, outputStream);
        } catch (IOException e) {
            logger.info("文件下载失败：objectId:" + objectId, e.getMessage());
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    logger.info("文件流关闭失败 ", e.getMessage());
                }
            }
        }
    }


    /**
     * 通过objectId下载文件，可指定文件名
     *
     * @param objectId objectId
     * @param filename 指定下载文件在本地的文件名
     */
    public static void downloadFile(String objectId, String filename) {
        File file = new File(filename);
        downloadFile(objectId, file);
    }

    /**
     * 通过文件名下载<br>
     * 由于downloadToStreamByName(filename,outputStream)方法的过期，本方法也不推荐使用<br>
     *
     * @param filename     文件名
     * @param outputStream 流
     * @deprecated
     */
    public static void downloadFileByName(String filename, OutputStream outputStream) {
        GridFSBucket bucket = GridFSBuckets.create(getDatabase());
        bucket.downloadToStreamByName(filename, outputStream);
    }

    /**
     * 下载文件，通过stream <br>
     * 如果一次性读取所有字节，大于chunk size的可能会出现乱序，导致文件损
     *
     * @param objectId objectId
     * @param out      流
     */
    public static void downloadFileUseStream(String objectId, OutputStream out) {
        GridFSBucket bucket = GridFSBuckets.create(getDatabase());
        GridFSDownloadStream stream = null;
        try {
            stream = bucket.openDownloadStream(new ObjectId(objectId));
            /* gridfs file */
            GridFSFile file = stream.getGridFSFile();
            /* chunk size */
            int size = file.getChunkSize();
            int len = (int) file.getLength();
            /* loop time */
            int cnt = len / size + (len % size == 0 ? 0 : 1);
            byte[] bts = new byte[Math.min(len, size)];
            try {
                while (cnt-- > 0) {
                    int tmp = stream.read(bts);
                    out.write(bts, 0, tmp);
                }
                out.flush();
            } catch (IOException e) {
                logger.info("下载文件失败:" + objectId, e.getMessage());
            }
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }


    /**
     * 下载文件
     *
     * @param objectId objectId
     * @param file     文件
     */
    public static void downloadFileUseStream(String objectId, File file) {
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            downloadFileUseStream(objectId, outputStream);
        } catch (IOException e) {
            logger.info("下载文件失败:" + objectId, e.getMessage());
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    logger.info("关闭outputStream失败：objectId:" + objectId, e.getMessage());
                }
            }
        }
    }

    /**
     * 下载文件,指定文件名称。
     * 下载到项目根目录
     *
     * @param objectId objectId
     * @param fileName 文件名
     */
    public static void downloadFileUseStream(String objectId, String fileName) {
        File file = new File(fileName);
        downloadFileUseStream(objectId, file);
    }

    /**
     * 通过objectId删除文件
     *
     * @param objectId objectId
     */
    public static void deleteByObjectId(String objectId) {
        GridFSBucket bucket = GridFSBuckets.create(getDatabase());
        bucket.delete(new ObjectId(objectId));
    }

    /**
     * 通过objectId获取文件filename
     *
     * @param objectId objectId
     * @return 返回filename
     */
    public static String getFilenameFromGridFsByObjectId(String objectId) {
        Mongo mongo = new Mongo(HOST, PORT);
        DB db = mongo.getDB(DATABASE);
        GridFS gridFS = new GridFS(db, "fs");
        GridFSDBFile gridFSFile = gridFS.find(new ObjectId("5a9e53607c7b241bc4039334"));
        return gridFSFile.get("filename").toString();
    }

    /**
     * 根据objectId 获得 gridFSDBFile
     *
     * @param objectId objectId
     * @return 返回GridFSDBFile
     */
    public static GridFSDBFile getGridFSFileByObjectId(String objectId) {
        Mongo mongo = new Mongo(HOST, PORT);
        DB db = mongo.getDB(DATABASE);
        GridFS gridFS = new GridFS(db, "fs");
        GridFSDBFile gridFSFile = gridFS.find(new ObjectId(objectId));
        return gridFSFile;
    }
}


