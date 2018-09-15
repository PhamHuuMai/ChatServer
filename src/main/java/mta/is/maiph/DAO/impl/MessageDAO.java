package mta.is.maiph.DAO.impl;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import java.util.LinkedList;
import java.util.List;
import mta.is.maiph.DAO.AbstractDAO;
import mta.is.maiph.config.MongoConfig;
import mta.is.maiph.entity.Message;
import org.bson.types.ObjectId;

/**
 *
 * @author MaiPH
 */
public class MessageDAO extends AbstractDAO {

    @Override
    protected DB getDB() {
        return mongo.getDB(MongoConfig.DB);
    }

    @Override
    protected String colectionName() {
        return "";
    }

    public void add(String cvsId, String userId, String value,int type) {
        DBObject obj = new BasicDBObject("user_id", userId);
        obj.put("value", value);
        obj.put("type", type);
        getDB().getCollection(cvsId).insert(obj);
    }

    public List<Message> getAllContent(String cvsId) {
        List<Message> result = new LinkedList<>();
        DBCursor cur = getDB().getCollection(cvsId).find().sort(new BasicDBObject("_id", 1));
        while (cur.hasNext()) {
            DBObject next = cur.next();
            ObjectId id = (ObjectId) next.get("_id");
            String userId = (String) next.get("user_id");
            String value = (String) next.get("value");
            String date = id.getDate().toString();

            result.add(new Message(id.toHexString(), userId, value, date,"",""));
        }
        return result;
    }

    public List<Message> getContent(String cvsId, int skip, int take) {
        List<Message> result = new LinkedList<>();
        DBCursor cur = getDB().getCollection(cvsId)
                .find()
                .sort(new BasicDBObject("_id", -1))
                .skip(skip)
                .limit(take);
        while (cur.hasNext()) {
            DBObject next = cur.next();
            ObjectId id = (ObjectId) next.get("_id");
            String userId = (String) next.get("user_id");
            String value = (String) next.get("value");
            String date = id.getDate().toLocaleString();
            result.add(new Message(id.toHexString(), userId, value, date,"",""));
        }
        return result;
    }
}
