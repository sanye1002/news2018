package cn.popo.news.core.utils;

import cn.popo.news.core.service.api.AgoPersonalService;
import cn.popo.news.core.service.api.IMService;
import cn.popo.news.core.service.api.impl.AgoPersonalServiceImpl;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/webSocket/{userId}")
@Component
public class IMUtil {

    @Autowired
    private AgoPersonalService agoPersonalService;
    @Autowired
    private IMService imService;

    private static IMUtil imUtil;

    @PostConstruct //通过@PostConstruct实现初始化bean之前进行的操作
    public void init() {
        imUtil = this;
        imUtil.agoPersonalService = this.agoPersonalService;
        imUtil.imService = this.imService;
        // 初使化时将已静态化的testService实例化
    }

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<IMUtil> webSocketSet = new CopyOnWriteArraySet<IMUtil>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    //定义一个全局变量map，key为用户名，该用户对应的session为value
    private static Map<String, Session> map = new HashMap<String, Session>();

    //定义一个全局变量map，key为用户名，该用户对应的是否在聊天页面为value，1：在，0：不在
    private static Map<String, String> mapIn = new HashMap<String, String>();
    //用户的id作为标识
    private String userId;

    //建立连接
    @OnOpen
    public void onOpen(@PathParam("userId") String userId, Session session) {
        System.out.println("open");
        this.session = session;
        this.userId = userId;
        //添加到webSocket中
        webSocketSet.add(this);
        //存在一个连接状态，userId为用户标识
        map.put(this.userId, this.session);
    }

    //关闭连接，移除所以标识
    @OnClose
    public void onClose() {
        System.out.println("close");
        webSocketSet.remove(this);
        map.remove(this.userId);
        mapIn.remove(this.userId);
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     * @param session ..
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println(123);
        JSONObject jsStr = JSONObject.parseObject(message);
        Integer heartCheck = jsStr.getInteger("heartCheck");
        if (heartCheck != null) {
            try {
                sendMessage("1");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            String toUserId = (String) jsStr.get("to");
            String type = jsStr.getString("type");
            //进入或退出聊天页面，0退出，1进入
            Integer in = jsStr.getInteger("in");
            if (in != null) {
                if (in == 0) {
                    mapIn.put(this.userId, "0");
                } else if (in == 1) {
                    mapIn.put(this.userId, toUserId);
                }
            } else {
                String msg = type + "~type~" + jsStr.getString("msg");
                try {
                    //是否单向发送
                    System.out.println(toUserId);
                    if (toUserId != null && !"".equals(toUserId)) {
                        sendToMessage(msg, toUserId);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


    }

    //发生错误
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("error");
        error.printStackTrace();
    }

    /**
     * 发送消息
     *
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }

    /**
     * 向对方发送消息
     *
     * @param message  发送的消息
     * @param toUserId 对方userId
     * @throws IOException
     */
    private void sendToMessage(String message, String toUserId) throws IOException {
        Session session = map.get(toUserId);
        String toToUserId = mapIn.get(toUserId);
        Integer state = 0;
        //判断对方是否在线
        if (session != null) {
            //判断对方是否在和我聊天的页面
            if (!this.userId.equals(toToUserId)) {
                //一个特殊字符串，表示对方在，但是没有在和我聊天页面
                System.out.println("hhhhhh");
                session.getBasicRemote().sendText(message + "~type~-/out*/`~type~" + this.userId);
            } else {
                //向客户端发送message
                session.getBasicRemote().sendText(message);
                state = 1;
            }

        } else {
            //未登录状态 未读+1
            imUtil.imService.addUnreadNum(toUserId, this.userId);
        }
        //存数据库
        List<String> strings = SplitUtil.splitMsg(message);
        imUtil.agoPersonalService.saveCommunication(
                toUserId, this.userId, strings.get(1), state, strings.get(0));

    }

    /*private void save(String message, String toUserId,Integer state,boolean flag,
                      String avatar,String nickname,Integer id){

        agoPersonalService.saveCommunication(toUserId, this.userId, message,state);
        if(flag){
            imService.save(this.userId,toUserId,avatar,nickname);
        }else {
            imService.updateLastTime(id);
        }

    }*/


}
