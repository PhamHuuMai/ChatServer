package mta.is.maiph.controller;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import mta.is.maiph.constant.ErrorCode;
import mta.is.maiph.dto.request.AddEventRequest;
import mta.is.maiph.dto.request.RemoveEventRequest;
import mta.is.maiph.dto.request.UploadFileRequest;
import mta.is.maiph.dto.response.EventResponse;
import mta.is.maiph.dto.response.Response;
import mta.is.maiph.entity.Event;
import mta.is.maiph.service.EventService;
import mta.is.maiph.service.UserService;
import mta.is.maiph.session.SessionManager;
import mta.is.maiph.util.Util;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author MaiPH
 */
@RestController()
@Slf4j
public class EventController {

    private final EventService eventService;

    private final UserService userService;

    @Autowired
    public EventController(EventService eventService,UserService userService) {
        this.eventService = eventService;
        this.userService = userService;
    }

    @PostMapping("/getevent")
    public ResponseEntity getEvent(@RequestHeader(name = "Authorization") String token, @RequestBody AddEventRequest request) throws Exception {
        Response response = new Response(ErrorCode.SUCCESS);
        List<Event> events = eventService.getEvent(request.getCvsId());
        List<EventResponse> result = new ArrayList<>();
        events.forEach((event) -> {
            result.add(EventResponse.builder()
                    .id(event.getId())
                    .content(event.getContent())
                    .title(event.getTitle())
                    .actionTime(Util.format_yyyyMMdd(event.getActionTime()))
                    .createTime(Util.format_yyyyMMdd(event.getCreateTime()))
                    .userName(userService.getUserName(event.getUserId()))
                    .piority(event.getPiority())
                    .build());
        });
        response.setData(result);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping("/createevent")
    public ResponseEntity createEvent(@RequestHeader(name = "Authorization") String token, @RequestBody AddEventRequest request) throws Exception {
        Response response = new Response(ErrorCode.SUCCESS);
        String userId = SessionManager.instance().check(token);
        Event event = Event.builder()
                .userId(userId)
                .cvsId(request.getCvsId())
                .actionTime(Util.format_yyyyMMdd(request.getTime()))
                .title(request.getTitle())
                .content(request.getContent())
                .piority(request.getPiority())
                .createTime(System.currentTimeMillis())
                .build();
        eventService.addEvent(event);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping("/removeevent")
    public ResponseEntity removeEvent(@RequestHeader(name = "Authorization") String token, @RequestBody RemoveEventRequest request) throws Exception {
        Response response = new Response(ErrorCode.SUCCESS);
        eventService.removeEvent(request.getEventId());
        return new ResponseEntity(response, HttpStatus.OK);
    }
}
