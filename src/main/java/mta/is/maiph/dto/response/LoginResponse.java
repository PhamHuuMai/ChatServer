package mta.is.maiph.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author MaiPH
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class LoginResponse {

    String token;
    String email;
    String userId;
    String name;
    String avatar;
}
