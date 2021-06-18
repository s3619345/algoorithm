package com.sense.newots.object;

import com.sense.newots.impl.metric.ActivityMetric;
import com.sense.newots.impl.metric.MetricUtil;
import com.sense.newots.object.entity.ActivityInfo;
import com.sense.newots.object.entity.CircuitConf;
import com.sense.newots.object.service.ActivityInfoService;
import com.sense.newots.service.CircuitTransactionService;
import com.sense.newots.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;

/**
 * Created by senseinfo on 2019/3/12.
 */
@Slf4j
public class StopCircuitJob implements Job {

    private ActivityInfoService activityDao = GetBeanUtil.getBean(ActivityInfoService.class);
    private CircuitTransactionService circuitTransactionService = GetBeanUtil.getBean(CircuitTransactionService.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //查询每个模板的最后结束时间  停止模板
        List<CircuitConf> circuitConfs = this.circuitTransactionService.getCircuitListEndTime();
        log.info("end time 暂停模板:"+ circuitConfs);
        if (circuitConfs ==null|| circuitConfs.isEmpty()){
            return;
        }else {
            for (int i = 0; i < circuitConfs.size(); i++) {
                CircuitConf circuitConf =  circuitConfs.get(i);
                String activityName = String.valueOf(circuitConfs.get(i).getActivityName());
                ActivityInfo activityInfo = this.activityDao.findActivityInfoByTemplate(activityName);
                if (activityInfo!=null){
                    String activityKey = activityInfo.getName() + ":" + activityInfo.getDomain();
                    AutoCallThread aThread = (AutoCallThread) TaskContainer.autoCallActivityMap.get(activityKey);
                    ActivityMetric metric = (ActivityMetric) MetricUtil.actMetrics.get(activityKey);
                    if (aThread!=null && aThread.getStatus()==1){
                        aThread.setActivityInfo(activityInfo);
                        aThread.pauseTask();
                        if (StringUtil.isNotEmpty(metric)){
                            metric.setStatus(2);
                        }
                    }
                    circuitConf.setIsRun(2);
                    this.circuitTransactionService.updateCircuit(circuitConf);
                }else {
                    return;
                }

            }
        }
    }
}
