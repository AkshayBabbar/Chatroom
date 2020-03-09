package edu.udacity.java.nano.chat;

// import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket Server
 *
 * @see ServerEndpoint WebSocket Client
 * @see Session WebSocket Session
 */

@Component
@ServerEndpoint("/chat")
public class WebSocketChatServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketChatServer.class);
    /**
     * All chat sessions.
     */
    private static Map<String, Session> onlineSessions = new ConcurrentHashMap<>();
    private ObjectMapper mapper = new ObjectMapper();

    private static void sendMessageToAll(String msg) {
        // TODO: add send message method.

        for (String entry : onlineSessions.keySet()) {
            final Session session = onlineSessions.get(entry);
            try {
                session.getBasicRemote().sendText(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Open connection, 1) add session, 2) add user.
     */
    @OnOpen
    public void onOpen(Session session) throws JsonProcessingException {
        // TODO: add on open connection.
        onlineSessions.put(session.getId(), session);
        Integer numberOfUser = onlineSessions.size();
        Message newMessage = new Message(numberOfUser.toString(), "");
        newMessage.setOnlineCount(numberOfUser);
        String message = mapper.writeValueAsString(newMessage);
        sendMessageToAll(message);

        // String message = JSON.toJSON(newMessage).toString();
    }

    /**
     * Send message, 1) get username and session, 2) send message to all.
     */
    @OnMessage
    public void onMessage(Session session, String jsonStr) throws IOException {
        // TODO: add send message.
        Message objMessage = mapper.readValue(jsonStr, Message.class);
        objMessage.setType(Message.MessageType.CHAT);
        String message = mapper.writeValueAsString(objMessage);
        sendMessageToAll(message);
    }

    /**
     * Close connection, 1) remove session, 2) update user.
     */
    @OnClose
    public void onClose(Session session) throws JsonProcessingException {
        // TODO: add close connection.

        try {
            onlineSessions.remove(session.getId());
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Print exception.
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

}
