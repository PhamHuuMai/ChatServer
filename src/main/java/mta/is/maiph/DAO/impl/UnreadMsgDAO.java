package mta.is.maiph.DAO.impl;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import mta.is.maiph.DAO.AbstractDAO;
import mta.is.maiph.config.MongoConfig;

/**
 *
 * @author MaiPH
 */
public class UnreadMsgDAO extends AbstractDAO{

    @Override
    protected DB getDB() {
        return mongo.getDB(MongoConfig.DB);
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
    
    public void rename(String cvsId,String name) {
        DBObject query = new BasicDBObject("conversation_id", cvsId);
        BasicDBObject update = new BasicDBObject("conversation_name", name);
        getColection().update(query, new BasicDBObject("$set",update),true,true);
    }
}
