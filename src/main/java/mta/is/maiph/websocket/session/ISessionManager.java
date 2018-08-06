package mta.is.maiph.websocket.session;

import java.util.List;
import org.springframework.web.socket.WebSocketSession;

/**
 *
 * @author MaiPH
 */
public interface ISessionManager {

    public void add(String userId, WebSocketSession newSession);

    public void remove(String sessionId);

    public List<WebSocketSession> get(String userId);
}
