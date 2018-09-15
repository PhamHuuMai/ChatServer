package mta.is.maiph.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author MaiPH
 */
@Setter
@Getter
@ToString
public class RenameRequest {
    String userId;
    String newName;
}
