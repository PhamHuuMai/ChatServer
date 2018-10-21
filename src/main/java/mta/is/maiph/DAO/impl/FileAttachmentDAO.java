/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mta.is.maiph.DAO.impl;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import java.util.ArrayList;
import java.util.List;
import mta.is.maiph.DAO.AbstractDAO;
import mta.is.maiph.config.MongoConfig;
import mta.is.maiph.entity.File;
import mta.is.maiph.entity.FileAttachment;
import org.bson.types.ObjectId;

/**
 *
 * @author MaiPH
 */
public class FileAttachmentDAO extends AbstractDAO {

    @Override
    protected DB getDB() {
        return mongo.getDB(MongoConfig.DB);
    }

    @Override
    protected String colectionName() {
        return "file_attachment";
    }

    public String insert(FileAttachment file) {
        DBObject obj = new BasicDBObject("user_id", file.getUserId());
        obj.put("cvs_id", file.getCvsId());
        obj.put("url", file.getUrl());
        obj.put("mime_type", file.getMimeType());
        obj.put("original_name", file.getOriginalFileName());
        obj.put("time", System.currentTimeMillis());
        obj.put("status", file.isStatus());
        getColection().insert(obj);
        return ((ObjectId) obj.get("_id")).toHexString();
    }

    public void updateFlag(String id) {
        DBObject findObj = new BasicDBObject("_id", new ObjectId(id));
        getColection().update(findObj, new BasicDBObject("$set", new BasicDBObject("status", true)));
    }

    public List<File> get(String cvsId) {
        List<File> result = new ArrayList<>();
        DBObject obj = new BasicDBObject("cvs_id", cvsId);
        try (DBCursor cur = getColection().find(obj)) {
            while (cur.hasNext()) {
                DBObject nextElement = cur.next();
                result.add(castToFile(obj));
            }
        }
        return result;
    }

    private File castToFile(DBObject file) {
        File result = File.builder()
                .originalFileName((String) file.get("original_name"))
                .userId((String) file.get("user_id"))
                .url((String) file.get("url"))
                .time((long) file.get("time"))
                .mimeType((String) file.get("mime_type"))
                .build();
        return result;
    }
}
