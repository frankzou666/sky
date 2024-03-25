package com.sky.service.impl;

import com.sky.service.UploadMinioFileService;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;

import java.io.File;
import java.io.FileInputStream;

@Service
@Slf4j
public class UploadMinioFileServiceImpl implements UploadMinioFileService {

    @Autowired
    MinioClient minioClient;

    @Value("${minio.endpoint}")
    private String endpoint;

    String bucket="mediafiles";
    @Override
    public String uploadImage(String localFilePath) {

        String url=null;
        File file =  new File(localFilePath);

        String extension = localFilePath.substring(localFilePath.lastIndexOf("."));

        //得到mimeType
        String mimeType = getMimeType(extension);
        //使用文件的md5值作为文件名上传到minio
        String objectName = getFileMd5(file)+extension;
        try {
            UploadObjectArgs uploadObjectArgs = UploadObjectArgs.builder()
                    .bucket(bucket)//桶
                    .filename(localFilePath) //指定本地文件路径
                    .object(objectName)//对象名 放在子目录下
                    .contentType(mimeType)
                    .build();
            //上传文件
            minioClient.uploadObject(uploadObjectArgs);
            log.debug("上传文件到minio成功,bucket:{},objectName:{},错误信息:{}",bucket,objectName);
            url = endpoint +"/"+bucket+"/"+objectName;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("上传文件出错,bucket:{},objectName:{},错误信息:{}",bucket,objectName,e.getMessage());
        }
        return url;

    }

    //得到文件的MD5
    private String getFileMd5(File file) {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            String fileMd5 = DigestUtils.md5Hex(fileInputStream);
            return fileMd5;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getMimeType(String extension){
        if(extension == null){
            extension = "";
        }
        //根据扩展名取出mimeType
        ContentInfo extensionMatch = ContentInfoUtil.findExtensionMatch(extension);
        String mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;//通用mimeType，字节流
        if(extensionMatch!=null){
            mimeType = extensionMatch.getMimeType();
        }
        return mimeType;

    }
}
