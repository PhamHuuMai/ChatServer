package mta.is.maiph.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author MaiPH
 */
@Getter
@Setter
@AllArgsConstructor
@ToString

public class LoginResponse {

    String token;
    String email;
    
}
