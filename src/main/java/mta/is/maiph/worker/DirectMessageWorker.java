
package mta.is.maiph.worker;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import mta.is.maiph.DAO.impl.ConversationDAO;
import mta.is.maiph.dto.connection.Message;
import mta.is.maiph.entity.Conversation;
import mta.is.maiph.repository.ConversationRepository;
import mta.is.maiph.websocket.entrypointchat.ReccieveMessageEntryPoint;
import mta.is.maiph.websocket.session.WebsocketSessionManager;
import org.bson.types.ObjectId;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 *
 * @author MaiPH
 */
@Service
public class DirectMessageWorker extends Thread{
    
    private ConversationRepository conversationRepository;
    @Autowired
    public DirectMessageWorker(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }
    
    @Override
    public void run() {
       while(true){
           try{
           if(ReccieveMessageEntryPoint.isEmpty())
               continue;
           // get msg in send pool
           Message msg = ReccieveMessageEntryPoint.pool();
           // get to conversation
           String cvsId = msg.getToConversationId();
           System.out.println(cvsId);
           // get all member in conversation
           ConversationDAO cvtDAO = new ConversationDAO();
           // send to all member in conversation
           List<String> mem = cvtDAO.getListMem(cvsId);
           JSONObject json = new  JSONObject();
           json.put("msg_type",msg.getMessageType());
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
           }catch(Exception ex){
               ex.printStackTrace();
           }
           
       }
    }
    
}
