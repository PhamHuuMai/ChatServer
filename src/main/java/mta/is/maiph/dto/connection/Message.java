package mta.is.maiph.dto.connection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author MaiPH
 */
@Getter
@Setter
@AllArgsConstructor

public class Message {
    String fromId;
    String toConversationId;
    int messageType;
    String messageValue;
}
