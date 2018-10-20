package mta.is.maiph.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
public class EventResponse {

    String id;
    String title;
    String content;
    int piority;
    String createTime;
    String actionTime;
    String userName;
    String cvsId;
}
