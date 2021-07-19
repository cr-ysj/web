package com.example.demo.config.webSocket;

import io.jsonwebtoken.io.IOException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@ServerEndpoint(value = "/ws/WebSocketServer")
@Component
public class WebSocketServer {

    //在线连接
    private static final AtomicInteger OnlineCount = new AtomicInteger(0);
    private static final AtomicInteger temNum = new AtomicInteger(0);

    // concurrent包的线程安全Set，用来存放每个客户端对应的Session对象。
    private static CopyOnWriteArraySet<Session> SessionSet = new CopyOnWriteArraySet<Session>();

    @PostConstruct
    public void init() {
        log.info("---init---");
    }




    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        int cnt = OnlineCount.incrementAndGet(); // 在线数加1
        log.info("有连接加入，当前连接数为：{}", cnt);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        SessionSet.remove(session);
        int cnt = OnlineCount.decrementAndGet();
        log.info("有连接关闭，当前连接数为：{}", cnt);
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message
     *  客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("来自客户端的消息：{}",message);
        SendMessage(session, "收到消息，消息内容："+message);

    }

    /**
     * 出现错误
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误：{}，Session ID： {}",error.getMessage(),session.getId());
    }


    private static List<String> arrays=Arrays.asList(new String[]{"5, 20, 36, 10, 10, 20"
    ,"5, 15, 36, 20, 10, 30",
    "25, 25, 16, 30, 10, 25","5, 25, 16, 30, 10, 2","51, 45, 26, 10, 20, 21"});

    /**
     * 发送消息，实践表明，每次浏览器刷新，session会发生变化。
     * @param session
     * @param message
     */
    public static void SendMessage(Session session, String message) {
        try {
            RemoteEndpoint.Basic basicRemote = session.getBasicRemote();
            while (true){
                arrays.get(temNum.get());
                basicRemote.sendText(arrays.get(temNum.incrementAndGet()));
                temNum.incrementAndGet();
                if(temNum.get()==4){
                    temNum.set(0);
                }
                Thread.sleep(1000*10);
            }
        } catch (Exception  e) {
            log.error("发送消息出错：{}", e.getMessage());
        }
    }
}
