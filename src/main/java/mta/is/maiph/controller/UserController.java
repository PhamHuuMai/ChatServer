/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mta.is.maiph.controller;

import java.util.LinkedList;
import java.util.List;
import mta.is.maiph.DAO.impl.ConversationDAO;
import mta.is.maiph.DAO.impl.UserDAO;
import mta.is.maiph.constant.ErrorCode;
import mta.is.maiph.dto.request.AddMemberRequest;
import mta.is.maiph.dto.request.AllFriendRequest;
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

    private UserRepository userRepository;
    private UserDAO userDAO = new UserDAO();
    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest loginRequest) throws Exception {
        Response response = new Response(ErrorCode.SUCCESS);
        List<User> users = userRepository.findAll();
        User user = userRepository.findByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPasswordMd5());
        if (user == null) {
            throw new ApplicationException(ErrorCode.INVALID_ACCOUNT);
        }
        String token = TokenFactory.generateRandomToken();
        SessionManager.instance().add(token, user.getId());
        response.setData(new LoginResponse(token, user.getEmail(), user.getId(),user.getName()));
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
                "http://103.81.85.169/file/2.png"
        );
        User result = userRepository.insert(user);

        String token = TokenFactory.generateRandomToken();
        SessionManager.instance().add(token, result.getId());
        response.setData(new LoginResponse(token, user.getEmail(), result.getId(),result.getName()));
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping("/getAllUser")
    public ResponseEntity getAllUser(@RequestHeader(name = "Authorization") String token) throws Exception {
        Response response = new Response(ErrorCode.SUCCESS);
        String userId = SessionManager.instance().check(token);
        List<User> users = userRepository.findAll();
        List<UserResponse> userResponse = new LinkedList<>();
        for (User user : users) {
            userResponse.add(new UserResponse(user));
        }
        response.setData(userResponse);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping("/addfriend")
    public ResponseEntity addFriend(@RequestHeader(name = "Authorization") String token, @RequestBody AddMemberRequest request) throws Exception {
        Response response = new Response(ErrorCode.SUCCESS);
        String userId = SessionManager.instance().check(token);
        String cvsId = request.getCvsId();
        String memberId = request.getMemberId();
        // 

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping("/allfriend")
    public ResponseEntity getFriend(@RequestHeader(name = "Authorization") String token, @RequestBody AllFriendRequest request) throws Exception {
        Response response = new Response(ErrorCode.SUCCESS);
        String userId = SessionManager.instance().check(token);
        String csvId = request.getCvsId();
        List<String> members = ConversationDAO.instance().getListMem(csvId);
        List<User> users = userRepository.findAll();
        List<UserResponse> userResponse = new LinkedList<>();

        for (User user : users) {
            if (!members.contains(user.getId())) {
                userResponse.add(new UserResponse(user));
            }
        }
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
