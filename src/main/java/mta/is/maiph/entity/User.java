package mta.is.maiph.entity;

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
@Document(collection = "user")
public class User {
    @Id
    String id;
    @Field(value = "name")
    String name;
    @Field(value = "email")
    String email;
    @Field(value = "original_password")
    String originalPassword;
    @Field(value = "password")
    String password;
    @Field(value = "last_time_login")
    String lastLogin;
}
