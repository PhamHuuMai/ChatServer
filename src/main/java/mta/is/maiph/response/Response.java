package mta.is.maiph.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author MaiPH
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Response {

    int code;
    Object data;

    public Response(int code) {
        this.code = code;
    }
}
