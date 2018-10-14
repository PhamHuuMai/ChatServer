package mta.is.maiph.service;

import java.util.List;
import mta.is.maiph.entity.User;
import mta.is.maiph.exception.ApplicationException;

/**
 *
 * @author MaiPH
 */

public interface UserService {
    User login(String email,String password) throws ApplicationException;
    User register(User xUser) throws ApplicationException;
    void updateUserName(String userId, String newName);
    
    List<User> getAllUser(String userId);
    List<User> searchUser(String keyName);
    
    List<User> getFriend(String userId);
    List<User> getFriendExcep(String userId, List<String> excIds);
    List<User> getRequestedFriend(String userId);
    
    void requestFriend(String userId, String friendId);
    void denyFriend(String userId, String friendId);
    void acceptFriend(String userId, String friendId);
    void rejectFriend(String userId, String friendId);
}
