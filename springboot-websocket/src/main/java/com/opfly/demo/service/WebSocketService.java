package com.opfly.demo.service;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opfly.demo.pojo.MessageInfo;

@Service
@ServerEndpoint("/ws/{clientId}")
public class WebSocketService {
	//当前在线人数
    private static int onlineCount = 0;
    private static ConcurrentHashMap<String, WebSocketService> clients = new ConcurrentHashMap<>();
    private String clientId;
    private Session session;
    
    @OnOpen
    public void onOpen(@PathParam("clientId") String clientId, Session session) {
    	this.session = session;
    	this.clientId = clientId;
        if (clients.containsKey(clientId)) {
        	clients.remove(clientId);
        	clients.put(clientId, this);
        } else {
        	clients.put(clientId, this);
            addOnlineCount();
        }

        try {
        	sendServerMessage("服务器连接成功");
        } catch (IOException e) {
        	e.printStackTrace();
        }
    }

    @OnClose
    public void onClose(Session session) {
        if(clients.containsKey(clientId)){
        	clients.remove(clientId);
            subOnlineCount();
        }
    }
    
    /**
     * 转发消息，将客户端发送的消息转发给目标客户端
     * @param message
     * @param session
     */
    @OnMessage
    public void onMessage(String message, Session session) {
    	ObjectMapper objectMapper = new ObjectMapper();
    	MessageInfo messageInfo = null;
    	try {
    		messageInfo = objectMapper.readValue(message, MessageInfo.class);
    		
    		String targetClientId = messageInfo.getTargetClientId();
        	if (!clients.containsKey(targetClientId)) {
        		sendServerMessage("用户：" + targetClientId + " 不在线");
        	}
        	else {
        		clients.get(targetClientId).session.getBasicRemote().sendText(message);
        	}
    	}catch (Exception e) {
		}
    }

    @OnError
    public void onError(Session session, Throwable error) {
    	System.out.println("发生错误");
    }

    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }
    
    /**
     * 封装服务器消息
     * @param content 消息内容
     * @throws IOException
     */
    public void sendServerMessage(String content) throws IOException {
        try {
        	MessageInfo messageInfo = new MessageInfo();
        	messageInfo.setSourceClientId("服务器");
        	messageInfo.setTargetClientId(this.clientId);
        	messageInfo.setType("chat");
        	messageInfo.setContent(content);
        	ObjectMapper objectMapper = new ObjectMapper();
        	String message = objectMapper.writeValueAsString(messageInfo);
			this.session.getBasicRemote().sendText(message);;
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketService.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
    	WebSocketService.onlineCount--;
    }
}
