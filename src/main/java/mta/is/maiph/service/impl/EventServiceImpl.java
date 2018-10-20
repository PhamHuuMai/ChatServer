package mta.is.maiph.service.impl;

import java.util.List;
import mta.is.maiph.DAO.impl.EventDAO;
import mta.is.maiph.entity.Event;
import mta.is.maiph.service.EventService;
import org.springframework.stereotype.Service;

/**
 *
 * @author MaiPH
 */
@Service
public class EventServiceImpl implements EventService {
    
    private EventDAO eventDAO = new EventDAO();
    
    @Override
    public List<Event> getEvent(String conversationId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void addEvent(Event event) {
        eventDAO.add(event);
    }
    
    @Override
    public void updateEvent(Event event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void removeEvent(String eventId) {
        eventDAO.remove(eventId);
    }
    
}
