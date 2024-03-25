package com.sky.controller.admin;


import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.service.UploadMinioFileService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/admin/common")
@Slf4j
public class CommonController {

    @Autowired
    UploadMinioFileService UploadMinioFileService;

    @Autowired
    RedisTemplate<String, String> redisTemplate;


    @PostMapping("/upload")
    @ApiOperation("文件上传")
    public Result<String> upload(MultipartFile file) throws IOException {
        log.info("文件上传:{}",file);
        String fileName = file.getOriginalFilename();
        String prefix = fileName.substring(0,fileName.lastIndexOf("."));
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        File tempfile = File.createTempFile(prefix,suffix);
        file.transferTo(tempfile);
        String localFilePath = tempfile.getAbsolutePath();
        String url = UploadMinioFileService.uploadImage(localFilePath);
       if (url!=null) {
           return  Result.success(url);
       } else {
           return  Result.error(MessageConstant.UPLOAD_FAILED);
       }
    }


    @PostMapping("/redistest")
    public void redisTest() {
        redisTemplate.opsForValue().set("key","good1");

    }
}
