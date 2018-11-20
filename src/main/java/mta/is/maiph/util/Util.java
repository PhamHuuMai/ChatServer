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

    static long A_DAY = 24 * 60 * 60 * 1000;
    static long A_HOUR = 60 * 60 * 1000;
    static long A_MIN = 60 * 1000;
    static long A_SEC = 1000;

    public static String getLastOnline(long offset) {
        long dayPer = offset / A_DAY;
        String prefix = "";
        if (dayPer >= 1) {
            prefix = dayPer+"ngày trước";
        } else {
            long hourPer = offset / A_HOUR;
            if (hourPer >= 1) {
                prefix = hourPer + "giờ trước";
            } else {
                long minPer = offset / A_MIN;
                if (minPer >= 1) {
                    prefix = minPer + "phút trước";
                } else {
                    long secPer = offset / A_SEC;
                    prefix = secPer + "giây trước";
                }
            }
        }
        return prefix;
    }

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

    public static String currentTIme_yyyyMMddhhmmss() {
        Date date = new Date(System.currentTimeMillis());
        StringBuilder time = new StringBuilder();
        time.append(date.getYear() + 1900);
        time.append(date.getMonth() + 1);
        time.append(date.getDate());
        time.append(date.getHours());
        time.append(date.getMinutes());
        time.append(date.getSeconds());

        return time.toString();
    }

    public static long format_yyyyMMddhhmmss(String yyyyMMddhhmmss) {
        Date date = new Date();
        int year = Integer.valueOf(yyyyMMddhhmmss.substring(0, 4)) - 1900;
        date.setYear(year);
        int month = Integer.valueOf(yyyyMMddhhmmss.substring(4, 6)) - 1;
        date.setMonth(month);
        int day = Integer.valueOf(yyyyMMddhhmmss.substring(6, 8));
        date.setDate(day);
        int hour = Integer.valueOf(yyyyMMddhhmmss.substring(8, 10));
        date.setHours(hour);
        int minute = Integer.valueOf(yyyyMMddhhmmss.substring(10, 12));
        date.setMinutes(minute);
        int sec = Integer.valueOf(yyyyMMddhhmmss.substring(12, 14));
        date.setSeconds(sec);
        return date.getTime();
    }

    public static long format_yyyyMMdd(String yyyyMMdd) {
        Date date = new Date();
        int year = Integer.valueOf(yyyyMMdd.substring(0, 4)) - 1900;
        date.setYear(year);
        int month = Integer.valueOf(yyyyMMdd.substring(4, 6)) - 1;
        date.setMonth(month);
        int day = Integer.valueOf(yyyyMMdd.substring(6, 8));
        date.setDate(day);
        int hour = 0;
        date.setHours(hour);
        int minute = 0;
        date.setMinutes(minute);
        int sec = 0;
        date.setSeconds(sec);
        return date.getTime();
    }

    public static String format_yyyyMMddhhmmss(long timeLong) {
        Date date = new Date(timeLong);
        StringBuilder time = new StringBuilder();
        time.append(date.getYear() + 1900);
        time.append(date.getMonth() + 1);
        time.append(date.getDate());
        time.append(date.getHours());
        time.append(date.getMinutes());
        time.append(date.getSeconds());
        return time.toString();
    }

    public static String format_yyyyMMdd(long timeLong) {
        Date date = new Date(timeLong);
        StringBuilder time = new StringBuilder();
        time.append(date.getYear() + 1900);
        time.append("/");
        time.append(date.getMonth() + 1);
        time.append("/");
        time.append(date.getDate());
//        time.append("-");
//        time.append(date.getHours());
//        time.append(":");
//        time.append(date.getMinutes());
//        time.append(":");
//        time.append(date.getSeconds());
        return time.toString();
    }
}
