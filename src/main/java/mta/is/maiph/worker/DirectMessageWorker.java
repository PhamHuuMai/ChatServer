
package mta.is.maiph.worker;

import mta.is.maiph.dto.connection.Message;
import mta.is.maiph.websocket.entrypointchat.ReccieveMessageEntryPoint;

/**
 *
 * @author MaiPH
 */
public class DirectMessageWorker implements Runnable{

    @Override
    public void run() {
       while(true){
           // get msg in send pool
           Message msg = ReccieveMessageEntryPoint.pool();
           // get to conversation
           String cvsId = msg.getToConversationId();
           // get all member in conversation
           
           // send to all member in conversation
           
       }
    }
    
}
