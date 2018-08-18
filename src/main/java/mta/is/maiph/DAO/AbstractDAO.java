package mta.is.maiph.DAO;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import mta.is.maiph.config.MongoConfig;

/**
 *
 * @author MaiPH
 */
public abstract class AbstractDAO {

    protected static MongoClient mongo;

    static {
        if (mongo == null) {
            MongoClientURI uri = new MongoClientURI(MongoConfig.URI);
            mongo = new MongoClient(uri);
        }
    }

    protected abstract DB getDB();

    protected abstract String colectionName();

    protected DBCollection getColection() {
        return getDB().getCollection(colectionName());
    }

}
