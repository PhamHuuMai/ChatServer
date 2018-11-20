package mta.is.maiph.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import mta.is.maiph.entity.User;
import mta.is.maiph.util.Util;

/**
 *
 * @author MaiPH
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserResponse {

    String userId;
    String userName;
    String avatar;
    String lastLoginTIme;

    public UserResponse(User user) {
        userId = user.getId();
        userName = user.getName();
        lastLoginTIme = timeStringFormat(user.getLastLogin());
        avatar = user.getAvatarUrl();
    }

    private String timeStringFormat(String lastLoginStr) {
        lastLoginStr = lastLoginStr +"00000000000000000";
        String prefix = "Online ";
        long lastlogin = Util.format_yyyyMMddhhmmss(lastLoginStr);
        long cur = System.currentTimeMillis();
        return prefix + Util.getLastOnline(cur - lastlogin);
    }
}
