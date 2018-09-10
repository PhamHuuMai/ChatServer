/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mta.is.maiph.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.binary.Base64;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
/**
 *
 * @author MaiPH
 */
public class Base64Util {

    public static String generatePath(){
        int year = Calendar.YEAR;
        int month = Calendar.MONTH;
        int date = Calendar.DAY_OF_MONTH;
        StringBuilder path = new StringBuilder(File.separatorChar);
        path.append(year);
        path.append(File.separatorChar);
        path.append(month);
        path.append(File.separatorChar);
        path.append(date);
        path.append(File.separatorChar);
        return path.toString();
    }
    
    public static String generatePathRoot(){
        StringBuilder path = new StringBuilder(File.separatorChar);
        path.append("var");
        path.append(File.separatorChar);
        path.append("www");
        path.append(File.separatorChar);
        path.append("html");
        path.append(File.separatorChar);
        path.append("file");
        return path.toString();
    }
    public static void createDir(String path){     
        Path paths = Paths.get(path);
        if (!Files.exists(paths)) {
            try {
                Files.createDirectories(paths);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
     }
    
    public static void decode(String base64, String filePath) {
        try {
            Base64 factory = new Base64();
            byte[] bits = factory.decode(base64);
            FileOutputStream outputStream;
            File file = new File(filePath);
            outputStream = new FileOutputStream(file);
            outputStream.write(bits);
            outputStream.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Base64Util.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Base64Util.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
