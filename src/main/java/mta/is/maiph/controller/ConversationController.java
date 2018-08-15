package mta.is.maiph.controller;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import mta.is.maiph.constant.ErrorCode;
import mta.is.maiph.dto.request.AddConversationRequest;
import mta.is.maiph.dto.response.ConversationResponse;
import mta.is.maiph.dto.response.Response;
import mta.is.maiph.entity.Conversation;
import mta.is.maiph.entity.UnreadMessage;
import mta.is.maiph.repository.ConversationRepository;
import mta.is.maiph.repository.UnreadRepository;
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

    @Autowired
    public ConversationController(ConversationRepository conversationRepository, UnreadRepository unreadRepository) {
        this.conversationRepository = conversationRepository;
        this.unreadRepository = unreadRepository;
    }

    @PostMapping("/addconversation")
    public ResponseEntity addConversation(@RequestBody AddConversationRequest addConversationRequest, @RequestHeader(name = "Authorization") String token) throws Exception {
        Response response = new Response(ErrorCode.SUCCESS);
        String userId = SessionManager.check(token);
        String memeberId = addConversationRequest.getMemberId();
        Conversation cvs = new Conversation();
        cvs.setAdminId(userId);
        cvs.setLastChat(Util.currentTIme_yyyyMMddhhmmss());
        cvs.setCreateTime(Util.currentTIme_yyyyMMddhhmmss());
        cvs.setMembers(Arrays.asList(userId, memeberId));
        cvs.setName("new conversation");
        Conversation result = conversationRepository.insert(cvs);
        unreadRepository.insert(new UnreadMessage(userId, userId, result.getId(), "new conversation", 0));
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping("/getallconversation")
    public ResponseEntity getAllConversation(@RequestHeader(name = "Authorization") String token) throws Exception {
        Response response = new Response(ErrorCode.SUCCESS);
        String userId = SessionManager.check(token);
        List<UnreadMessage> allConversation = (List<UnreadMessage>) unreadRepository.findAllById(Arrays.asList(userId));
        List<ConversationResponse> result = new LinkedList<>();
        for (UnreadMessage um : allConversation) {
            Conversation cvs = conversationRepository.findById(um.getConversationId()).get();
            result.add(new ConversationResponse(um.getConversationId(),
                    um.getConversationName(),
                    cvs.getLastChat(),
                    um.getNumUnread(),
                    cvs.getLastTimeAction()
            ));
        }
        response.setData(result);
        return new ResponseEntity(response, HttpStatus.OK);
    }
}
