package com.agji.controller;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by agji on 17/6/15.
 */
@ServerEndpoint(value = "/websocket")
@Component
public class WsController {
    //current connects
    private static int onlineCount = 0;

    //package concurrent's Thread-Safe Set, use for store every client's MyWebSocket object.
    private static CopyOnWriteArraySet<WsController> webSocketSet = new CopyOnWriteArraySet<WsController>();

    private Session session;

    /**
     * Connect Success
     **/
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);     //add to set
        addOnlineCount();           //online plus
        System.out.println("one more connect join in！current num of online is " + getOnlineCount());
        try {
            sendMessage("Welcome " + session.getId() + "！");
        } catch (IOException e) {
            System.out.println("IO Exception");
        }
    }

    /**
     *  Connect Close
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //delete from set
        subOnlineCount();           //online minus 1
        System.out.println("one connect closed！current num of online is " + getOnlineCount());
    }

    /**
     * receive message from client
     *
     * @param message message from client
     **/
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("from client " + session.getId() +"'s message :" + message);
        String sessionId = session.getId();
        //mass message
        for (WsController item : webSocketSet) {
            if (!sessionId.equals(item.session.getId())) {
                continue;
            }
            try {
                item.sendMessage(message + "------from " + session.getId() + "！");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Error
     * */
     @OnError
     public void onError(Session session, Throwable error) {
     System.out.println("error(s)");
     error.printStackTrace();
     }


     public void sendMessage(String message) throws IOException {
//     this.session.getBasicRemote().sendText(message);
     this.session.getAsyncRemote().sendText(message);
     }


     /**
      *custom mass message
      * */
    public static void sendInfo(String message) throws IOException {
        for (WsController item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                continue;
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WsController.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WsController.onlineCount--;
    }
}
