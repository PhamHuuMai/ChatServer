package mta.is.maiph.websocket.handler;

import mta.is.maiph.websocket.session.WebsocketSessionManager;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 *
 * @author MaiPH
 */

public class MessageReceiver extends TextWebSocketHandler{

    static int i =1;
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
//        WebsocketSessionManager.remove(session.getId());
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        super.handleTransportError(session, exception);
//        WebsocketSessionManager.remove(session.getId());
    }

    
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message); 
        System.out.println(message.getPayload());
        i++;
        session.sendMessage(new TextMessage(message.getPayload()+i)); 
    }

    
}
