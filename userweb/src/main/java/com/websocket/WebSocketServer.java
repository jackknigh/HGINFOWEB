//package com.websocket;
//
//import com.utils.sys.LocalDateTimeUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import javax.websocket.*;
//import javax.websocket.server.PathParam;
//import javax.websocket.server.ServerEndpoint;
//import java.io.IOException;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * Created by CuiL on 2019-03-03.
// */
//@ServerEndpoint("/websocket/{sid}")
//@Component
//public class WebSocketServer {
//    private static Logger log = LoggerFactory.getLogger(WebSocketServer.class);
//    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
//    private static int onlineCount = 0;
//    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
////    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();
//    private static ConcurrentHashMap<String, WebSocketServer> webSocketSet = new ConcurrentHashMap<String, WebSocketServer>();
//
//    //与某个客户端的连接会话，需要通过它来给客户端发送数据
//    private Session session;
//    //登陆用户的sid，用户编号
//    private String sid = "";
//
//    /**
//     * 连接建立成功调用的方法
//     */
//    @OnOpen
//    public void onOpen(Session session, @PathParam("sid") String sid) {
//        this.session = session;
//        webSocketSet.put(sid, this);     //加入set中
//        addOnlineCount();           //在线数加1
//        log.info("有新窗口开始监听:" + sid + ",当前在线人数为" + getOnlineCount());
//        this.sid = sid;
//        try {
//            sendMessage(sid,"连接成功");
//        } catch (IOException e) {
//            log.error("websocket IO异常");
//        }
//    }
//
//    /**
//     * 连接关闭调用的方法
//     */
//    @OnClose
//    public void onClose() {
//        webSocketSet.remove(this.sid);  //从map中删除
//        subOnlineCount();           //在线数减1
//        log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
//    }
//
//    /**
//     * 收到客户端消息后调用的方法，主要是用户间聊天以及上传消息
//     *
//     * @param message 客户端发送过来的消息
//     *                客户端发送给服务端消息的格式是  tousername##to##message，分隔以后第一个就是要发送的用户名，第二个是消息
//     *                服务端发送给客户端消息的格式是  sendusername##to##时间戳##消息，分隔以后第一个就是发送的用户名，第二个是时间
//     */
//    @OnMessage
//    public void onMessage(String message, Session session) {
//        log.info("收到来自窗口" + sid + "的信息:" + message);
//        String tem[] = message.split("##to##");
//        //群发消息
//        if (tem.length > 1) {
//            try {
//                if (webSocketSet.containsKey(tem[0])) {
//                    webSocketSet.get(tem[0]).sendMessage(sid,tem[1]);
//                } else {
//                    webSocketSet.get(sid).sendMessage(sid,"用户不在线" );
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    /**
//     * @param session
//     * @param error
//     */
//    @OnError
//    public void onError(Session session, Throwable error) {
//        log.error("发生错误");
//        error.printStackTrace();
//    }
//
//    /**
//     * 实现服务器主动推送
//     */
//    private void sendMessage(String fromSid,String message) throws IOException {
//        if (session == null) {
//            return;
//        }
//        this.session.getBasicRemote().sendText(fromSid + "##to##" + LocalDateTimeUtils.getNow() + "##" + message);
//    }
//
//
//    /***
//     *<p>方法:sendInfo 群发或单发自定义消息，主要是后台主动发起</p>
//     *<ul>
//     *<li> @param message 消息
//     * 客户端发送给服务端消息的格式是  tousername##to##message，分隔以后第一个就是要发送的用户名，第二个是消息
//     * 服务端发送给客户端消息的格式是  sendusername##to##时间戳##消息，分隔以后第一个就是发送的用户名，第二个是时间</li>
//     *<li> @param 单发sid=昵称 群发sid=null</li>
//     *<li>@return void  </li>
//     *<li>@author CuiLiang </li>
//     *<li>@date 2019-03-03 15:20  </li>
//     *</ul>
//     */
//    public static void sendInfo(String message, @PathParam("sid") String sid) {
//        log.info("推送消息到窗口" + sid + "，推送内容:" + message);
//
//        if (sid == null) {
//            //群发
//            webSocketSet.forEach((_sid, session) -> {
//                session.sendInfo(message, _sid);
//            });
//        } else {
//            try {
//                webSocketSet.get(sid).sendMessage("system",message);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }
//
//    public static synchronized int getOnlineCount() {
//        return onlineCount;
//    }
//
//    public static synchronized void addOnlineCount() {
//        WebSocketServer.onlineCount++;
//    }
//
//    public static synchronized void subOnlineCount() {
//        WebSocketServer.onlineCount--;
//    }
//}
//
