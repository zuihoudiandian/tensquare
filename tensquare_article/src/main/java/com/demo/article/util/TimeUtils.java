package com.demo.article.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TimeUtils {

    public static Date formatNow(String pattern) {
        LocalDateTime time = LocalDateTime.now();
        String fixtime = time.format(DateTimeFormatter.ofPattern(pattern));
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        Date now = null;
        try {
            now = dateFormat.parse(fixtime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return now;
    }

//    @Test
//    public  void tset1(){
//        Date date = TimeUtils.formatNow("yyyy-MM-dd hh:mm:ss");
//        System.out.println(date.getTime());
//    }
}
