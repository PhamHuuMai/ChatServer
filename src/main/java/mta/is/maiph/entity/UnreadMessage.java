package mta.is.maiph.entity;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 *
 * @author MaiPH
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "unread_msg")
public class UnreadMessage {
    @Id
    String id;
    
    @Field(value = "user_id")
    String userId;
    
    @Field(value = "conversation_id")
    String conversationId;
    
    @Field(value = "conversation_name")
    String conversationName;
    
    @Field(value = "num_unread")
    int numUnread;
    
}
