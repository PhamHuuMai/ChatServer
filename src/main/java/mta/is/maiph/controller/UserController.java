/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mta.is.maiph.controller;

import java.util.LinkedList;
import java.util.List;
import mta.is.maiph.DAO.impl.ConversationDAO;
import mta.is.maiph.DAO.impl.FriendDAO;
import mta.is.maiph.DAO.impl.UserDAO;
import mta.is.maiph.constant.ErrorCode;
import mta.is.maiph.dto.request.AllFriendRequest;
import mta.is.maiph.dto.request.FriendRequest;
import mta.is.maiph.entity.User;
import mta.is.maiph.exception.ApplicationException;
import mta.is.maiph.repository.UserRepository;
import mta.is.maiph.dto.request.LoginRequest;
import mta.is.maiph.dto.request.RegisterRequest;
import mta.is.maiph.dto.request.RenameRequest;
import mta.is.maiph.dto.response.LoginResponse;
import mta.is.maiph.dto.response.Response;
import mta.is.maiph.dto.response.UserResponse;
import mta.is.maiph.session.SessionManager;
import mta.is.maiph.session.TokenFactory;
import mta.is.maiph.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author MaiPH
 */
@RestController()
public class UserController {

    private final UserRepository userRepository;
    private final UserDAO userDAO = new UserDAO();
    private final FriendDAO friendDAO = new FriendDAO();

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest loginRequest) throws Exception {
        Response response = new Response(ErrorCode.SUCCESS);
        User user = userRepository.findByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPasswordMd5());
        if (user == null) {
            throw new ApplicationException(ErrorCode.INVALID_ACCOUNT);
        }
        String token = TokenFactory.generateRandomToken();
        SessionManager.instance().add(token, user.getId());
        response.setData(new LoginResponse(token, user.getEmail(), user.getId(), user.getName(), user.getAvatarUrl()));
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequest regRequest) throws Exception {
        Response response = new Response(ErrorCode.SUCCESS);
        User user = userRepository.findByEmail(regRequest.getEmail());
        if (user != null) {
            throw new ApplicationException(ErrorCode.INVALID_ACCOUNT);
        }
        String originalPass = regRequest.getPassword();
        String password = Util.MD5(originalPass);
        user = new User(null,
                regRequest.getName(),
                regRequest.getEmail(),
                originalPass, password,
                Util.currentTIme_yyyyMMddhhmmss(),
                "/2.png"
        );
        User result = userRepository.insert(user);
        String token = TokenFactory.generateRandomToken();
        SessionManager.instance().add(token, result.getId());
        response.setData(new LoginResponse(token, user.getEmail(), result.getId(), result.getName(), result.getAvatarUrl()));
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping("/getAllFriend")
    public ResponseEntity getAllFriend(@RequestHeader(name = "Authorization") String token) throws Exception {
        Response response = new Response(ErrorCode.SUCCESS);
        String userId = SessionManager.instance().check(token);
        List<String> friends = friendDAO.listFriend(userId);
        List<User> users = userRepository.findByIdIn(friends);
        List<UserResponse> userResponse = new LinkedList<>();
        users.forEach((user) -> {
            userResponse.add(new UserResponse(user));
        });
        response.setData(userResponse);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping("/getAllRequestedFriend")
    public ResponseEntity getAllRequestedFriend(@RequestHeader(name = "Authorization") String token) throws Exception {
        Response response = new Response(ErrorCode.SUCCESS);
        String userId = SessionManager.instance().check(token);
        List<String> friends = friendDAO.listRequestFriend(userId);
        List<User> users = userRepository.findByIdIn(friends);
        List<UserResponse> userResponse = new LinkedList<>();
        users.forEach((user) -> {
            userResponse.add(new UserResponse(user));
        });
        response.setData(userResponse);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping("/requestfriend")
    public ResponseEntity addFriend(@RequestHeader(name = "Authorization") String token, @RequestBody FriendRequest req) throws Exception {
        Response response = new Response(ErrorCode.SUCCESS);
        String userId = SessionManager.instance().check(token);
        String friendId = req.getFriendId();
        friendDAO.requestFriend(friendId, userId);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping("/denyfriend")
    public ResponseEntity denyFriend(@RequestHeader(name = "Authorization") String token, @RequestBody FriendRequest req) throws Exception {
        Response response = new Response(ErrorCode.SUCCESS);
        String userId = SessionManager.instance().check(token);
        String friendId = req.getFriendId();
        friendDAO.denyFriend(friendId, userId);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping("/rejectfriend")
    public ResponseEntity rejectFriend(@RequestHeader(name = "Authorization") String token, @RequestBody FriendRequest req) throws Exception {
        Response response = new Response(ErrorCode.SUCCESS);
        String userId = SessionManager.instance().check(token);
        String friendId = req.getFriendId();
        friendDAO.rejectFriend(userId, friendId);
        friendDAO.rejectFriend(friendId, userId);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping("/getAllUser")
    public ResponseEntity getAllUser(@RequestHeader(name = "Authorization") String token) throws Exception {
        Response response = new Response(ErrorCode.SUCCESS);
        String userId = SessionManager.instance().check(token);
        List<User> users = userRepository.findAll();
        List<UserResponse> userResponse = new LinkedList<>();
        users.forEach((user) -> {
            userResponse.add(new UserResponse(user));
        });
        response.setData(userResponse);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping("/allfriend")
    public ResponseEntity getAllFriend(@RequestHeader(name = "Authorization") String token, @RequestBody AllFriendRequest request) throws Exception {
        Response response = new Response(ErrorCode.SUCCESS);
        String userId = SessionManager.instance().check(token);
        String csvId = request.getCvsId();
        List<String> members = ConversationDAO.instance().getListMem(csvId);
        List<User> users = userRepository.findAll();
        List<UserResponse> userResponse = new LinkedList<>();
        users.stream().filter((user) -> (!members.contains(user.getId()))).forEachOrdered((user) -> {
            userResponse.add(new UserResponse(user));
        });
        response.setData(userResponse);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping("/rename")
    public ResponseEntity rename(@RequestHeader(name = "Authorization") String token, @RequestBody RenameRequest request) throws Exception {
        Response response = new Response(ErrorCode.SUCCESS);
        String userId = SessionManager.instance().check(token);
        String name = request.getNewName();
        userDAO.updateUserName(userId, name);
        return new ResponseEntity(response, HttpStatus.OK);
    }

}
