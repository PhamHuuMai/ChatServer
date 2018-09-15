package mta.is.maiph.DAO.impl;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import mta.is.maiph.DAO.AbstractDAO;
import mta.is.maiph.config.MongoConfig;
import org.bson.types.ObjectId;

/**
 *
 * @author MaiPH
 */
public class UserDAO extends AbstractDAO {

    @Override
    protected DB getDB() {
        return mongo.getDB(MongoConfig.DB);
    }

    @Override
    protected String colectionName() {
        return "user";
    }
    public String getUserNameById(String userId){
        DBObject resul = getColection().findOne(new BasicDBObject("_id",new ObjectId(userId)));
        return (String)resul.get("name");
    }
    public void updateUserName(String userId,String name){
        DBObject find = new BasicDBObject("_id",new ObjectId(userId));
        getColection().update(find, new BasicDBObject("$set",new BasicDBObject("name",name)));
    }
    public void updateAvatar(String userId,String avatar){
        DBObject find = new BasicDBObject("_id",new ObjectId(userId));
        getColection().update(find, new BasicDBObject("$set",new BasicDBObject("avatar_url",avatar)));
    }
}
