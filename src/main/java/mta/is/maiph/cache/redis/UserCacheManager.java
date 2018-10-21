package mta.is.maiph.cache.redis;

import java.util.HashMap;
import java.util.Map;
import mta.is.maiph.DAO.impl.UserDAO;
import mta.is.maiph.cache.BaseCache;
import mta.is.maiph.cache.UserCache;
import mta.is.maiph.constant.ErrorCode;
import mta.is.maiph.entity.User;
import mta.is.maiph.exception.ApplicationException;

/**
 *
 * @author MaiPH
 */
public class UserCacheManager extends BaseCacheManager {

    private static UserCacheManager instance;

    private UserDAO userDAO;

    private UserCacheManager() {
        super();
        userDAO = new UserDAO();
    }

    public static UserCacheManager instance() {
        if (instance == null) {
            instance = new UserCacheManager();
        }
        return instance;
    }

    @Override
    protected String getPrefix() {
        return "user";
    }

    @Override
    protected BaseCache castToEntity(Map<String, String> map) {
        UserCache userCache = UserCache.builder()
                .email(map.get("email"))
                .userName("user_name")
                .avatar(map.get("avatar"))
                .build();
        return userCache;
    }

    @Override
    protected Map<String, String> initFromDB(String id) throws ApplicationException {
        User user = userDAO.get(id);
        if (user == null) {
            throw new ApplicationException(ErrorCode.INVALID_ACCOUNT);
        }
        Map<String, String> result = new HashMap<>();
        result.put("email", user.getEmail());
        result.put("user_name", user.getName());
        result.put("avatar", user.getAvatarUrl());
        return result;
    }

}
