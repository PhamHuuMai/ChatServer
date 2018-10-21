package mta.is.maiph.cache;

import lombok.Builder;
import lombok.Data;

/**
 *
 * @author MaiPH
 */
@Data
@Builder
public class UserCache implements BaseCache {

    String userId;
    String userName;
    String email;
    String avatar;
}
