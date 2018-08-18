
package mta.is.maiph.DAO.impl;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import mta.is.maiph.DAO.AbstractDAO;

/**
 *
 * @author MaiPH
 */
public class MessageDAO extends AbstractDAO{

    @Override
    protected DB getDB() {
         return mongo.getDB("test");
    }

    @Override
    protected String colectionName() {
        return "";
    }
    
    public void add(String cvsId, String userId, String value){
        DBObject obj = new BasicDBObject("user_id", userId);
        obj.put("value", value);
        getDB().getCollection(cvsId).insert(obj);
    }
}
