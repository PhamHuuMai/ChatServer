package mta.is.maiph.DAO.impl;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import mta.is.maiph.DAO.AbstractDAO;

/**
 *
 * @author MaiPH
 */
public class UnreadMsgDAO extends AbstractDAO{

    @Override
    protected DB getDB() {
        return mongo.getDB("test");
    }

    @Override
    protected String colectionName() {
        return "unread_msg";
    }
    
    public void incUnread(String userId,String cvsId) {
        DBObject query = new BasicDBObject("user_id", userId);
        query.put("conversation_id", cvsId);
        BasicDBObject inc = new BasicDBObject("num_unread", 1);
        getColection().update(query, new BasicDBObject("$inc",inc));
    }
    public void read(String userId,String cvsId) {
        DBObject query = new BasicDBObject("user_id", userId);
        query.put("conversation_id", cvsId);
        BasicDBObject inc = new BasicDBObject("num_unread", 0);
        getColection().update(query, new BasicDBObject("$set",inc));
    }
}
