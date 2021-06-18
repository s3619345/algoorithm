package com.sense.newots.util;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.sense.newots.impl.metric.MetricUtil;
import com.sense.newots.object.GetBeanUtil;
import com.sense.newots.object.entity.RosterInfo;
import com.sense.newots.object.service.RosterService;
import com.sense.newots.redis.RedisDevice;
import com.sense.newots.request.MakeCall;
import com.sense.newots.service.UseTransactionService;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.JedisPubSub;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Slf4j
public class RedisUtil {

    public static RedisDevice redisDevice = null;

    private static UseTransactionService useTransactioDao = GetBeanUtil.getBean(UseTransactionService.class);
    private static RosterService rosterDao = GetBeanUtil.getBean(RosterService.class);
    static Type type = new TypeToken<RosterInfo>() {
    }.getType();

    static Gson gson = new Gson();


    public static void init() {
        redisDevice = new RedisDevice();
    }

    //得到ActivityName,BatchName,Id 缓存在redis里 并把它的状态改为1 (1：进行中，0：未开始)
    public static void addRoster(RosterInfo info) {
        String rosterId = info.getActivityName() + "_" + info.getBatchName() + "_roster_" + info.getId();
        boolean result = redisDevice.setCallRoster(rosterId, gson.toJson(info));
        log.info("### key-[" + rosterId + "] add result " + result);
        if (result) {
            expireRoster(info);
        }
        info.setStatus(1);
        //info.setCallRound(info.getCallRound()+1);
        //rosterDao.updateRosterInfo(info);
        // useTransactionService.updateRosterInfo(info);
    }

    //外呼数据自增
    public static void incrRedisRoster(RosterInfo info, String caller, String callee) {

        String rosterId = info.getActivityName() + "_" + info.getBatchName();
        boolean result = redisDevice.incr(rosterId);
        log.info("### key-[" + rosterId + "] incr result " + result);
        if (result) {
            expireRedisRoster(rosterId);
        }
        info.setStatus(1);
        String prefix = caller.substring(0, caller.length() - 11);
        String address = info.getAddress().concat(",").concat(prefix);
        //  rosterDao.updateStatus(info.getId(), address, callee);
    }

    //外呼数据阶梯加
    public static void incrbyRedisRoster(List<RosterInfo> info, List<MakeCall> callList) {
        String rosterId = MetricUtil.serverIp + "_" + info.get(0).getActivityName() + "_" + info.get(0).getBatchName();
        boolean result = redisDevice.incrBy(rosterId, (long) info.size());
        log.info("### key-[" + rosterId + "] incr result " + result);
        if (result) {
            expireRedisRoster(rosterId);
        }
        //rosterDao.updateStatus(info.get(0).getBatchName(),min,max,","+prefix);

        rosterDao.updateJdbcRosterInfo(callList);
    }

    //根据key自减
    public static Long decr(String activityName, String batchName) {
        String key = MetricUtil.serverIp + "_" + activityName + "_" + batchName;
        Long num = redisDevice.decr(key);
        log.info("### 删除-[" + key + "] decr result " + num);
        return num;
    }

    public static void updateRoster(RosterInfo info) {
        String rosterId = info.getActivityName() + "_" + info.getBatchName() + "_roster_" + info.getId();
        boolean result = redisDevice.setCallRoster(rosterId, gson.toJson(info));
        log.info("### key-[" + rosterId + "] update result " + result);
    }

    public static void expireRoster(RosterInfo info) {
        String rosterId = info.getActivityName() + "_" + info.getBatchName() + "_roster_" + info.getId();
        boolean result = redisDevice.expireCallRoster(rosterId);
        log.info("### key-[" + rosterId + "] expire 120s result " + result);
    }

    //设置key的过期时间
    public static void expireRedisRoster(String rosterId) {
        boolean result = redisDevice.expireRosterId(rosterId);
        log.info("### key-[" + rosterId + "] expire 120s result " + result);
    }

    //删除缓存
    public static void delRoster(RosterInfo info) {
        String rosterId = info.getActivityName() + "_" + info.getBatchName() + "_roster_" + info.getId();
        Long result = redisDevice.delCallRoster(rosterId);
        log.info("### key-[" + rosterId + "] delete result " + result);
    }

    //得到redis里的缓存信息
    public static RosterInfo getRoster(String activityName, String batchName, String roster_id) {
        String rosterId = activityName + "_" + batchName + "_roster_" + roster_id;
        String result = redisDevice.getCallRoster(rosterId);
        log.info("### key-[" + rosterId + "] get result " + result);
        if (result == null) {
            return null;
        }
        RosterInfo info = (RosterInfo) gson.fromJson(result, type);
        return info;
    }

    //统计批次在redis里的数据
    public static int getKeys(String activityName, String batchId) {
        String rosterId = MetricUtil.serverIp + "_" + activityName + "_" + batchId;
        String redisNum = redisDevice.getKey(rosterId);
        int num = 0;
        if (redisNum != null) {
            num = Integer.parseInt(redisNum);
        }
        return num;
    }

    //统计模板在redis里的数据 实时并发
    public static int getActivityKeys(String activityName) {
        String rosterId = MetricUtil.serverIp + "_" + activityName + "_*";
        Set<String> keyList = redisDevice.getkeysNum(rosterId);
        int num = 0;
        if (StringUtil.isNotEmpty(keyList)) {
            for (String keys : keyList) {
                String str = redisDevice.getKey(keys);
                if (StringUtil.isNotEmpty(str)) {
                    Integer redisNum = Integer.parseInt(str);
                    num += redisNum;
                }

            }
        }
//        keys = keys.replaceAll("\\[", "").repl aceAll("\\]", "");
//        String redisNum = redisDevice.getKey(keys);
//        if (redisNum != null){
//            num = Integer.parseInt(redisNum);
//        }
        return num;
    }

    public static void del(String batchName) {
        boolean result = redisDevice.delBatchId(batchName);
        log.info("### 删除-[" + batchName + "] delete result " + result);
    }

    public static boolean incr(String batchName) {
        return redisDevice.incr(batchName);
    }

    public static void incrBy(String batchName, Long value) {
        redisDevice.incrBy(batchName, value);
    }

    public static String get(String batchName) {
        String arr = redisDevice.getKey(batchName);
        if (StringUtil.isEmpty(arr)) {
            return "0";
        }
        return arr;
    }

    public static void set(String key, String value) {
        redisDevice.set(key, value);
    }


    public static void expireSet(String key, String value) {
        redisDevice.set(key, value);
        expireRedisRoster(key);
    }


    public static void sublishMsg(JedisPubSub jedisPubSub, String channels) {
        redisDevice.sublishMsg(jedisPubSub, channels);
    }

    // 获取在哈希表中指定 key 的所有字段和值
    public static Map<String, String> hgetAll(String key) {
        Set<String> keys = redisDevice.keys("*" + key + "*");
        if (StringUtil.isNotEmpty(keys)) {
            log.info("hgetAll keys :" + keys);
            return redisDevice.hgetAll(keys.iterator().next());
        }
        return null;
    }

    //删除标签结果
    public static void del() {
        Set<String> keys = redisDevice.keys("5888*");
        for (String key : keys
        ) {
            redisDevice.del(key);
        }
        Set<String> key9s = redisDevice.keys("5999*");
        for (String k9 : key9s
        ) {
            redisDevice.del(k9);
        }
    }
}