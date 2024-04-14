package com.sky.task;


import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Component
@Slf4j
public class MyTask {
    //task 类型需要用用@Component标注为一个组件
    //例如每5秒
    @Scheduled(cron="0 30 * * * ?")
    public void logMsg() {
        log.info("今天的日期:"+ LocalDateTime.now());
    }
}
