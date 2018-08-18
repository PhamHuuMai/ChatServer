package mta.is.maiph.DAO.impl;

import com.mongodb.DB;
import mta.is.maiph.DAO.AbstractDAO;

/**
 *
 * @author MaiPH
 */
public class ContactTrackingDAO extends AbstractDAO{

    @Override
    protected DB getDB() {
        return mongo.getDB("test");
    }

    @Override
    protected String colectionName() {
        return "contact_tracking";
    }
    
    public void add(String userId,String friendId){
   
    }
    public boolean check(String userId,String friendId){
        return true;
    }
}
