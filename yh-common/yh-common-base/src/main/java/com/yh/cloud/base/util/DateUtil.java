package com.yh.cloud.base.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static LocalDate stringToDate(String s){
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if(s == null){
            return null;
        }
        return LocalDate.parse(s,df);
    }

    public static LocalDateTime stringToDateTime(String s){
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if(s == null){
            return null;
        }
        return LocalDateTime.parse(s,df);
    }
}
