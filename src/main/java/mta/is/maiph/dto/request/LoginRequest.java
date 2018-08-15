package mta.is.maiph.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author MaiPH
 */
@Setter
@Getter
@ToString
public class LoginRequest {

    String email;
    String passwordMd5;

}
