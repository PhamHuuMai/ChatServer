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
public class UploadFileRequest {
    String mimeType;
    String fileName;
    String file;
}
