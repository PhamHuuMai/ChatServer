package mta.is.maiph.DAO.impl;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import mta.is.maiph.DAO.AbstractDAO;
import org.bson.types.ObjectId;

/**
 *
 * @author MaiPH
 */
public class ConversationDAO extends AbstractDAO {

    private static ConversationDAO instance;

    private ConversationDAO() {
    }

    public static ConversationDAO instance() {
        return instance == null ? new ConversationDAO() : instance;
    }

    @Override
    protected DB getDB() {
        return mongo.getDB("test");
    }

    @Override
    protected String colectionName() {
        return "conversation";
    }

    public List<String> getListMem(String id) {
        DBObject result = getColection().findOne(new BasicDBObject("_id", new ObjectId(id)));
        BasicDBList list = (BasicDBList) result.get("members");
        List<String> ret = new LinkedList<>();
        list.forEach(new Consumer<Object>() {
            @Override
            public void accept(Object t) {
                ret.add((String) t);
            }
        });
        return ret;
    }

    public void updateLastMsg(String cvsId, String msgValue) {
        DBObject updateObj = new BasicDBObject("last_chat_value", msgValue);
        getColection().update(new BasicDBObject("_id", new ObjectId(cvsId)),
                new BasicDBObject("$set", updateObj));
    }
}
