/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mta.is.maiph.service;

import java.util.List;
import mta.is.maiph.entity.Event;

/**
 *
 * @author MaiPH
 */
public interface EventService {
    List<Event> getEvent(String conversationId);
    void addEvent(Event event);
    void updateEvent(Event event);
    void removeEvent(String eventId);
}
