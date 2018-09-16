package mta.is.maiph.DAO.impl;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import mta.is.maiph.DAO.AbstractDAO;
import mta.is.maiph.config.MongoConfig;
import org.bson.types.ObjectId;

/**
 *
 * @author MaiPH
 */
public class FriendDAO extends AbstractDAO {

    @Override
    protected DB getDB() {
        return mongo.getDB(MongoConfig.DB);
    }

    @Override
    protected String colectionName() {
        return "friend";
    }

    public void requestFriend(String userId, String friendId) {
        DBObject find = new BasicDBObject("_id", new ObjectId(userId));
        DBObject addToSetObj = new BasicDBObject("$addToSet", new BasicDBObject("requests", friendId));
        getColection().update(find, addToSetObj, true, true);
    }

    public void acceptFriend(String userId, String friendId) {
        DBObject find = new BasicDBObject("_id", new ObjectId(userId));
        DBObject addToSetObj = new BasicDBObject("$addToSet", new BasicDBObject("friends", friendId));
        DBObject pullObj = new BasicDBObject("$pull", new BasicDBObject("requests", friendId));
        getColection().update(find, addToSetObj, true, true);
        getColection().update(find, pullObj, true, true);
    }

    public void denyFriend(String userId, String friendId) {
        DBObject find = new BasicDBObject("_id", new ObjectId(userId));
        DBObject pullObj = new BasicDBObject("$pull", new BasicDBObject("requests", friendId));
        getColection().update(find, pullObj, true, true);
    }

    public void rejectFriend(String userId, String friendId) {
        DBObject find = new BasicDBObject("_id", new ObjectId(userId));
        DBObject pullObj = new BasicDBObject("$pull", new BasicDBObject("friends", friendId));
        getColection().update(find, pullObj, true, true);
    }

    public List<String> listFriend(String userId) {
        DBObject find = new BasicDBObject("_id", new ObjectId(userId));
        DBObject result = getColection().findOne(find);
        if (result != null) {
            Object requestsObj = result.get("friends");
            if (requestsObj != null) {
                return castToList((BasicDBList) requestsObj);
            }
            return new ArrayList<>();
        }
        return new ArrayList<>();
    }

    public List<String> listRequestFriend(String userId) {
        DBObject find = new BasicDBObject("_id", new ObjectId(userId));
        DBObject result = getColection().findOne(find);
        if (result != null) {
            Object requestsObj = result.get("requests");
            if (requestsObj != null) {
                return castToList((BasicDBList) requestsObj);
            }
            return new ArrayList<>();
        }
        return new ArrayList<>();
    }

    private List<String> castToList(BasicDBList listObj) {
        List<String> result = new LinkedList<>();
        listObj.forEach((t) -> {
            result.add((String) t);
        });
        return result;
    }
}
