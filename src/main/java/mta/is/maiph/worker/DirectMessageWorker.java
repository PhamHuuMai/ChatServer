
package mta.is.maiph.worker;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mta.is.maiph.dto.connection.Message;
import mta.is.maiph.entity.Conversation;
import mta.is.maiph.repository.ConversationRepository;
import mta.is.maiph.websocket.entrypointchat.ReccieveMessageEntryPoint;
import mta.is.maiph.websocket.session.WebsocketSessionManager;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 *
 * @author MaiPH
 */
public class DirectMessageWorker extends Thread{
    @Autowired
    private ConversationRepository conversationRepository;
    @Override
    public void run() {
       while(true){
           // get msg in send pool
           Message msg = ReccieveMessageEntryPoint.pool();
           // get to conversation
           String cvsId = msg.getToConversationId();
           // get all member in conversation
           Conversation cvs = conversationRepository.findById(cvsId).get();
           // send to all member in conversation
           List<String> mem = cvs.getMembers();
           mem.remove(msg.getFromId());
           JSONObject json = new  JSONObject();
           json.put("value",msg.getMessageValue());
           json.put("to",cvsId);
           json.put("from",msg.getFromId());
           for (String id : mem) {
               List<WebSocketSession> des = WebsocketSessionManager.get(id);
               for (WebSocketSession de : des) {
                   try {
                       de.sendMessage(new TextMessage(json.toJSONString()));
                   } catch (IOException ex) {
                       
                   }
               }
           }
           
           
       }
    }
    
}
