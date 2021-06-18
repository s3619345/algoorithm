package com.sense.newots.object;

import com.sense.newots.impl.metric.ActivityMetric;
import com.sense.newots.impl.metric.BatchMetric;
import com.sense.newots.impl.metric.MetricUtil;
import com.sense.newots.object.entity.ActivityInfo;
import com.sense.newots.object.entity.OutboundPolicyInfo;
import com.sense.newots.object.entity.RosterBatchInfo;
import com.sense.newots.object.service.ActivityInfoService;
import com.sense.newots.object.service.OutboundPolicyInfoService;
import com.sense.newots.object.service.RosterBatchService;
import com.sense.newots.object.util.TimeUtil;

import java.util.Iterator;
import java.util.List;

//项目启动时  一个模板对应一个线程
public class ActivityUtil {
    public ActivityUtil() {
    }

    private static ActivityInfoService activityDao = GetBeanUtil.getBean(ActivityInfoService.class);
    private static RosterBatchService rosterBatchDao = GetBeanUtil.getBean(RosterBatchService.class);
    private static OutboundPolicyInfoService outboundPolicyInfoDao = GetBeanUtil.getBean(OutboundPolicyInfoService.class);

    public static void init() {
        //查找所有的activityInfo表中的信息  循环遍历
        List<ActivityInfo> infos = activityDao.selectActivityInfo();
        if (infos != null) {
            Iterator ibnfo = infos.iterator();

            label35:

            //无线循环执行
            while (true) {

                ActivityInfo binfos;
                do {
                    if (!ibnfo.hasNext()) {
                        break label35;
                    }

                    //把实体遍历出来
                    binfos = (ActivityInfo) ibnfo.next();
                    //（status  0：未拨打 1:进行中 2:暂停 3:停止）
                } while (binfos.getStatus() != 1 && binfos.getStatus() != 2);

                //模板执行时间（获取当前时间）
                binfos.setActivityExecuteTime(TimeUtil.getCurrentTimeStr());

                activityDao.updateActivityInfo(binfos);
                //外呼策略
                OutboundPolicyInfo pinfo = outboundPolicyInfoDao.getTOutboundPolicyInfos(binfos.getDomain(), binfos.getPolicyName());
                //新开一个线程，启动
                ThreadPoolOntime.addThread(binfos, pinfo);
                /*AutoCallThread bname = new AutoCallThread("Auto", binfos, pinfo);
                bname.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                    private Logger logger = Logger.getLogger(ActivityUtil.class.getName());
                    @Override
                    public void uncaughtException(Thread t, Throwable e) {
                        logger.info("Uncaught exception in thread '" + t.getName() + "':cause"+e.getCause().getMessage()+"msg:"+e.getMessage());
                    }
                });
                bname.setStatus(binfos.getStatus());
                bname.start();*/

                String metric = binfos.getName() + ":" + binfos.getDomain();
                ActivityMetric metric1 = (ActivityMetric) MetricUtil.actMetrics.get(metric);
                if (metric1 == null) {
                    metric1 = new ActivityMetric();
                    metric1.setActivityName(binfos.getName());
                    metric1.setDomain(binfos.getDomain());
                    metric1.setMaxCall(binfos.getMaxCapacity());
                    metric1.setTemplateName(binfos.getRosterTemplateName());
                    metric1.setStatus(binfos.getStatus());
                    metric1.setBatchNum(binfos.getBatchNum());
                    metric1.setRoterNum(binfos.getRosterNum());
                    metric1.setPrefix(binfos.getPrefix());
                    metric1.setTrunkGrp(binfos.getTrunkGrp());
                    metric1.setOutCallNum(binfos.getOutCallNum());
                    metric1.setAnswerCallNum(binfos.getAnswerCallNum());
                    MetricUtil.actMetrics.put(metric, metric1);
                }

            }
        }
        //得到当天全部数据的批次信息 遍历
        List binfos1 = rosterBatchDao.getTodayRosterBatchInfos();
        Iterator pinfo1 = binfos1.iterator();

        while (pinfo1.hasNext()) {
            RosterBatchInfo ibnfo1 = (RosterBatchInfo) pinfo1.next();
//            String bname1 = ibnfo1.getBatchId() + ":" + ibnfo1.getCallRound() + ":" + ibnfo1.getActivityName() + ":" + ibnfo1.getDomain();
            String bname1 = ibnfo1.getBatchId() + ":" + ibnfo1.getActivityName();
            //从内存中取到已完成批次的信息
            BatchMetric metric2 = (BatchMetric) MetricUtil.batchMetrics.get(bname1);
            if (metric2 == null) {
                metric2 = new BatchMetric();
                MetricUtil.batchMetrics.put(bname1, metric2);
            }
            metric2.setActivityName(ibnfo1.getActivityName());
            metric2.setDomain(ibnfo1.getDomain());
            metric2.setBatchName(ibnfo1.getBatchId());
            metric2.setRoterNum(ibnfo1.getRoterNum());
            metric2.setAnswerCallNum(ibnfo1.getAnswerCallNum());
            metric2.setFail1Num(ibnfo1.getFail1Num());
            metric2.setFail2Num(ibnfo1.getFail2Num());
            metric2.setFail3Num(ibnfo1.getFail3Num());
            metric2.setFail4Num(ibnfo1.getFail4Num());
            metric2.setRound(ibnfo1.getCallRound());
            metric2.setFail5Num(ibnfo1.getFail5Num());
            metric2.setFailOtherNum(ibnfo1.getFailOtherNum());
            metric2.setOutCallNum(ibnfo1.getOutCallNum());
            metric2.setStatus(ibnfo1.getStatus());
        }
    }

}
