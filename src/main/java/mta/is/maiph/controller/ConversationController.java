package mta.is.maiph.controller;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import mta.is.maiph.DAO.impl.ContactTrackingDAO;
import mta.is.maiph.DAO.impl.ConversationDAO;
import mta.is.maiph.DAO.impl.MessageDAO;
import mta.is.maiph.DAO.impl.UnreadMsgDAO;
import mta.is.maiph.constant.ErrorCode;
import mta.is.maiph.dto.request.AddConversationRequest;
import mta.is.maiph.dto.request.AddMemberRequest;
import mta.is.maiph.dto.request.GetContenCvstRequest;
import mta.is.maiph.dto.request.ListMemberRequest;
import mta.is.maiph.dto.request.RenameConversationRequest;
import mta.is.maiph.dto.response.ConversationResponse;
import mta.is.maiph.dto.response.Response;
import mta.is.maiph.dto.response.UserResponse;
import mta.is.maiph.entity.Conversation;
import mta.is.maiph.entity.Message;
import mta.is.maiph.entity.UnreadMessage;
import mta.is.maiph.entity.User;
import mta.is.maiph.repository.ConversationRepository;
import mta.is.maiph.repository.UnreadRepository;
import mta.is.maiph.repository.UserRepository;
import mta.is.maiph.session.SessionManager;
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
public class ConversationController {

    private ConversationRepository conversationRepository;
    private UnreadRepository unreadRepository;
    private UserRepository userRepository;
    private ContactTrackingDAO ct = new ContactTrackingDAO();
    private MessageDAO msgDAO = new MessageDAO();
    private UnreadMsgDAO unReadMsgDAO = new UnreadMsgDAO();
    private ConversationDAO cvsDAO = ConversationDAO.instance();
    @Autowired
    public ConversationController(ConversationRepository conversationRepository, UnreadRepository unreadRepository, UserRepository userRepository) {
        this.conversationRepository = conversationRepository;
        this.unreadRepository = unreadRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/addconversation")
    public ResponseEntity addConversation(@RequestBody AddConversationRequest addConversationRequest, @RequestHeader(name = "Authorization") String token) throws Exception {
        Response response = new Response(ErrorCode.SUCCESS);
        String userId = SessionManager.instance().check(token);
        String memeberId = addConversationRequest.getMemberId();
        // 
        if (userId.equals(memeberId) || ct.contacted(userId, memeberId)) {
            System.out.println("==============================");
            return new ResponseEntity(response, HttpStatus.OK);
        }
        // 
        ct.add(userId, memeberId);
        ct.add(memeberId, userId);
        User user = userRepository.findById(userId).get();
        User friend = userRepository.findById(memeberId).get();
        //
        Conversation cvs = new Conversation();
        cvs.setAdminId(userId);
        cvs.setLastChat(Util.currentTIme_yyyyMMddhhmmss());
        cvs.setCreateTime(Util.currentTIme_yyyyMMddhhmmss());
        cvs.setMembers(Arrays.asList(userId, memeberId));
        cvs.setName("new conversation");
        cvs.setGroup(false);
        Conversation result = conversationRepository.insert(cvs);
        //
        unreadRepository.insert(new UnreadMessage(null, userId, result.getId(), friend.getName(), 0));
        unreadRepository.insert(new UnreadMessage(null, memeberId, result.getId(), user.getName(), 0));
        //
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping("/addmember")
    public ResponseEntity addMember(@RequestBody AddMemberRequest addMemberRequest, @RequestHeader(name = "Authorization") String token) throws Exception {
        Response response = new Response(ErrorCode.SUCCESS);
        String userId = SessionManager.instance().check(token);

        String memeberId = addMemberRequest.getMemberId();
        String cvsId = addMemberRequest.getCvsId();

        // Check cvs group
        Conversation cvs = conversationRepository.findById(cvsId).get();
        if (cvs == null) {
            return new ResponseEntity(response, HttpStatus.OK);
        }
        List<String> members = cvs.getMembers();
        if (members.contains(memeberId)) {
            return new ResponseEntity(response, HttpStatus.OK);
        }
        members.add(memeberId);
        if (!cvs.isGroup()) {
            // create new group and add member
            cvs.setId(null);
            cvs.setLastChat("");
            cvs.setCreateTime(Util.currentTIme_yyyyMMddhhmmss());
            cvs.setMembers(members);
            cvs.setName("new conversation");
            cvs.setGroup(true);
            Conversation result = conversationRepository.insert(cvs);
            for (String member : members) {
                unreadRepository.insert(new UnreadMessage(null, member, result.getId(), "new conversation", 0));
            }
        } else {
            // add member
            cvs.setMembers(members);
            conversationRepository.save(cvs);
            //
            unreadRepository.insert(new UnreadMessage(null, memeberId, cvsId, cvs.getName(), 0));
        }
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping("/allmember")
    public ResponseEntity allMember(@RequestBody ListMemberRequest request, @RequestHeader(name = "Authorization") String token) throws Exception {
        Response response = new Response(ErrorCode.SUCCESS);
        String userId = SessionManager.instance().check(token);

        String cvsId = request.getCvsId();

        // Check cvs group
        Conversation cvs = conversationRepository.findById(cvsId).get();
        if (cvs == null) {
            return new ResponseEntity(response, HttpStatus.OK);
        }
        List<String> members = cvs.getMembers();
        Iterable<User> result = userRepository.findAllById(members);
        List<UserResponse> resp = new LinkedList<>();
        for (User user : result) {
            resp.add(new UserResponse(user));
        }
        response.setData(resp);
        return new ResponseEntity(response, HttpStatus.OK);
    }
    @PostMapping("/getallconversation")
    public ResponseEntity getAllConversation(@RequestHeader(name = "Authorization") String token) throws Exception {
        Response response = new Response(ErrorCode.SUCCESS);
        String userId = SessionManager.instance().check(token);
        List<UnreadMessage> allConversation = (List<UnreadMessage>) unreadRepository.findByUserId(userId);
        List<ConversationResponse> result = new LinkedList<>();
        for (UnreadMessage um : allConversation) {
            Conversation cvs = conversationRepository.findById(um.getConversationId()).get();
            result.add(new ConversationResponse(um.getConversationId(),
                    um.getConversationName(),
                    "/1.png",
                    cvs.getLastChat(),
                    um.getNumUnread(),
                    cvs.getLastTimeAction()           
            ));
        }
        response.setData(result);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping("/getconversationcontent")
    public ResponseEntity getConversationAllContent(@RequestBody GetContenCvstRequest request, @RequestHeader(name = "Authorization") String token) throws Exception {
        Response response = new Response(ErrorCode.SUCCESS);
        String userId = SessionManager.instance().check(token);
        String cvsId = request.getCvsId();
        List<Message> result = msgDAO.getAllContent(cvsId);
        unReadMsgDAO.read(userId, cvsId);
        response.setData(result);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping("/getconversationcontent/v2")
    public ResponseEntity getConversationContent(@RequestBody GetContenCvstRequest request, @RequestHeader(name = "Authorization") String token) throws Exception {
        Response response = new Response(ErrorCode.SUCCESS);
        String userId = SessionManager.instance().check(token);
        int skip = request.getSkip();
        int take = request.getTake();
        String cvsId = request.getCvsId();
        List<Message> result = msgDAO.getContent(cvsId, skip, take);
        result.forEach((t) -> {
            String msgOwner = t.getUserId();
            User u = userRepository.findById(msgOwner).get();
            t.setName(u.getName());
            t.setAvatar(u.getAvatarUrl());
        });
        unReadMsgDAO.read(userId, cvsId);
        response.setData(result);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping("/renameconversation")
    public ResponseEntity renameConversation(@RequestBody RenameConversationRequest request, @RequestHeader(name = "Authorization") String token) throws Exception {
        Response response = new Response(ErrorCode.SUCCESS);
        String userId = SessionManager.instance().check(token);
        String cvsId = request.getCvsId();
        String name = request.getName();
        cvsDAO.updateName(cvsId, name);
        unReadMsgDAO.rename(cvsId, name);
        return new ResponseEntity(response, HttpStatus.OK);
    }

}
