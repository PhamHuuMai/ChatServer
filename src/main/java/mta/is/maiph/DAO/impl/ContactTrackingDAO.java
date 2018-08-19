package mta.is.maiph.DAO.impl;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import mta.is.maiph.DAO.AbstractDAO;

/**
 *
 * @author MaiPH
 */
public class ContactTrackingDAO extends AbstractDAO {

    private static int MAX_CONTACTED_USERS_SIZE = 5000;

    @Override
    protected DB getDB() {
        return mongo.getDB("test");
    }

    @Override
    protected String colectionName() {
        return "contact_tracking";
    }

    public void add(String userId, String friendId) {
        DBObject findObj = new BasicDBObject("user_id", userId);
        findObj.put("contacted" + "." + (MAX_CONTACTED_USERS_SIZE - 1), new BasicDBObject("$exists", false));
        DBObject addToSetObj = new BasicDBObject("$addToSet", new BasicDBObject("contacted", friendId));
        getColection().update(findObj, addToSetObj, true, false);
    }

    public boolean contacted(String userId, String friendId) {
        DBObject findObj = new BasicDBObject("user_id", userId);
        findObj.put("contacted", new BasicDBObject("$elemMatch",new BasicDBObject("$eq",friendId)));
        DBCursor cur = getColection().find(findObj);
        return (cur != null && cur.size() > 0);
    }
}
