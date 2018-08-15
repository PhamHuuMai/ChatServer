/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mta.is.maiph.controller;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.util.LinkedList;
import java.util.List;
import mta.is.maiph.constant.ErrorCode;
import mta.is.maiph.entity.User;
import mta.is.maiph.exception.ApplicationException;
import mta.is.maiph.repository.UserRepository;
import mta.is.maiph.dto.request.LoginRequest;
import mta.is.maiph.dto.request.RegisterRequest;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author MaiPH
 */
@RestController()
public class UserController {

    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest loginRequest) throws Exception {
        Response response = new Response(ErrorCode.SUCCESS);
        List<User> users = userRepository.findAll();
        User user = userRepository.findByEmailAndPassword(loginRequest.getEmail(),loginRequest.getPasswordMd5());
        if (user == null) {
            throw new ApplicationException(ErrorCode.INVALID_ACCOUNT);
        }
        String token = TokenFactory.generateRandomToken();
        SessionManager.add(token, user.getId());
        response.setData(new LoginResponse(token, user.getEmail()));
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
        user = new User(null, regRequest.getEmail(), originalPass, password,Util.currentTIme_yyyyMMddhhmmss());
        User result = userRepository.insert(user);

        String token = TokenFactory.generateRandomToken();
        SessionManager.add(token, result.getId());
        response.setData(new LoginResponse(token, user.getEmail()));
        return new ResponseEntity(response, HttpStatus.OK);
    }
    
    @PostMapping("/getAllUser")
    public ResponseEntity register(@RequestHeader(name = "Authorization") String token) throws Exception {
        Response response = new Response(ErrorCode.SUCCESS);
        String userId = SessionManager.check(token);
        List<User> users = userRepository.findAll();
        List<UserResponse> userResponse = new LinkedList<>();
        for (User user : users) {
            userResponse.add(new UserResponse(user));
        }
        response.setData(userResponse);
        return new ResponseEntity(response, HttpStatus.OK);
    }

}
