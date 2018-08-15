package mta.is.maiph.DAO;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

/**
 *
 * @author MaiPH
 */
public abstract class AbstractDAO {

    private static MongoClient mongo;

    static {
        MongoClientURI uri = new MongoClientURI("");
        mongo = new MongoClient(uri);
    }
//    public
}
