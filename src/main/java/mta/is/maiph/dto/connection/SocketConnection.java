package mta.is.maiph.dto.connection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;

/**
 *
 * @author MaiPH
 */
@Getter
@Setter
@AllArgsConstructor
public class SocketConnection {

    private String sessionId;
    private WebSocketSession session;
}
