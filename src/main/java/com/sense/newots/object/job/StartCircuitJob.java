package com.sense.newots.object.job;

import com.sense.newots.impl.metric.ActivityMetric;
import com.sense.newots.impl.metric.MetricUtil;
import com.sense.newots.object.AutoCallThread;
import com.sense.newots.object.GetBeanUtil;
import com.sense.newots.object.TaskContainer;
import com.sense.newots.object.entity.ActivityInfo;
import com.sense.newots.object.entity.CircuitConf;
import com.sense.newots.object.service.ActivityInfoService;
import com.sense.newots.service.CircuitTransactionService;
import com.sense.newots.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * Created by senseinfo on 2019/3/12.
 */
@Slf4j
public class StartCircuitJob implements Job {
    private ActivityInfoService activityDao = GetBeanUtil.getBean(ActivityInfoService.class);
    private CircuitTransactionService circuitTransactionService = GetBeanUtil.getBean(CircuitTransactionService.class);
    private ExecutorService executorService = GetBeanUtil.getBean(ExecutorService.class);
    public StartCircuitJob() {

    }

    private void waitTime(long mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            log.error("InterruptedException:{}", e);
        }
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        List<Callable<Integer>> tasks = new ArrayList<Callable<Integer>>();
        Callable<Integer> task = null;

        //查出每个模板的最近的定时任务start_time  is_run = 0  0：代表未执行，1：代表已执行
        List<CircuitConf> circuitConfs = this.circuitTransactionService.getCircuitListStrartTime();
        log.info("start time 启动模板:" + circuitConfs);
        if (circuitConfs == null || circuitConfs.isEmpty()) {
            return;
        } else {
            for (int i = 0; i < circuitConfs.size(); i++) {
                CircuitConf circuitConf = circuitConfs.get(i);
                String prefix = circuitConfs.get(i).getPrefix();
                String activityName = String.valueOf(circuitConfs.get(i).getActivityName());
                ActivityInfo activityInfo = this.activityDao.findActivityInfoByTemplate(activityName);

                if (StringUtil.isNotEmpty(activityInfo)) {
                    task = new Callable<Integer>() {
                        @Override
                        public Integer call() throws Exception {
                            String activityName = activityInfo.getName() + ":" + activityInfo.getDomain();
                            AutoCallThread aThread = (AutoCallThread) TaskContainer.autoCallActivityMap.get(activityName);
                            ActivityMetric metric = (ActivityMetric) MetricUtil.actMetrics.get(activityName);

                            if (StringUtil.isNotEmpty(aThread)) {
                                if (aThread.getStatus() == 1) {
                                    aThread.pauseTask();
                                }
                                //更新sql执行慢，需要等待一下，否则会影响下面的程序
                                Thread.sleep(100);
                                activityInfo.setPrefix(prefix);
                                if (activityDao.updatePrefix(prefix, activityInfo.getName())) {
                                    // aThread.setActivityInfo(activityInfo);
                                    aThread.resumeTask();
                                    if (StringUtil.isNotEmpty(metric)) {
                                        metric.setStatus(1);
                                        metric.setPrefix(prefix);
                                    }
                                    //重新addThread
                                /*OutboundPolicyInfo info = outboundPolicyInfoDao.getTOutboundPolicyInfos(activityInfo.getDomain(), activityInfo.getPolicyName());
                                ThreadPoolOntime.addThread(activityInfo, info);*/

                                    circuitConf.setIsRun(1);
                                    circuitTransactionService.updateCircuit(circuitConf);
                                }
                            }
                            return aThread.getStatus();
                        }
                    };

                    tasks.add(task);
                }
            }

            try {
                List<Future<Integer>> futures = executorService.invokeAll(tasks);
                for (Future<Integer> future : futures) {
                    log.info("模板运行状态：" + future.get());
                    //輸出 模板狀態
                }
            } catch (Exception e) {
                log.error("定时起模板任务失败", e);
            }


        }
    }

}
