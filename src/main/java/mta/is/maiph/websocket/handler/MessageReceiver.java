package mta.is.maiph.websocket.handler;

import java.util.List;
import mta.is.maiph.DAO.impl.ConversationDAO;
import mta.is.maiph.DAO.impl.MessageDAO;
import mta.is.maiph.DAO.impl.UnreadMsgDAO;
import mta.is.maiph.dto.connection.Message;
import mta.is.maiph.session.SessionManager;
import mta.is.maiph.websocket.entrypointchat.ReccieveMessageEntryPoint;
import mta.is.maiph.websocket.session.WebsocketSessionManager;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 *
 * @author MaiPH
 */
public class MessageReceiver extends TextWebSocketHandler {

    static int i = 1;

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        WebsocketSessionManager.remove(session.getId());
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);

    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        super.handleTransportError(session, exception);
        WebsocketSessionManager.remove(session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
//            super.handleTextMessage(session, message);
            String msg = message.getPayload();
            System.out.println(msg);
            JSONObject msgJson = (JSONObject) (new JSONParser()).parse(msg);
            Long msgType = (Long) msgJson.get("msg_type");
            if (msgType == 0) {
                String token = (String) msgJson.get("token");
                String userId = SessionManager.check(token);
                WebsocketSessionManager.add(userId, session);
            } else if (msgType == 1) {
                String userId = WebsocketSessionManager.getUserId(session.getId());
                String toConversation = (String) msgJson.get("to");
                String value = (String) msgJson.get("value");
                Message msgDTO = new Message(userId, toConversation, msgType.intValue(), value);
                ReccieveMessageEntryPoint.add(msgDTO);
                (new MessageDAO()).add(toConversation, userId, value);
                List<String> mems = (new ConversationDAO()).getListMem(toConversation);
                mems.remove(userId);
                for (String mem : mems) {
                    (new UnreadMsgDAO()).incUnread(mem, toConversation);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
