package mta.is.maiph.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

/**
 *
 * @author MaiPH
 */
@Setter
@Getter
@ToString
@FieldDefaults(level = AccessLevel.PROTECTED)
public class UploadFileRequest {
    String mimeType;
    String fileName;
    String file;
}
