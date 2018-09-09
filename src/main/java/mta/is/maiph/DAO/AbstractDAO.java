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
//            MongoClientURI uri = new MongoClientURI("mongodb://phamhuumai:21B%401996@cluster0-shard-00-00-jywz0.mongodb.net:27017/test?ssl=true&replicaSet=Cluster0-shard-0&authSource=admin&retryWrites=true");
            System.out.println(MongoConfig.HOST);
             mongo = new MongoClient(MongoConfig.HOST);
        }
    }

    protected abstract DB getDB();

    protected abstract String colectionName();

    protected DBCollection getColection() {
        return getDB().getCollection(colectionName());
    }

}
