package cn.popo.news.core.utils;

import cn.popo.news.core.service.api.AgoPersonalService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value="/webSocket/{userId}")
@Component
public class IMUtil {


    @Autowired
    private AgoPersonalService agoPersonalService;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<IMUtil> webSocketSet = new CopyOnWriteArraySet<IMUtil>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    //定义一个全局变量map，key为用户名，该用户对应的session为value
    private  static Map<String, Session> map = new HashMap<String, Session>();

    //定义一个全局变量map，key为用户名，该用户对应的是否在聊天页面为value，1：在，0：不在
    private  static Map<String, String> mapIn = new HashMap<String, String>();
    //用户的id作为标识
    private String userId;

    //建立连接
    @OnOpen
    public void onOpen(@PathParam("userId") String userId, Session session){
        this.session = session;
        this.userId = userId;
        //添加到webSocket中
        webSocketSet.add(this);
        //存在一个连接状态，userId为用户标识
        map.put(this.userId,this.session);
    }

    //关闭连接，移除所以标识
    @OnClose
    public void onClose(){
        webSocketSet.remove(this);
        map.remove(this.userId);
        mapIn.remove(this.userId);
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session ..
     */
    @OnMessage
    public void onMessage(String message,Session session){
        JSONObject jsStr = JSONObject.parseObject(message);
        String toUserId = (String) jsStr.get("to");
        //进入或退出聊天页面，0退出，1进入
        String inUserId = (String) jsStr.get("in");
        if("0".equals(inUserId)){
            mapIn.put(this.userId,"0");
        }else if("1".equals(inUserId)){
            mapIn.put(this.userId,toUserId);
        }else{
            String msg = (String) jsStr.get("msg");
            try {
                //是否单向发送
                if(!toUserId.equals("")){
                    sendToMessage(msg,toUserId);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    //发生错误
    @OnError
    public void onError(Session session, Throwable error){
        error.printStackTrace();
    }

    /**
     * 发送消息
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }

    /**
     * 向对方发送消息
     * @param message 发送的消息
     * @param toUserId 对方userId
     * @throws IOException
     */
    private void sendToMessage(String message, String toUserId) throws IOException {
        Session session = map.get(toUserId);

        String toToUserId = mapIn.get(toUserId);
        Integer state = 0;
        //判断对方是否在线
        if(session!=null){
            //判断对方是否在和我聊天的页面
            if(!this.userId.equals(toToUserId)){
                //一个特殊字符串，表示对方在，但是没有在和我聊天页面
                session.getBasicRemote().sendText("-/out*/`");
                //信息状态为未读
                state = 0;
            }else {
                //向客户端发送message
                session.getBasicRemote().sendText(message);
                //信息状态为已读
                state = 1;
            }

        }
        //存数据库
//        agoPersonalService.saveCommunication(toUserId, this.userId, message,state);

    }





}
