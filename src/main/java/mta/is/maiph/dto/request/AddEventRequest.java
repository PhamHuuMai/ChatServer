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
public class AddEventRequest {
    
    int piority;
    String title;
    String content;
    String time;
    String cvsId;
}
