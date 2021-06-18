package com.sense.newots.object;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.sense.newots.httpClient.CallUtil;
import com.sense.newots.httpClient.OkHttpClientUtils;
import com.sense.newots.impl.metric.MetricUtil;
import com.sense.newots.object.conf.InitConfig;
import com.sense.newots.object.entity.*;
import com.sense.newots.object.service.*;
import com.sense.newots.object.util.TimeUtil;
import com.sense.newots.request.MakeCall;
import com.sense.newots.util.HashMapProxy;
import com.sense.newots.util.HttpUtil;
import com.sense.newots.util.RedisUtil;
import com.sense.newots.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.sense.newots.impl.metric.MetricUtil.gson;


//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//
@Slf4j
public class AutoCallThread implements Runnable {
    private AtomicInteger status = new AtomicInteger(0);
    //private String taskType;
    private String activityName;
    private String domain;
    private int callLimit;
    private String dst;
    //private String dstName;
    private String prefix;
    private Map<String, String> prefixMap;
    /*private List<OutboundRecallPolicy> rounds;
    private int trunkSize;
    private int trunkIndex = 0;*/
    private TrunkNumber trunkNumber = null;
    private ActivityInfo activityInfo;
    private OutboundPolicyInfo policyInfo;

    private ConfigParamService configDao = GetBeanUtil.getBean(ConfigParamService.class);
    private ActivityInfoService activityDao = GetBeanUtil.getBean(ActivityInfoService.class);
    private RosterBatchService batchDao = GetBeanUtil.getBean(RosterBatchService.class);
    private RosterService rosterDao = GetBeanUtil.getBean(RosterService.class);
    private TrunkNumberService trunkNumberDao = GetBeanUtil.getBean(TrunkNumberService.class);
    private TrunkPoolCorrService trunkPoolCorrDao = GetBeanUtil.getBean(TrunkPoolCorrService.class);
    private BatchRecordService batchRecordDao = GetBeanUtil.getBean(BatchRecordService.class);
    private ExecutorService executorService = GetBeanUtil.getBean(ExecutorService.class);

    public AutoCallThread(ActivityInfo _activity, OutboundPolicyInfo _policyInfo) {
        //this.taskType = type;
        this.setActivityInfo(_activity);//一个模板相当于一个线程
        this.setPolicyInfo(_policyInfo);//外呼策略
        //this.setName(this.activityName);//起的一个模板名称
        this.initParams();
    }

    /*private TrunkNumber getAvaiableTrunk() {
        if (this.trunkSize <= 0) {
            return null;
        } else {
            // TODO: 2018/5/28 0028 1.线路负载 2.线路可用
            if (this.trunkIndex >= this.trunkSize) {
                this.trunkIndex %= this.trunkSize;
            }
            return  this.trunks.get(this.trunkIndex);
        }
    }*/

    private String getUUIInfo(RosterInfo rInfo) {

        String[] uuis = this.getActivityInfo().getUui().split(",");
       /* if (uuis.length <= 0) {
            return "";
        }*/

        Map<String, String> customerFields = JSONObject.parseObject(rInfo.getCustomFields(), HashMapProxy.class);
        ((HashMapProxy) customerFields)
                .putObject("phoneNum1", rInfo.getPhoneNum1())
                .putObject("lastname", rInfo.getLastname())
                .putObject("firstname", rInfo.getFirstname())
                .putObject("age", rInfo.getAge() + "")
                .putObject("sex", rInfo.getSex());
            /*Map<String, String> customerFields = (Map) gson.fromJson(rInfo.getCustomFields(), MetricUtil.mapList);
            String corrUUi = "";
            Map<String, String> uuiMap = new MapBuilder<String, String>().put("phoneNum1", rInfo.getPhoneNum1())
                    .put("phoneNum2", rInfo.getPhoneNum2()).put("phoneNum3", rInfo.getPhoneNum3())
                    .put("phoneNum4", rInfo.getPhoneNum4()).put("phoneNum5", rInfo.getPhoneNum5())
                    .put("lastname", rInfo.getLastname()).put("firstname", rInfo.getFirstname())
                    .put("age", rInfo.getAge() + "").put("sex", rInfo.getSex())
                    .put("customerId", rInfo.getCustomerId()).put("address", rInfo.getAddress())
                    .put("email", rInfo.getEmail()).put("cardType", rInfo.getCardType())
                    .put("cardNum", rInfo.getCardNum()).put("sex", rInfo.getSex())
                    .putAll(customerFields).build();*/


        /*for (int var = 0; var < uuis.length; ++var) {
            corrUUi = corrUUi + customerFields.get(uuis[var]) + "_";
        }
        corrUUi = corrUUi.substring(0, corrUUi.length() - 1);*/

        /*StringBuffer corrUUi = new StringBuffer();
        for (int var = 0, len = uuis.length; var < len; var++) {
            if (var == uuis.length - 1) {
                corrUUi.append(customerFields.get(uuis[var]));
            } else {
                corrUUi.append(customerFields.get(uuis[var])).append("_");
            }

        }*/
        String corrUUi = IntStream.range(0, uuis.length)
                .boxed().map(e -> customerFields.get(uuis[e]))
                .collect(Collectors.joining("_"));

        customerFields.clear();

        return corrUUi;
    }


    private void initParams() {
        //外呼策略dst
        String[] steps = this.getPolicyInfo().getCallAnswerStep().split("[|]");
        if (steps.length >= 3) {
            String dstName = steps[2];
            ConfigParam corr = this.configDao.findByName(dstName);
            if (corr != null) {
                this.dst = corr.getValue().trim();
                log.info("## param config dst number " + this.dst);
            } else {
                log.info("## param config name error " + dstName);
            }
        } else {
            log.info("## param answer setp error " + this.getPolicyInfo().getCallAnswerStep());
        }


        //获取线路号码trunkNumber
        List<TrunkPoolCorr> corr1 = this.trunkPoolCorrDao.getTTrunkPoolCorrs(this.getActivityInfo().getTrunkGrp());
        if (StringUtil.isNotEmpty(corr1)) {
            corr1.stream().forEach(e -> {
                TrunkNumber trunk = this.trunkNumberDao.findbyNum(e.getDisplayNum());
                if (StringUtil.isNotEmpty(trunk)) {
                    this.trunkNumber = trunk;
                }
            });
        }

        //外呼策略
        //this.rounds = this.recallPolicyDao.getTOutboundRecallPolicys(this.domain, this.getActivityInfo().getName(), 0, 10);
    }

    /*private String getRoundPhone(int round) {
        if (this.rounds != null) {
            Iterator var3 = this.rounds.iterator();
            while (var3.hasNext()) {
                OutboundRecallPolicy policy = (OutboundRecallPolicy) var3.next();
                if (policy.getRound() == round) {
                    return policy.getPhoneNumCol();
                }
            }
        }

        return "phoneNum1";
    }*/

    private void waitTime(long mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            log.error("InterruptedException:{}", e);
        }
    }

    @Override
    public void run() {
        //long startTime = System.currentTimeMillis();
        log.info("##### taskthread [" + this.getActivityInfo().getName() + ":" + this.getActivityInfo().getDomain() + "] start to run ");

        AutoCallThread preCallThread = TaskContainer.autoCallActivityMap.remove(this.activityName);
        if (null != preCallThread) {
            preCallThread.stopTask();
            preCallThread = null;
        }
        TaskContainer.autoCallActivityMap.put(this.activityName, this);

        if (this.status.get() == 0) {
            this.status.set(1);
        }
        end:
        while (this.status.get() != 3) {
            //RosterBatchInfo batchInfo = null;

            loop:
            while (status.get() == 1) {

                LocalDateTime today = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00");
                String todayStartTime = formatter.format(today);
                String todayEndTime = todayStartTime.replace("00:00:00", "23:59:59");
                List<RosterBatchInfo> list = this.batchDao.findNextUncallBatch(todayStartTime, todayEndTime, this.getActivityInfo().getRosterTemplateName());
                if (StringUtil.isNotEmpty(list)) {
                    for (RosterBatchInfo batchInfo : list) {
                        //集群环境下，先竞争（是否成功更新）
                        if (this.batchDao.updateRosterBatchByStatus(TimeUtil.getCurrentTimeStr(), batchInfo.getBatchId())) {
//                      log.info("## find batchInfo " + batchInfo.toString());
                            this.batchRecordDao.updateRecordTime(TimeUtil.getCurrentTimeStr(), batchInfo.getBatchId(), this.callLimit, this.prefix, trunkNumber.getTrunkGrp());
                            batchInfo.setStartTime(TimeUtil.getCurrentTimeStr());
                            //批次不为空则查询：根据拨打轮数确定当前拨打的线路前缀prefix
                            List<String> pf = Splitter.on(",").omitEmptyStrings().splitToList(this.getActivityInfo().getPrefix());
                            prefix = pf.get(batchInfo.getCurrentFinishedRound() % pf.size());
                            if (this.prefix.contains("|")) {
                                String[] prefixList = this.prefix.split("\\|");

                                prefixMap = IntStream.range(0, prefixList.length)
                                        .boxed().filter(x -> StringUtil.isNotEmpty(prefixList[x]))
                                        .collect(Collectors.toMap(i -> MetricUtil.keys[i], i -> prefixList[i]));
                            }

                            //查询批次-拨打数据
                            long id = 0;
                            List<RosterInfo> rosters;//= this.rosterDao.findBatchRosters(this.domain, batchInfo.getBatchId(), this.callLimit);
                            while (StringUtil.isNotEmpty(
                                    rosters = this.rosterDao.findBatchRosters(batchInfo.getBatchId(), this.callLimit - RedisUtil.getActivityKeys(this.getActivityInfo().getName()) <= 0 ? 1 : this.callLimit - RedisUtil.getActivityKeys(this.getActivityInfo().getName()), id)
                            )) {
                                //调用拨打策略
                                Integer count = this.batchDao.checkTemplate(batchInfo.getTemplateName());
                                if (count != 0) {
                                    try {
                                        String rsp = OkHttpClientUtils.easyPost(InitConfig.STRATEGY_URL, gson.toJson(rosters));
                                        if (!rsp.contains("empty")) {
                                            rosters = JSONObject.parseArray(rsp, RosterInfo.class);
                                        }
                                        log.info(batchInfo.getTemplateName() + ": 调用拨打策略成功");
                                    } catch (Exception e) {
                                        log.info("调用拨打策略失败:" + e);
                                    }
                                }

                                log.info("# find roster " + batchInfo + " size [" + rosters.size() + "]");
                                //get rosters 最大最小id
                                long min = rosters.get(0).getId();
                                id = Iterables.getLast(rosters).getId();

                                //异步调短信和calllist 封装
                                List<MakeCall> callList = rosters.stream().map(info ->
                                        callOutRoster(info, trunkNumber, batchInfo)
                                ).collect(Collectors.toList());
                                for (MakeCall makeCall : callList) {
                                    //                          //redis 阶梯加和status =1 更新
                                    String caller=makeCall.getCaller();
                                    makeCall.setCaller(","+makeCall.getCaller().substring(0,caller.length()-11));

                                }

                                RedisUtil.incrbyRedisRoster(rosters, callList);
                                //makecall list
                                executorService.submit(() -> {
                                    //添加redis自增
                                    //发送ces list 分段
                                    String result = CallUtil.makeCall(callList);

                                    if (result == null) {
                                        log.info("## 结果。。。。。。。" + result);
                                        // useTransactionService.updateRosterFailInfo(info.getId());
                                        // RedisUtil.decr(info.getActivityName(),info.getBatchName());
                                    }
                                });
                                //add 计算
                                MetricUtil.addCallOut(rosters, this.getActivityInfo(), batchInfo.getRoterNum(), batchInfo.getCallRound());


                                // 判断模板并发数
                                int currentCallnum;
                                while ((currentCallnum = RedisUtil.getActivityKeys(this.getActivityInfo().getName())) >= callLimit) {
                                    log.info(batchInfo.getBatchId() + "## out of call limt " + this.callLimit);
                                    waitTime(100);
                                    // currentCallnum = RedisUtil.getActivityKeys(getActivityInfo().getName());
                                    if (status.get() != 1) {
                                        break;
                                    }
                                }

                                //模板暂停超12小时 break
                                int mills = 0;
                                while (status.get() == 2) {
                                    waitTime(1000);
                                    mills++;
                                    // 43200
                                    if (mills == 43200) {
                                        log.info("模板暫停超過12个小时 " + batchInfo.getBatchId() + "清空 size()--" + rosters.size());
                                        rosters.clear();
                                        break loop;
                                    }
                                /*if (mills > 86400) {
                                    //rosters.clear();
                                    break loop;
                                }*/
                                }

                                //3 模板任务直接结束，节约系统资源
                                if (status.get() == 3) {
                                    break end;
                                }

                                //本次while clear roster
                                rosters.clear();

                                RosterBatchInfo batchInfo1 = batchDao.findById(batchInfo.getBatchId());
                                if (batchInfo1.getStatus() != 1) {
                                    break loop;
                                } else {
                                    List<String> pfLoop = Splitter.on(",").omitEmptyStrings().splitToList(this.getActivityInfo().getPrefix());
                                    prefix = pfLoop.get(batchInfo.getCurrentFinishedRound() % pfLoop.size());
                                    if (this.prefix.contains("|")) {
                                        String[] prefixList = this.prefix.split("\\|");
                                        prefixMap = IntStream.range(0, prefixList.length)
                                                .boxed().filter(x -> StringUtil.isNotEmpty(prefixList[x]))
                                                .collect(Collectors.toMap(i -> MetricUtil.keys[i], i -> prefixList[i]));
                                    }
                                }

                            }

                            if (StringUtil.isEmpty(rosters)) {
                                log.info("## roster batch all call out complete " + batchInfo.getBatchId());
                                batchInfo.setStatus(2);
                                MetricUtil.addCompleteBatchInfo(batchInfo);
                                //this.batchDao.updateRosterBatchInfo(batchInfo);
                                continue;
                            }

                        }
                    }
                } else {
                    //finishBatch = true;
                    //log.info("## no batchInfo ");
                    waitTime(10000);

                }
            }
            waitTime(3000);
        }

        AutoCallThread newCallThread = TaskContainer.autoCallActivityMap.get(this.activityName);
        if (null != newCallThread) {
            log.info("---###### activity preCallThread excute complete ");

            if (newCallThread.getStatus() == 3) {
                log.info("---###### activity newCallThread " + this.getActivityInfo() + " excute complete " + " costtime xs");
                this.getActivityInfo().setStatus(getStatus());
                this.getActivityInfo().setEndDatetime(TimeUtil.getCurrentTimeStr());
                this.activityDao.updateActivityInfo(this.getActivityInfo());
                TaskContainer.autoCallActivityMap.remove(this.activityName);
            }
        }

    }


    private MakeCall callOutRoster(RosterInfo info, TrunkNumber t_trunk, RosterBatchInfo batchInfo) {
        MakeCall mkcall = new MakeCall();
        mkcall.setAni(t_trunk.getDisplayNum())
                .setActivityName(getActivityInfo().getName())
                .setCallee(StringUtil.isEmpty(info.getCallee()) ? this.dst : info.getCallee())
                .setDomain(info.getDomain())
                .setTrunkGrp(t_trunk.getTrunkGrp())
                .setRound(info.getCallRound())
                .setBatchName(batchInfo.getBatchId())
                .setRosterinfoId("" + info.getId())
                .setUui(getUUIInfo(info))
                .setCaller(StringUtil.isEmpty(info.getPrefix()) ? this.prefix + info.getPhoneNum1() : info.getPrefix() + info.getPhoneNum1());
        //判断配置运营商-线路
        if (this.prefix.contains("|")) {
            String value = prefixMap.get(info.getOperators());
            mkcall.setCaller(StringUtil.isEmpty(value) ? prefixMap.get("中国移动") + info.getPhoneNum1() : value + info.getPhoneNum1());
        }

        if ("all".equals(getActivityInfo().getIsSMS()) && batchInfo.getCallRound() == 1) {
            //异步调短信
            executorService.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    Map smsMap = new HashMap();
                    smsMap.put("templateId", getActivityInfo().getName());
                    smsMap.put("mobile", info.getPhoneNum1());
                    try {
                        String respon = HttpUtil.post(InitConfig.SMS_URL, smsMap);
                        log.info("请求发短信成功---" + respon);
                    } catch (Exception e) {
                        log.info("请求发短信失败---" + smsMap, "failed cause" + e);
                    }
                    return null;
                }
            });
        }

        return mkcall;

/*
        task = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                ClientResponse result = CallUtil.makeCall(mkcall);
                logger.info("## 结果。。。。。。。" + result);

                if (result == null) {
//                    info.setMakeCallTime(TimeUtil.getCurrentTimeStr());
//                    info.setCallEndTime(TimeUtil.getCurrentTimeStr());
//                    info.setCallResult("外呼接口失败");
//                    info.setResultCode(12);
//                    info.setStatus(2);
                    //rosterDao.updateRosterInfo(info);
                    useTransactionService.updateRosterFailInfo(info.getId());
                } else {
                    //解决redis中的并发数与实际并发数不一致
                    RedisUtil.addRoster(info);
                }
                return info.getId();
            }
        };*/
/*
        tasks.add(task);
        if (end) {
            log.info("redis 需要填充的数据量" + callNum);
            try {
                List<Future<Integer>> futures = executorService.invokeAll(tasks);
                maxId = futures.get(futures.size() - 1).get();
                for (Future<Integer> future : futures) {
                    if (future.get() > maxId) {
                        maxId = future.get();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            tasks.clear();
            callNum = 0;
        }*/
        //并发
    /*   executorService.execute(()->{
            try {
                semaphore.acquire();
                ReqResponse result = CallUtil.makeCall(mkcall);
                log.info("## 结果。。。。。。。"+result);

                if(result != null){
                    RedisUtil.addRoster(info);
                }else{
                    info.setMakeCallTime(TimeUtil.getCurrentTimeStr());
                    info.setCallEndTime(TimeUtil.getCurrentTimeStr());
                    info.setCallResult("外呼接口失败");
                    info.setResultCode(12);
                    info.setStatus(2);
                    rosterDao.updateRosterInfo(info);
                }
                semaphore.release();
            } catch (Exception e) {
                log.error("exception", e);
            }
        });*/
        /*ReqResponse result = CallUtil.makeCall(mkcall);
        if(result != null){
            RedisUtil.addRoster(info);
        }else{
            info.setMakeCallTime(TimeUtil.getCurrentTimeStr());
            info.setCallEndTime(TimeUtil.getCurrentTimeStr());
            info.setCallResult("外呼接口失败");
            info.setResultCode(12);
            info.setStatus(2);
            rosterDao.updateRosterInfo(info);
        }*/

    }

    public void stopTask() {
        this.status.set(3);
        log.info("##### stop taskthread " + this.activityName);
    }

    public void pauseTask() {
        this.status.set(2);
        this.getActivityInfo().setStatus(2);
        this.activityDao.updateActivityInfo(this.getActivityInfo());
        log.info("####pause taskthread " + this.activityName);
    }

    public void resumeTask() {
        this.initParams();
        this.status.set(1);
        this.getActivityInfo().setStatus(1);
        this.getActivityInfo().setActivityExecuteTime(TimeUtil.getCurrentTimeStr());
        this.activityDao.updateActivityInfo(this.getActivityInfo());
        log.info("####resume taskthread " + this.activityName);
    }

    public int getStatus() {
        return this.status.get();
    }

    public void setStatus(int status) {
        this.status.set(status);
    }

    public ActivityInfo getActivityInfo() {
        return this.activityInfo;
    }

    public void setActivityInfo(ActivityInfo activityInfo) {
        this.activityInfo = activityInfo;
        if (activityInfo != null) {
            this.domain = this.getActivityInfo().getDomain();
            this.callLimit = this.getActivityInfo().getMaxCapacity();
            //this.prefix = this.getActivityInfo().getPrefix();
            this.activityName = this.getActivityInfo().getName() + ":" + this.getActivityInfo().getDomain();
        }
    }

    public OutboundPolicyInfo getPolicyInfo() {
        return this.policyInfo;
    }

    public void setPolicyInfo(OutboundPolicyInfo policyInfo) {
        this.policyInfo = policyInfo;
    }
}



