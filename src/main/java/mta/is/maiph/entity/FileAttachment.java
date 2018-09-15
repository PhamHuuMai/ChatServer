package mta.is.maiph.entity;

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
public class FileAttachment extends File{
    String cvsId;
    boolean status = false;

    public FileAttachment(String id, String userId, String originalFileName, String mimeType, String url, String time,String csvId) {
        super(id, userId, originalFileName, mimeType, url, time);
        this.cvsId = csvId;
    }
            
}
