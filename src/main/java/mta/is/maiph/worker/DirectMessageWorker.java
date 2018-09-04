package mta.is.maiph.worker;

import java.io.IOException;
import java.util.List;
import mta.is.maiph.DAO.impl.ConversationDAO;
import mta.is.maiph.DAO.impl.UserDAO;
import mta.is.maiph.dto.connection.Message;
import mta.is.maiph.websocket.entrypointchat.ReccieveMessageEntryPoint;
import mta.is.maiph.websocket.session.WebsocketSessionManager;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 *
 * @author MaiPH
 */
@Service
public class DirectMessageWorker extends Thread {

    private UserDAO userDAO = new UserDAO();

    @Override
    public void run() {
        while (true) {
            try {
                if (ReccieveMessageEntryPoint.instance().isEmpty()) {
                    continue;
                }
                // get msg in send pool
                Message msg = ReccieveMessageEntryPoint.instance().pool();
                // get to conversation
                String cvsId = msg.getToConversationId();
                String fromId = msg.getFromId();
                String fromUsername = userDAO.getUserNameById(fromId);
                // get all member in conversation
                ConversationDAO cvtDAO = ConversationDAO.instance();
                // send to all member in conversation
                List<String> mem = cvtDAO.getListMem(cvsId);
                JSONObject json = new JSONObject();
                json.put("msg_type", msg.getMessageType());
                json.put("value", msg.getMessageValue());
                json.put("to", cvsId);
                json.put("from", msg.getFromId());
                json.put("name", fromUsername);
                for (String id : mem) {
                    List<WebSocketSession> des = WebsocketSessionManager.get(id);
                    for (WebSocketSession de : des) {
                        try {
                            de.sendMessage(new TextMessage(json.toJSONString()));
                        } catch (IOException ex) {

                        }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    }

}
