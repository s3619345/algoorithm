package com.sense.newots.redis;

public abstract interface RedisTag
{
  public static abstract interface Activity
  {
    public static final String IDFA = "idfa";
    public static final String LOGIN_TRY_TIME = "login_try_time";
    public static final String LIULIANG_RESENDCARD = "liuliang";
    public static final String VALENTINES = "valentines";
    public static final String CONTACTERS = "contacters";
    public static final String PRIORITY = "priority";
    public static final String CONTACTERS_INIT_TIME = "contacters_init_time";
    public static final String SEND_LIBAO_TIME = "send_libao_time";
    public static final String SMS_LIBAO_USER = "sms_libao_user_list";
    public static final String USER_SEND_LIBAO_TIMES = "user_send_libao_times";
    public static final String ONEDAY_SEND_LIBAO_COUNT = "oneday_send_libao_count";
    public static final String ONE_DAY_LUCK_USER_COUNT = "one_day_luck_user_count";
    public static final String LUCK_USER_LIST = "luck_user_list";
    public static final String BACKUP_LUCK_USER_LIST = "backup_luck_user_list";
  }
}
