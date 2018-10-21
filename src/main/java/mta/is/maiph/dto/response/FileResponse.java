package mta.is.maiph.dto.response;

import lombok.Builder;
import lombok.Data;

/**
 *
 * @author MaiPH
 */
@Data
@Builder
public class FileResponse {
    String userName;
    String url;
    String time;
    String originalFileName;
}
