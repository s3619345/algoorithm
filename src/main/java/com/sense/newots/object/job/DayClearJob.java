//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sense.newots.object.job;


import com.sense.newots.impl.metric.MetricUtil;
import com.sense.newots.object.GetBeanUtil;
import com.sense.newots.object.entity.ActivityInfo;
import com.sense.newots.object.entity.ActivityInfoHistory;
import com.sense.newots.object.entity.RosterBatchInfo;
import com.sense.newots.object.service.ActivityInfoHistoryService;
import com.sense.newots.object.service.ActivityInfoService;
import com.sense.newots.object.service.RosterBatchService;
import com.sense.newots.object.service.RosterService;
import com.sense.newots.service.CircuitTransactionService;
import com.sense.newots.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.dao.DataAccessException;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

@Slf4j
public class DayClearJob implements Job {

    private ActivityInfoService activityDao = GetBeanUtil.getBean(ActivityInfoService.class);
    private RosterBatchService batchDao = GetBeanUtil.getBean(RosterBatchService.class);
    private ActivityInfoHistoryService activityHDao = GetBeanUtil.getBean(ActivityInfoHistoryService.class);
    private RosterService rosterDao = GetBeanUtil.getBean(RosterService.class);
    private CircuitTransactionService circuitTransactionService = GetBeanUtil.getBean(CircuitTransactionService.class);

    public DayClearJob() {
    }

    public void execute(JobExecutionContext context) throws JobExecutionException {
        long currentTiem = System.currentTimeMillis();
        log.info("#@## day clear task start at " + (new Timestamp(currentTiem)).toString());

        //清理内存数据
        MetricUtil.clear();
        AnswerClickJob.clear();
        //this.processActivitys(); //清理activity_info表里已完成(状态为3)的模板
        this.processBatchs();
        this.processCircuit();

        //清空标签结果
        RedisUtil.del();
    }


    //处理t_activity_info表里的数据
    private void processActivitys() {
        //查已拨打完成的  status=3
        List acts = this.activityDao.getStoppedActivityInfos();
        if (acts != null) {
            Iterator var3 = acts.iterator();

            while (var3.hasNext()) {

                //把t_activity_info表中的数据拷贝到t_activity_info_history表里
                ActivityInfo info = (ActivityInfo) var3.next();
                ActivityInfoHistory aHistory = new ActivityInfoHistory();

                try {
                    BeanUtils.copyProperties(aHistory, info);
                } catch (IllegalAccessException var6) {
                    log.error("IllegalAccessException :{}", var6);
                } catch (InvocationTargetException var7) {
                    log.error("InvocationTargetException:{}", var7);
                }
                //添加到t_activity_info_history表
                this.activityHDao.createActivityInfoHistory(aHistory);
                //删除t_activity_info里的数据
                this.activityDao.deleteActivityInfo(info);
            }
        }

    }

    //处理t_roster_info表里的信息
    private void processBatchs() {
        //已完成的批次  status = 2
//        List batchs = this.batchDao.getFinishRosterBatchInfos();
//        log.info("find finishRosterBatchInfos size ---: "+batchs.size());
//        if(batchs != null) {
//            Iterator var3 = batchs.iterator();
//
//            while(true) {
//                List rosters;
//                do {
//                    if(!var3.hasNext()) {
//                        return;
//                    }
//
//                    RosterBatchInfo info = (RosterBatchInfo)var3.next();
//                    rosters = this.rosterDao.findFinishBatchRosters(info.getDomain(), info.getBatchId());
//                } while(rosters == null);
//
//                Iterator var6 = rosters.iterator();
//
//                while(var6.hasNext()) {
//
//                    //每天把t_roster_info表里的数据拷贝到t_roster_info_history表里
//                    RosterInfo rInfo = (RosterInfo)var6.next();
//                    RosterInfoHistory rInfoH = new RosterInfoHistory();
//
//                    try {
//                        BeanUtils.copyProperties(rInfoH, rInfo);
//                    } catch (IllegalAccessException var9) {
//                        log.error(var9);
//                    } catch (InvocationTargetException var10) {
//                        log.error(var10);
//                    }
//
//                    this.rosterHDao.createRosterInfoHistory(rInfoH);
//                    this.rosterDao.deleteRosterInfo(rInfo);
//                }
//            }
//        }

        try {

            //1.把roster_info 表里的数据复制到history表里
            boolean e = this.rosterDao.replaceRosterHistory();
            List<RosterBatchInfo> batchs = this.batchDao.getFinishRosterBatchInfos();
            for (int i = 0; i < batchs.size(); i++) {
                RosterBatchInfo info = batchs.get(i);
                if (e) {
                    //2.复制成功后删除roster_info 表里的数据
                    this.rosterDao.delRosterInfo(info.getBatchId());
                }
            }
            log.info("清理 roster_info is success");
        } catch (DataAccessException e) {
            log.info("清理 roster_info is failed");
        }


    }

    //  清理circuit_conf线路 is_run 初始化为0
    private void processCircuit() {
        boolean success = this.circuitTransactionService.updateCircuitByStatus();
        if (success) {
            log.info("初始化线路配置成功");
        }
    }

}
