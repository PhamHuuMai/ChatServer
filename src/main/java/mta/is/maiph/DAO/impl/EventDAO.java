package mta.is.maiph.DAO.impl;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import java.util.ArrayList;
import java.util.List;
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

    public List<Event> get(String cvsId) {
        List<Event> result = new ArrayList<>();
        DBObject findObj = new BasicDBObject("cvs_id", cvsId);
        DBObject sortObj = new BasicDBObject("action_time", 1);
        sortObj.put("",1);
        try (DBCursor cursor = getColection().find(findObj).sort(sortObj)) {
            while (cursor.hasNext()) {
                DBObject nextElement = cursor.next();
                result.add(castFromDBObject(nextElement));
            }
        }
        return result;
    }

    public void remove(String id) {
        getColection().remove(new BasicDBObject("_id", new ObjectId(id)));
    }

    private DBObject castToDBObject(Event event) {
        DBObject result = new BasicDBObject("title", event.getTitle());
        result.put("content", event.getContent());
        result.put("user_id", event.getUserId());
        result.put("cvs_id", event.getCvsId());
        result.put("piority", event.getPiority());
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
                .piority((Integer)event.get("piority"))
                .cvsId((String) event.get("cvs_id"))
                .actionTime((Long) event.get("action_time"))
                .createTime((Long) event.get("time"))
                .build();
        return result;
    }
}
