/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mta.is.maiph.controller;

import java.util.Base64;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.sound.midi.SysexMessage;
import mta.is.maiph.DAO.impl.ConversationDAO;
import mta.is.maiph.DAO.impl.FileDAO;
import mta.is.maiph.constant.ErrorCode;
import mta.is.maiph.dto.request.AddMemberRequest;
import mta.is.maiph.dto.request.AllFriendRequest;
import mta.is.maiph.entity.User;
import mta.is.maiph.exception.ApplicationException;
import mta.is.maiph.repository.UserRepository;
import mta.is.maiph.dto.request.LoginRequest;
import mta.is.maiph.dto.request.RegisterRequest;
import mta.is.maiph.dto.request.UploadFileRequest;
import mta.is.maiph.dto.response.LoginResponse;
import mta.is.maiph.dto.response.Response;
import mta.is.maiph.dto.response.UploadFileResponse;
import mta.is.maiph.dto.response.UserResponse;
import mta.is.maiph.entity.File;
import mta.is.maiph.session.SessionManager;
import mta.is.maiph.session.TokenFactory;
import mta.is.maiph.util.Base64Util;
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
public class FileController {

    @Autowired
    public FileController() {

    }

    @PostMapping("/uploadfile")
    public ResponseEntity upload(@RequestHeader(name = "Authorization") String token, @RequestBody UploadFileRequest request) throws Exception {
        Response response = new Response(ErrorCode.SUCCESS);
        String userId = SessionManager.instance().check(token);
        String fileBase64 = request.getFile();
        String fileName = request.getFileName();
        String filePath = Base64Util.generatePathRoot() + Base64Util.generatePath();
        Base64Util.createDir(filePath);
        String fileNameLocal = System.currentTimeMillis() + fileName;
        Base64Util.decode(fileBase64, filePath + fileNameLocal);
        new FileDAO().insert(
                new File(
                        null,
                        userId,
                        fileName, request.getMimeType(),
                        Base64Util.generatePath() + fileNameLocal,
                        new Date().toGMTString()));
        response.setData(new UploadFileResponse(Base64Util.generatePath() + fileNameLocal, ""));
        return new ResponseEntity(response, HttpStatus.OK);
    }

}
