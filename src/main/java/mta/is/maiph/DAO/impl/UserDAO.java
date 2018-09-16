package mta.is.maiph.DAO.impl;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import java.util.LinkedList;
import java.util.List;
import mta.is.maiph.DAO.AbstractDAO;
import mta.is.maiph.config.MongoConfig;
import mta.is.maiph.entity.User;
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

    public String getUserNameById(String userId) {
        DBObject resul = getColection().findOne(new BasicDBObject("_id", new ObjectId(userId)));
        return (String) resul.get("name");
    }

    public String getAvatarById(String userId) {
        DBObject resul = getColection().findOne(new BasicDBObject("_id", new ObjectId(userId)));
        return (String) resul.get("avatar_url");
    }

    public void updateUserName(String userId, String name) {
        DBObject find = new BasicDBObject("_id", new ObjectId(userId));
        getColection().update(find, new BasicDBObject("$set", new BasicDBObject("name", name)));
    }

    public void updateAvatar(String userId, String avatar) {
        DBObject find = new BasicDBObject("_id", new ObjectId(userId));
        getColection().update(find, new BasicDBObject("$set", new BasicDBObject("avatar_url", avatar)));
    }

    public List<User> searchByName(String text) {
        DBObject find = new BasicDBObject("name", new BasicDBObject("$regex", ".*" + text + ".*"));
        List<User> result = new LinkedList<>();
        DBCursor cur = getColection().find(find);
        while (cur.hasNext()) {
            DBObject next = cur.next();
            result.add(castToUser(next));
        }
        return result;
    }

    private User castToUser(DBObject in) {
        String name = (String) in.get("name");
        String lastlogin = (String) in.get("last_time_login");
        String avatar = (String) in.get("avatar_url");
        ObjectId id = (ObjectId) in.get("_id");
        return User.builder()
                .avatarUrl(avatar)
                .name(name)
                .lastLogin(lastlogin)
                .id(id.toHexString())
                .build();
    }

}
