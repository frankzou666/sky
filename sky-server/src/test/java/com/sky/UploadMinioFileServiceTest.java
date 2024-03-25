package com.sky;

import com.sky.service.UploadMinioFileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UploadMinioFileServiceTest {

    @Autowired
    UploadMinioFileService UploadMinioFileService;

    @Test
    public void uploadImgTest(){
        String localPath ="/Users/oliver/Downloads/S__13844516.jpg";
        System.out.println(UploadMinioFileService.uploadImage(localPath));


    }
}
