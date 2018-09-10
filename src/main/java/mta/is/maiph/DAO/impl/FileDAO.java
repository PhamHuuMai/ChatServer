/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mta.is.maiph.DAO.impl;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import mta.is.maiph.DAO.AbstractDAO;
import mta.is.maiph.config.MongoConfig;
import mta.is.maiph.entity.File;

/**
 *
 * @author MaiPH
 */
public class FileDAO extends AbstractDAO {

    @Override
    protected DB getDB() {
        return mongo.getDB(MongoConfig.DB);
    }

    @Override
    protected String colectionName() {
        return "file";
    }
    
    public void insert(File file){
        DBObject obj = new BasicDBObject("user_id",file.getUserId());
        obj.put("url", file.getUrl());
        obj.put("mime_type", file.getMimeType());
        obj.put("original_name", file.getOriginalFileName());
        obj.put("time", System.currentTimeMillis());
        getColection().insert(obj);
    }
}
