package com.ecommerce.chatmanager.websocket;

import com.ecommerce.chatmanager.domain.Msg;
import com.ecommerce.chatmanager.domain.enumeration.MessageType;
import com.ecommerce.chatmanager.service.dto.ClientDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private Logger logger = Logger.getLogger(WebSocketHandler.class.getName());

    private Map<ClientDTO, WebSocketSession> userSessions = new ConcurrentHashMap<>();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage msg) throws IOException {
        logger.info("Sending message: " + msg.getPayload() + " to " + userSessions.size() + " sessions.");
        String payload = msg.getPayload();
        ObjectMapper mapper = new ObjectMapper();
        Msg obj = mapper.readValue(payload, Msg.class);

        ClientDTO user = new ClientDTO();
        user.setId(obj.getFrom());
        user.setFname(obj.getFromUserName());

        if (obj.getType().equalsIgnoreCase(MessageType.JOINED.toString())) {
            logger.info(user.getFname() + " Joined the chat");
            userSessions.put(user, session);
        } else if (obj.getType().equalsIgnoreCase(MessageType.LEFT.toString())) {
            logger.info(user.getFname() + " Left the chat");
            userSessions.remove(user);
        }

        for (WebSocketSession webSocketSession : userSessions.values()) {
            webSocketSession.sendMessage(msg);
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        logger.info("Added Websocket session, total number of sessions are " + userSessions.size());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        logger.info("Removed Websocket session, total number of sessions are " + userSessions.size());
    }

    public Set<ClientDTO> getOnlineUsers() {
        return userSessions.keySet();
    }
}
