package com.sky.task;


import com.sky.websocket.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Component
@Slf4j
public class MyTask {

    @Autowired
    WebSocketServer webSocketServer;
    //task 类型需要用用@Component标注为一个组件
    //例如每5秒

    @Scheduled(cron="0 0/30 * * * ?")
    public void logMsg() {
        log.info("今天的日期:"+ LocalDateTime.now());
        webSocketServer.sendToAll();
    }
}
