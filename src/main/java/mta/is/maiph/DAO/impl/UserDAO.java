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
}
