/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mta.is.maiph.controller;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.sound.midi.SysexMessage;
import lombok.extern.slf4j.Slf4j;
import mta.is.maiph.DAO.impl.ConversationDAO;
import mta.is.maiph.DAO.impl.FileAttachmentDAO;
import mta.is.maiph.DAO.impl.FileDAO;
import mta.is.maiph.DAO.impl.UserDAO;
import mta.is.maiph.constant.ErrorCode;
import mta.is.maiph.constant.FileConstants;
import mta.is.maiph.dto.request.AddMemberRequest;
import mta.is.maiph.dto.request.AllFriendRequest;
import mta.is.maiph.entity.User;
import mta.is.maiph.exception.ApplicationException;
import mta.is.maiph.repository.UserRepository;
import mta.is.maiph.dto.request.LoginRequest;
import mta.is.maiph.dto.request.RegisterRequest;
import mta.is.maiph.dto.request.UploadFileAttachmentRequest;
import mta.is.maiph.dto.request.UploadFileRequest;
import mta.is.maiph.dto.response.FileResponse;
import mta.is.maiph.dto.response.LoginResponse;
import mta.is.maiph.dto.response.Response;
import mta.is.maiph.dto.response.UploadFileResponse;
import mta.is.maiph.dto.response.UserResponse;
import mta.is.maiph.entity.File;
import mta.is.maiph.entity.FileAttachment;
import mta.is.maiph.service.UserService;
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
@Slf4j
public class FileController {
    
    private final FileDAO fileDAO = new FileDAO();
    private final UserDAO userDAO = new UserDAO();
    private final FileAttachmentDAO fileAttachmentDAO = new FileAttachmentDAO();
    private final UserService userService;
    
    @Autowired
    public FileController(UserService userService) {
        this.userService = userService;
    }
    
    @PostMapping("/uploadavatar")
    public ResponseEntity upload(@RequestHeader(name = "Authorization") String token, @RequestBody UploadFileRequest request) throws Exception {
        Response response = new Response(ErrorCode.SUCCESS);
        String userId = SessionManager.instance().check(token);
        String fileBase64 = request.getFile();
        String fileName = request.getFileName();
        String filePath = Base64Util.generatePathRoot() + Base64Util.generatePath();
        Base64Util.createDir(filePath);
        String fileNameLocal = System.currentTimeMillis() + fileName;
        Base64Util.decode(fileBase64, filePath + fileNameLocal);
        fileDAO.insert(
                new File(
                        null,
                        userId,
                        fileName, request.getMimeType(),
                        Base64Util.generatePath() + fileNameLocal,
                        System.currentTimeMillis()));
        userDAO.updateAvatar(userId, Base64Util.generatePath() + fileNameLocal);
        response.setData(new UploadFileResponse(Base64Util.generatePath() + fileNameLocal, "",3));
        return new ResponseEntity(response, HttpStatus.OK);
    }
    
    @PostMapping("/uploadfileattachment")
    public ResponseEntity uploadFileAttachment(@RequestHeader(name = "Authorization") String token, @RequestBody UploadFileAttachmentRequest request) throws Exception {
        Response response = new Response(ErrorCode.SUCCESS);
        String userId = SessionManager.instance().check(token);
        String fileBase64 = request.getFile();
        String fileName = request.getFileName();
        String filePath = Base64Util.generatePathRoot() + Base64Util.generatePath();
        Base64Util.createDir(filePath);
        String fileNameLocal = System.currentTimeMillis() + "-" + fileName;
        Base64Util.decode(fileBase64, filePath + fileNameLocal);
        String id = fileAttachmentDAO.insert(
                new FileAttachment(
                        null,
                        userId,
                        fileName,
                        request.getMimeType(),
                        Base64Util.generatePath() + fileNameLocal,
                        System.currentTimeMillis(),
                        request.getCvsId()
                ));
        
        response.setData(new UploadFileResponse(Base64Util.generatePath() + fileNameLocal, id, getMediaType(request.getMimeType())));
        return new ResponseEntity(response, HttpStatus.OK);
    }
    
    private int getMediaType(String mimetype) {
        if (mimetype == null) {
            return FileConstants.FILE_TYPE.OTHER;
        }
        if (mimetype.contains("image")) {
            return FileConstants.FILE_TYPE.IMAGE;
        }
        if (mimetype.contains("video")) {
            return FileConstants.FILE_TYPE.VIDEO;
        }
        if (mimetype.contains("audio")) {
            return FileConstants.FILE_TYPE.AUDIO;
        }
        return FileConstants.FILE_TYPE.OTHER;
    }
    
    @PostMapping("/getfileattachment")
    public ResponseEntity getFileAttachment(@RequestHeader(name = "Authorization") String token, @RequestBody UploadFileAttachmentRequest request) throws Exception {
        Response response = new Response(ErrorCode.SUCCESS);
        String userId = SessionManager.instance().check(token);
        String cvsId = request.getCvsId();
        List<File> files = fileAttachmentDAO.get(cvsId);
        List<FileResponse> result = new ArrayList<>();
        files.forEach((file) -> {
            FileResponse fileResponse = FileResponse.builder()
                    .originalFileName(file.getOriginalFileName())
                    .time(Util.format_yyyyMMdd(file.getTime()))
                    .url(FileConstants.HOST_FILE_ATT + file.getUrl())
                    .userName(userService.getUserName(file.getUserId()))
                    .build();
            result.add(fileResponse);
        });
        response.setData(result);
        return new ResponseEntity(response, HttpStatus.OK);
    }
    
}
