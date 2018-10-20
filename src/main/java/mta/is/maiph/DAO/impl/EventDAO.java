package mta.is.maiph.DAO.impl;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import mta.is.maiph.DAO.AbstractDAO;
import mta.is.maiph.config.MongoConfig;
import mta.is.maiph.entity.Event;
import org.bson.types.ObjectId;

/**
 *
 * @author MaiPH
 */
public class EventDAO extends AbstractDAO {
    
    @Override
    protected DB getDB() {
        return mongo.getDB(MongoConfig.DB);
    }
    
    @Override
    protected String colectionName() {
        return "event";
    }
    
    public void add(Event event) {
        getColection().insert(castToDBObject(event));
    }
    
    public void remove(String id) {
        getColection().remove(new BasicDBObject("_id", new ObjectId(id)));
    }
    
    private DBObject castToDBObject(Event event) {
        DBObject result = new BasicDBObject("title", event.getTitle());
        result.put("content", event.getContent());
        result.put("user_id", event.getUserId());
        result.put("cvs_id", event.getCvsId());
        result.put("action_time", event.getActionTime());
        result.put("time", event.getCreateTime());
        return result;
    }
    
    private Event castFromDBObject(DBObject event) {
        Event result = Event.builder()
                .id(((ObjectId) event.get("_id")).toHexString())
                .title((String) event.get("title"))
                .content((String) event.get("content"))
                .userId((String) event.get("user_id"))
                .cvsId((String) event.get("cvs_id"))
                .actionTime((Long) event.get("action_time"))
                .createTime((Long) event.get("time"))
                .build();
        return result;
    }
}
