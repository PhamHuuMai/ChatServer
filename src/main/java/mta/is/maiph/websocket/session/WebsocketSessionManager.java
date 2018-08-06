package mta.is.maiph.websocket.session;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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
    private static final HashMap<String, List<SocketConnection>> pool1 = new HashMap<>();
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
                    if (sessionId.equals(t.getSessionId())) {
                        return true;
                    }
                    return false;
                }
            });
            pool2.remove(sessionId);
        }
    }

    public static void add(String userId, WebSocketSession newSession) {
        if (pool1.containsKey(userId)) {
            String sesionId = newSession.getId();
            pool1.get(userId).add(new SocketConnection(sesionId, newSession));
            pool2.put(sesionId, userId);
        } else {
            String sesionId = newSession.getId();
            pool1.put(userId, Arrays.asList(new SocketConnection(sesionId, newSession)));
            pool2.put(sesionId, userId);
        }
    }

    public static List<WebSocketSession> get(String userId) {
        List<SocketConnection> scs = pool1.get(userId);
        List<WebSocketSession> ws = new LinkedList<>();
        for (SocketConnection w : scs) {
            ws.add(w.getSession());
        }
        return ws;
    }

}
