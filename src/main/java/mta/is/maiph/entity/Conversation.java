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
@Document(collection = "conversation")
public class Conversation {
    @Id
    String id;
    
    @Field(value = "name")
    String name;
    
    @Field(value = "admin_id")
    String adminId;
    
    @Field(value = "members")
    List<String> members;
    
    @Field(value = "last_chat_value")
    String lastChat;
    
    @Field(value = "last_time_action")
    String lastTimeAction;
    
    @Field(value = "create_time")
    String createTime;
    
    @Field(value = "num_member")
    int numMember;
    
    @Field(value = "is_group")
    boolean isGroup;
}
