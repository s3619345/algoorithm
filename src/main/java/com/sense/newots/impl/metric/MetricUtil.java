package com.sense.newots.impl.metric;


import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.util.concurrent.AtomicLongMap;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sense.newots.request.RosterResultW;
import com.sense.newots.object.entity.ActivityInfo;
import com.sense.newots.object.entity.RosterBatchInfo;
import com.sense.newots.object.entity.RosterInfo;
import com.sense.newots.util.RedisUtil;
import com.sense.newots.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;


@Slf4j
public class MetricUtil {

    public static Gson gson = new Gson();
    public static Type typeDBCol = new TypeToken<List>() {
    }.getType();
    public static Type typeList = new TypeToken<List>() {
    }.getType();
    public static Type mapList = (new TypeToken<Map>() {
    }).getType();

    public static ConcurrentHashMap<String, com.sense.newots.impl.metric.ActivityMetric> actMetrics = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<String, com.sense.newots.impl.metric.BatchMetric> batchMetrics = new ConcurrentHashMap();
    // public static ConcurrentHashMap<String,ConcurrentHashMap<String, BatchMetric>> batchMetrics1 = new ConcurrentHashMap();
    //public static List<RosterBatchInfo> completeBatchs = new CopyOnWriteArrayList();

    public static Cache<String, List<Map<Object, Object>>> cache = CacheBuilder.newBuilder()
            .maximumSize(2000)
            .expireAfterWrite(60, TimeUnit.SECONDS)
            .build();

    public static Cache<String, List<Map<Object, Object>>> gen = CacheBuilder.newBuilder()
            .maximumSize(2000)
            .expireAfterWrite(60, TimeUnit.SECONDS)
            .build();

    public static Cache<String, String> callee = CacheBuilder.newBuilder()
            .maximumSize(2000)
            .expireAfterWrite(600, TimeUnit.SECONDS)
            .build();

    public static Cache<String, Integer> sendCount = CacheBuilder.newBuilder()
            .maximumSize(200)
            .expireAfterWrite(60, TimeUnit.SECONDS)
            .build();

    public static AtomicLongMap<String> isbreak = AtomicLongMap.create();

    public static Queue<RosterBatchInfo> completeBatchs = new ConcurrentLinkedQueue();
    //监控统计
    public static ConcurrentHashMap<String, LongAdder> actOutCallNum = new ConcurrentHashMap<String, LongAdder>();
    public static AtomicLongMap<String> actAnswerCallNum = AtomicLongMap.create();
    public static ConcurrentHashMap<String, LongAdder> batOutCallNum = new ConcurrentHashMap<String, LongAdder>();
    public static AtomicLongMap<String> batAnswerCallNum = AtomicLongMap.create();
    public static AtomicLongMap<String> fail1Num8 = AtomicLongMap.create();
    public static AtomicLongMap<String> fail2Num6 = AtomicLongMap.create();
    public static AtomicLongMap<String> fail3Num1_9 = AtomicLongMap.create();
    public static AtomicLongMap<String> fail5Num7 = AtomicLongMap.create();
    public static AtomicLongMap<String> fail4Num11 = AtomicLongMap.create();
    public static AtomicLongMap<String> failOtherNum = AtomicLongMap.create();
    public static AtomicLongMap<String> ivrResultNum = AtomicLongMap.create();

    public static String[] keys = {"中国移动", "中国联通", "中国电信"};
    public static String serverIp = null;

    static {
        //获取服务器的IP地址
        InetAddress address = getCurrentIp();
        serverIp = address.getHostAddress();
    }

    public static void clear() {
        completeBatchs.clear();
        actMetrics.clear();
        batchMetrics.clear();
        //分片，并发下降低锁的竞争
        actOutCallNum.clear();
        actAnswerCallNum.clear();
        batOutCallNum.clear();
        batAnswerCallNum.clear();
        fail1Num8.clear();
        fail2Num6.clear();
        fail3Num1_9.clear();
        fail4Num11.clear();
        fail5Num7.clear();
        ivrResultNum.clear();

    }

    public static InetAddress getCurrentIp() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) networkInterfaces.nextElement();
                Enumeration<InetAddress> nias = ni.getInetAddresses();
                while (nias.hasMoreElements()) {
                    InetAddress ia = (InetAddress) nias.nextElement();
                    if (!ia.isLinkLocalAddress() && !ia.isLoopbackAddress() && ia instanceof Inet4Address) {
                        return ia;
                    }
                }
            }
        } catch (SocketException e) {
            log.info("获取服务器IP地址有误:{}", e.getStackTrace());
        }
        return null;
    }

    public static synchronized void addRostersDay(String activityName, ActivityInfo activityInfo, String domain, int rosterNum, String batchId) {
        String name = activityName + ":" + domain;
        com.sense.newots.impl.metric.ActivityMetric metric = (com.sense.newots.impl.metric.ActivityMetric) actMetrics.get(name);
        if (metric == null) {
            metric = new com.sense.newots.impl.metric.ActivityMetric();
            metric.setDomain(domain);
            metric.setActivityName(activityName);
            metric.setStatus(activityInfo.getStatus());
            metric.setPrefix(activityInfo.getPrefix());
            metric.setTrunkGrp(activityInfo.getTrunkGrp());
            metric.setMaxCall(activityInfo.getMaxCapacity());
            actMetrics.put(name, metric);
            log.info(name + " |" + activityName + "| " + " is null create activity metric !");
        }
        //模板今日名单数
        metric.addRosterNumDay(rosterNum);
        //模板今日批次数
        metric.addCompleteBatchNumDay();

        String bname = batchId + ":" + activityName;
        com.sense.newots.impl.metric.BatchMetric batchMetric = (com.sense.newots.impl.metric.BatchMetric) MetricUtil.batchMetrics.get(bname);
        if (batchMetric == null) {
            batchMetric = new com.sense.newots.impl.metric.BatchMetric();
            batchMetric.setActivityName(activityName);
            batchMetric.setBatchName(batchId);
            batchMetric.setDomain(domain);
            batchMetric.setRoterNum(rosterNum);
            batchMetric.setRound(1);
            batchMetrics.put(bname, batchMetric);
//            batchMetrics1.put(activityName,batchMetrics);
        }

    }

    public static void addCallOut(List<RosterInfo> infoList, ActivityInfo activityInfo, int rosterNum, int callRound) {

        RosterInfo info = infoList.get(0);
        String name = info.getActivityName() + ":" + info.getDomain();
        com.sense.newots.impl.metric.ActivityMetric metric = (com.sense.newots.impl.metric.ActivityMetric) actMetrics.get(name);
        if (metric == null) {
            metric = new com.sense.newots.impl.metric.ActivityMetric();
            metric.setDomain(info.getDomain());
            metric.setActivityName(info.getActivityName());
            metric.setMaxCall(activityInfo.getMaxCapacity());
            metric.setTemplateName(activityInfo.getRosterTemplateName());
            metric.setStatus(activityInfo.getStatus());
            metric.setBatchNum(activityInfo.getBatchNum());
            metric.setRoterNum(activityInfo.getRosterNum());
            metric.setPrefix(activityInfo.getPrefix());
            metric.setTrunkGrp(activityInfo.getTrunkGrp());

            actMetrics.put(name, metric);
            log.info(name + " |" + info.getId() + "| " + " is null create activity metric !");
        }
        //metric.addOutCallNum();
        //模板外呼数量 LongAdder add
        actOutCallNum.computeIfAbsent(name, k -> new LongAdder()).add(infoList.size());
        metric.setCurrentBatch(info.getBatchName());
        metric.setCurrentRound(callRound);
        String bname = info.getBatchName() + ":" + info.getActivityName();
        bname = bname.trim();
        com.sense.newots.impl.metric.BatchMetric bmetric = (com.sense.newots.impl.metric.BatchMetric) batchMetrics.get(bname);

        if (bmetric == null) {
            bmetric = new com.sense.newots.impl.metric.BatchMetric();
            bmetric.setDomain(info.getDomain());
            bmetric.setActivityName(info.getActivityName());
            bmetric.setRound(info.getCallRound());
            bmetric.setBatchName(info.getBatchName());
            bmetric.setRoterNum(rosterNum);
            batchMetrics.put(bname, bmetric);
            log.info(bname + " |" + info.getId() + "| " + " is null create batch metric !");
        }

        //bmetric.addOutCallNum();

        //批次外呼数量
        batOutCallNum.computeIfAbsent(bname, k -> new LongAdder()).add(infoList.size());

        log.info(" ### " + info.getBatchName() + "| " +
                info.getId() + "|" + " add outcallnum [" + bmetric.getOutCallNum() + "]");
    }

    public static synchronized void addCallOut(RosterInfo info, RosterBatchInfo batchInfo) {
        String name = info.getActivityName() + ":" + info.getDomain();
        com.sense.newots.impl.metric.ActivityMetric metric = (com.sense.newots.impl.metric.ActivityMetric) actMetrics.get(name);
        if (metric == null) {
            metric = new com.sense.newots.impl.metric.ActivityMetric();
            metric.setDomain(info.getDomain());
            metric.setActivityName(info.getActivityName());
            actMetrics.put(name, metric);
            log.info(name + " |" + info.getId() + "| " + " is null create activity metric !");
        }
        metric.addOutCallNum();
        metric.setCurrentBatch(info.getBatchName());
        metric.setCurrentRound(info.getCallRound());
        String bname = info.getBatchName() + ":" + info.getActivityName();
        bname = bname.trim();
        com.sense.newots.impl.metric.BatchMetric bmetric = (com.sense.newots.impl.metric.BatchMetric) batchMetrics.get(bname);
        if (bmetric == null) {
            bmetric = new com.sense.newots.impl.metric.BatchMetric();
            bmetric.setDomain(info.getDomain());
            bmetric.setActivityName(info.getActivityName());
            bmetric.setRound(info.getCallRound());
            bmetric.setBatchName(info.getBatchName());
            batchMetrics.put(bname, bmetric);
            log.info(bname + " |" + info.getId() + "| " + " is null create batch metric !");
        }

        bmetric.addOutCallNum();
        log.info(" ### " + info.getBatchName() + "| " +
                info.getId() + "|" + " add outcallnum [" + bmetric.getOutCallNum() + "]");
    }


    public static synchronized void addCompleteBatchInfo(RosterBatchInfo info) {
        String bname = info.getBatchId() + ":" + info.getDomain();
        log.info(" ## " + bname + " all roster call out waiting for complete !");
        completeBatchs.add(info);
    }

    //接收通话结果统计
    public static void addCallResults(RosterResultW info) {
        String name = info.getActivity_id() + ":" + info.getDomain();
        String bname = info.getBatch_id() + ":" + info.getActivity_id();

        if (info.getResultCode() == 0) {
            actAnswerCallNum.incrementAndGet(name);
            batAnswerCallNum.incrementAndGet(bname);
        } else if (info.getResultCode() == 8) {
            fail1Num8.incrementAndGet(bname);
        } else if (info.getResultCode() == 6) {
            fail2Num6.incrementAndGet(bname);
        } else if ((info.getResultCode() == 1) || (info.getResultCode() == 9)) {
            fail3Num1_9.incrementAndGet(bname);
        } else if (info.getResultCode() == 7) {
            fail5Num7.incrementAndGet(bname);
        } else if (info.getResultCode() == 11) {
            fail4Num11.incrementAndGet(bname);
        } else {
            failOtherNum.incrementAndGet(bname);
        }
    }

    public static synchronized void addCallResult(RosterResultW info) {
        String name = info.getActivity_id() + ":" + info.getDomain();
        com.sense.newots.impl.metric.ActivityMetric metric = (com.sense.newots.impl.metric.ActivityMetric) actMetrics.get(name);
        if (metric == null) {
            metric = new com.sense.newots.impl.metric.ActivityMetric();
            metric.setDomain(info.getDomain());
            metric.setActivityName(info.getActivity_id());
            actMetrics.put(name, metric);
            log.info(name + " |" + info.getRosterinfo_id() + "| " + " is null create activity metric !");
        }
        if (info.getResultCode() == 0) {
            metric.addAnswerCallNum();

        }

//        if (info.getRound() == 0) {
//            info.setRound(1);
//        }
        String bname = info.getBatch_id() + ":" + info.getActivity_id();
        bname = bname.trim();
        com.sense.newots.impl.metric.BatchMetric bmetric = (com.sense.newots.impl.metric.BatchMetric) batchMetrics.get(bname);
        if (bmetric == null) {
            bmetric = new com.sense.newots.impl.metric.BatchMetric();
            bmetric.setDomain(info.getDomain());
            bmetric.setActivityName(info.getActivity_id());
            bmetric.setRound(info.getRound());
            bmetric.setBatchName(info.getBatch_id());
            batchMetrics.put(bname, bmetric);

            log.info(bname + " |" + info.getRosterinfo_id() + "| " + " is null create batch metric !");
        }
        if (info.getResultCode() == 0)
            bmetric.addAnswerCallNum();

        else if (info.getResultCode() == 8)
            bmetric.addFail1Num();

        else if (info.getResultCode() == 6)
            bmetric.addFail2Num();

        else if ((info.getResultCode() == 1) ||
                (info.getResultCode() == 9))
            bmetric.addFail3Num();

        else if (info.getResultCode() == 7)
            bmetric.addFail5Num();

        else if (info.getResultCode() == 11)
            bmetric.addFail4Num();
        else
            bmetric.addFailOtherNum();
    }

    public static com.sense.newots.impl.metric.ActivityMetric getActMetrics(String activityName, String prefix, String domain) {
        String name = activityName + ":" + domain;
        com.sense.newots.impl.metric.ActivityMetric metric = (com.sense.newots.impl.metric.ActivityMetric) actMetrics.get(name);
        if (StringUtil.isNotEmpty(metric)) {
            if (StringUtil.isEmpty(actOutCallNum.get(name))) {
                metric.setOutCallNum(0);
            } else {
                metric.setOutCallNum(actOutCallNum.get(name).intValue());
            }
            metric.setCallRate(RedisUtil.getActivityKeys(activityName));
            metric.setAnswerCallNum((int) actAnswerCallNum.get(name));
        }
        return metric;
    }


    public static void main(String[] args) {

        for (int i = 0; i < 10000; i++) {

            batchMetrics.put(i + "qwuei", new com.sense.newots.impl.metric.BatchMetric());

        }
        long s = System.currentTimeMillis();

        for (Map.Entry<String, com.sense.newots.impl.metric.BatchMetric> entry : MetricUtil.batchMetrics.entrySet()) {
            if (entry.getKey().indexOf("500000qwuei") > -1) {
                System.out.println("indexOf" + (System.currentTimeMillis() - s));

            }
        }

        System.out.println(System.currentTimeMillis() - s);
    }
}
