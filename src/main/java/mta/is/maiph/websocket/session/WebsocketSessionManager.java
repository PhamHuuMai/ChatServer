package mta.is.maiph.websocket.session;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Predicate;
import mta.is.maiph.dto.connection.SocketConnection;
import org.springframework.web.socket.WebSocketSession;

/**
 *
 * @author MaiPH
 */
public class WebsocketSessionManager  {

    /*
    *   key: user_id
    *   value: List<SocketConnection>  
     */
    private static final HashMap<String, ConcurrentLinkedQueue<SocketConnection>> pool1 = new HashMap<>();
    /*
    *   key: sessionId
    *   value: user_id  
     */
    private static final HashMap<String, String> pool2 = new HashMap<>();

    public static void remove(String sessionId) {
        String userId = pool2.get(sessionId);
        if (userId != null) {
            pool1.get(userId).removeIf(new Predicate<SocketConnection>() {
                @Override
                public boolean test(SocketConnection t) {
                    return sessionId.equals(t.getSessionId());
         
                }
            });
            pool2.remove(sessionId);
        }
    }

    public static void add(String userId, WebSocketSession newSession) {
        if (pool1.containsKey(userId)) {
            String sesionId = newSession.getId();
            ConcurrentLinkedQueue<SocketConnection> lst = pool1.get(userId);
            System.out.println(lst);
            System.out.println(lst.getClass());
            lst.add(new SocketConnection(sesionId, newSession));
            pool2.put(sesionId, userId);
        } else {
            System.out.println("=============== " + userId);
            String sesionId = newSession.getId();
            ConcurrentLinkedQueue<SocketConnection> lst = new ConcurrentLinkedQueue<>();
            lst.add(new SocketConnection(sesionId, newSession));
            pool1.put(userId, lst);
            pool2.put(sesionId, userId);
//            List<SocketConnection> scs = pool1.get(userId);
//            System.out.println(scs);
        }
        
    }

    public static List<WebSocketSession> get(String userId) {
        ConcurrentLinkedQueue<SocketConnection> scs = pool1.get(userId);
        if(scs == null)
            scs = new ConcurrentLinkedQueue<SocketConnection>();
        List<WebSocketSession> ws = new LinkedList<>();
        for (SocketConnection w : scs) {
            ws.add(w.getSession());
        }
        return ws;
    }

    public static String getUserId(String sessionId){
       return (String)pool2.get(sessionId);
    }
}
