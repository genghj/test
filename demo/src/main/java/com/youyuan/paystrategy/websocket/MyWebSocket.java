package com.youyuan.paystrategy.websocket;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.json.JsonParseException;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * websocket的具体实现类
 */
@ServerEndpoint(value = "/websocket/{nickname}")
@Component
public class MyWebSocket {
    //用来记录sessionId和该session进行绑定
    private static Map<String,Session> map = new HashMap<String, Session>();
    //用来存放每个客户端对应的MyWebSocket对象。  
    private static CopyOnWriteArraySet<MyWebSocket> webSocketSet = new CopyOnWriteArraySet<MyWebSocket>();
   
    //与某个客户端的连接会话，需要通过它来给客户端发送数据  
    private Session session;
   private String nickname;
    /** 
     * 连接建立成功调用的方法 
     */  
    @OnOpen
    public void onOpen(Session session,@PathParam("nickname") String nickname) {
        this.session = session;
        this.nickname = nickname;
        map.put(session.getId(), session);
        webSocketSet.add(this);     //加入set中  
        System.out.println("有新连接加入！当前在线人数为" + webSocketSet.size());
        this.session.getAsyncRemote().sendText("恭喜您成功连接上WebSocket-->当前在线人数为："+webSocketSet.size()+";"+this.nickname+"上线了（他的频道号是"+session.getId()+"）");
    }  
   
    /** 
     * 连接关闭调用的方法 
     */  
    @OnClose
    public void onClose() {  
        webSocketSet.remove(this);  //从set中删除  
        System.out.println("有一连接关闭！当前在线人数为" + webSocketSet.size());
    }  
   
    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
//    @OnMessage
//    public void onMessage(String message, Session session) {
//        System.out.println("来自客户端的消息:" + message);
//        //群发消息
//        broadcast(message);
//    }
    @OnMessage
    public void onMessage(String message, Session session,@PathParam("nickname") String nickname) {
        System.out.println("来自客户端的消息:" + message);

        ObjectMapper objectMapper = new ObjectMapper();
        SocketMsg socketMsg;
        try {
            socketMsg = objectMapper.readValue(message, SocketMsg.class);
            if(socketMsg.getType() == 1){
                //单聊.需要找到发送者和接受者.

                socketMsg.setFromUser(session.getId());//发送者.
                Session fromSession = map.get(socketMsg.getFromUser());
                Session toSession = map.get(socketMsg.getToUser());
                //发送给接受者.
                if(toSession != null){
                    //发送给发送者.
                    fromSession.getAsyncRemote().sendText(nickname+"："+socketMsg.getMsg());
                    toSession.getAsyncRemote().sendText(nickname+"："+socketMsg.getMsg());
                }else{
                    //发送给发送者.
                    fromSession.getAsyncRemote().sendText("系统消息：对方不在线或者您输入的频道号不对");
                }
            }else{
                //群发消息
                broadcast(nickname,socketMsg);
            }
        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /** 
     * 发生错误时调用 
     * 
     */  
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();  
    }  
   
    /** 
     * 群发自定义消息 
     * */  
    public  void broadcast(String message, SocketMsg socketMsg){
        for (MyWebSocket item : webSocketSet) {  
               //同步异步说明参考：http://blog.csdn.net/who_is_xiaoming/article/details/53287691  
               //this.session.getBasicRemote().sendText(message);  
               item.session.getAsyncRemote().sendText(nickname+"："+socketMsg.getMsg());//异步发送消息.
        }  
    }  
} 