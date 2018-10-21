package mta.is.maiph.service.impl;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import mta.is.maiph.DAO.impl.FriendDAO;
import mta.is.maiph.DAO.impl.UserDAO;
import mta.is.maiph.cache.UserCache;
import mta.is.maiph.cache.redis.UserCacheManager;
import mta.is.maiph.constant.ErrorCode;
import mta.is.maiph.entity.User;
import mta.is.maiph.exception.ApplicationException;
import mta.is.maiph.repository.UserRepository;
import mta.is.maiph.service.UserService;
import mta.is.maiph.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author MaiPH
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserRepository userRepository;
    private final UserDAO userDAO = new UserDAO();
    private final FriendDAO friendDAO = new FriendDAO();
    
    @Override
    public User login(String email, String password) throws ApplicationException {
        User user = userRepository.findByEmailAndPassword(email, password);
        if (user == null) {
            throw new ApplicationException(ErrorCode.INVALID_ACCOUNT);
        }
        return user;
    }
    
    @Override
    public User register(User xUser) throws ApplicationException {
        User user = userRepository.findByEmail(xUser.getEmail());
        if (user != null) {
            throw new ApplicationException(ErrorCode.INVALID_ACCOUNT);
        }
        String originalPass = xUser.getPassword();
        String password = Util.MD5(originalPass);
        user = new User(null,
                xUser.getName(),
                xUser.getEmail(),
                originalPass, password,
                Util.currentTIme_yyyyMMddhhmmss(),
                "/2.png"
        );
        User result = userRepository.insert(user);
        return result;
    }
    
    @Override
    public void updateUserName(String userId, String newName) {
        userDAO.updateUserName(userId, newName);
        UserCacheManager.instance().set(userId, "user_name", newName);
    }
    
    @Override
    public List<User> getAllUser(String userId) {
        List<User> users = userRepository.findAll();
        return users;
    }
    
    @Override
    public List<User> searchUser(String keyName) {
        List<User> users = userDAO.searchByName(keyName);
        return users;
    }
    
    @Override
    public List<User> getFriend(String userId) {
        List<String> friends = friendDAO.listFriend(userId);
        List<User> users = userRepository.findByIdIn(friends);
        return users;
    }
    
    @Override
    public List<User> getFriendExcep(String userId, List<String> excIds) {
        List<String> friends = friendDAO.listFriend(userId);
        friends.removeAll(excIds);
        List<User> users = userRepository.findByIdIn(friends);
        return users;
    }
    
    @Override
    public List<User> getRequestedFriend(String userId) {
        List<String> friends = friendDAO.listRequestFriend(userId);
        List<User> users = userRepository.findByIdIn(friends);
        return users;
    }
    
    @Override
    public void requestFriend(String userId, String friendId) {
        friendDAO.requestFriend(friendId, userId);
    }
    
    @Override
    public void denyFriend(String userId, String friendId) {
        friendDAO.denyFriend(userId, friendId);
    }
    
    @Override
    public void acceptFriend(String userId, String friendId) {
        friendDAO.acceptFriend(userId, friendId);
    }
    
    @Override
    public void rejectFriend(String userId, String friendId) {
        friendDAO.rejectFriend(userId, friendId);
        friendDAO.rejectFriend(friendId, userId);
    }
    
    @Override
    public String getUserName(String userId) {
        String userName = "";
        try {
            UserCache userCache = (UserCache)UserCacheManager.instance().get(userId);
            userName = userCache.getUserName();
        } catch (Exception ex) {
            log.error("", ex);
        }
        log.info(" userservice.getusername() ====== " + userId + " = " + userName);
        return userName;
    }
    
}
