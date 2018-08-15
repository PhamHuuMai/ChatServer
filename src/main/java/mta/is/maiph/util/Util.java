/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mta.is.maiph.util;

import java.util.Date;

/**
 *
 * @author MaiPH
 */
public class Util {

    public static String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    public static String currentTIme_yyyyMMddhhmmss(){
        Date date = new Date(System.currentTimeMillis());
        StringBuilder time = new StringBuilder();
        time.append(date.getYear());
        time.append(date.getMonth());
        time.append(date.getDate());
        time.append(date.getHours());
        time.append(date.getMinutes());
        time.append(date.getSeconds());
        
        return time.toString();
    }
}
