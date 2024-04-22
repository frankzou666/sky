package com.sky.websocket;


import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
//表示客户连接的地址为(ws://host/ws/sid
//这个sid是客户端的连接
@ServerEndpoint("/ws/{sid}")
public class WebSocketServer {

    public  static  Map<String,Session> sessionMap = new HashMap<>();

    //客户端建立连接
    @OnOpen
    public void open(Session session, @PathParam("sid") String sid) {
        sessionMap.put(sid,session);
        System.out.println("客户端建立连接成功:"+session.getBasicRemote());

    }

    @OnMessage
    public void reciverMsg(String msg,Session session) {
        System.out.println(msg);
    }

    public void sendToAll() {

        for(Session session:sessionMap.values()) {
            try{
                String msgObj;
                Map<String ,String> mapObj = new HashMap<>();
                mapObj.put("data","来自服务器的消息:当前时间:"+ LocalDateTime.now());
                msgObj= JSON.toJSONString(mapObj);
                session.getBasicRemote().sendText(msgObj);

            } catch (Exception ex) {

            }

        }
    }
}
