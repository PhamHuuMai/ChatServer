package mta.is.maiph.websocket.handler;

import java.util.List;
import mta.is.maiph.DAO.impl.ConversationDAO;
import mta.is.maiph.DAO.impl.FileAttachmentDAO;
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
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 *
 * @author MaiPH
 */
public class MessageReceiver extends TextWebSocketHandler {
    
    static int i = 1;
    private static MessageDAO msgDAO = new MessageDAO();
    private static UnreadMsgDAO unreadMsgDAO = new UnreadMsgDAO();
    private static ConversationDAO cvsDAO = ConversationDAO.instance();
    private static FileAttachmentDAO fileAttDAO = new FileAttachmentDAO();
    
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
                String userId = SessionManager.instance().check(token);
                WebsocketSessionManager.add(userId, session);
            } else if (msgType == 1) {   // text msg
                String userId = WebsocketSessionManager.getUserId(session.getId());
                String toConversation = (String) msgJson.get("to");
                String value = (String) msgJson.get("value");
                Message msgDTO = new Message(userId, toConversation, msgType.intValue(), value);
                ReccieveMessageEntryPoint.instance().add(msgDTO);
                msgDAO.add(toConversation, userId, value,msgType.intValue());
                //
                cvsDAO.updateLastMsg(toConversation, value);
                //
                List<String> mems = cvsDAO.getListMem(toConversation);
                mems.remove(userId);
                for (String mem : mems) {
                    unreadMsgDAO.incUnread(mem, toConversation);
                }
            } else if (msgType == 2) {  // typing
                String userId = WebsocketSessionManager.getUserId(session.getId());
                String toConversation = (String) msgJson.get("to");
                Message msgDTO = new Message(userId, toConversation, msgType.intValue(), "");
                ReccieveMessageEntryPoint.instance().add(msgDTO);
            } else if (msgType == 3) {  // file attchment msg 
                String userId = WebsocketSessionManager.getUserId(session.getId());
                String toConversation = (String) msgJson.get("to");
                String value = (String) msgJson.get("value");
                String fileId = (String) msgJson.get("file_id");
                Message msgDTO = new Message(userId, toConversation, msgType.intValue(), value);
                ReccieveMessageEntryPoint.instance().add(msgDTO);
                msgDAO.add(toConversation, userId, value,msgType.intValue());
                //
                cvsDAO.updateLastMsg(toConversation, value);
                //
                if (fileId != null) {
                    fileAttDAO.updateFlag(fileId);
                }
                List<String> mems = cvsDAO.getListMem(toConversation);
                mems.remove(userId);
                for (String mem : mems) {
                    unreadMsgDAO.incUnread(mem, toConversation);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
    
}
