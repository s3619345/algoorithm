package com.sense.newots.object.util;

import java.sql.Timestamp;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;

public class TimeUtil
{
   static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
   static SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

   public static String getCurrentTimeStr() {
       String ctime = formatter2.format(new Date());
       return ctime;
   }

   public static String getCurrentDateStr() {
       String ctime = formatter.format(new Date());
       return ctime;
   }

   public static String formatCurrentTimeStr(Long microSeconds) {
       microSeconds = Long.valueOf(microSeconds.longValue() / 1000L);
       Timestamp stamp = new Timestamp(microSeconds.longValue());
       return stamp.toString();
   }

   public static long getTimeEclipse(Long sTime, Long eTime) {
       if (eTime.longValue() < sTime.longValue()) {
           return 0L;
       }
       Long time = Long.valueOf(eTime.longValue() - sTime.longValue());
       time = Long.valueOf(time.longValue() / 1000000L);
       return time.longValue();
   }

   public static long getTimeEclipse(String sTime, String eTime) {
       Timestamp stamp = Timestamp.valueOf(sTime);
       Timestamp etamp = Timestamp.valueOf(eTime);
       if (etamp.getTime() < stamp.getTime()) {
           return 0L;
       }
       Long time = Long.valueOf(etamp.getTime() - stamp.getTime());
       time = Long.valueOf(time.longValue() / 1000L);
       return time.longValue();
   }
}