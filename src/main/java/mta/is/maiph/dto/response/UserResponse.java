
package mta.is.maiph.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mta.is.maiph.entity.User;

/**
 *
 * @author MaiPH
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserResponse {
    String userId;
    String userName;
    String lastLoginTIme;
    public UserResponse(User user){
       userId = user.getId();
       userName = user.getName();
       lastLoginTIme = user.getLastLogin();
    }
    
}
