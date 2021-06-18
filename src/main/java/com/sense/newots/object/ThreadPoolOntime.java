package com.sense.newots.object;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.sense.newots.object.conf.MyUncaughtExceptionHandler;
import com.sense.newots.object.entity.ActivityInfo;
import com.sense.newots.object.entity.OutboundPolicyInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by yangyuchang on 2019/08/07.
 * 线程发生未捕获异常自动重启
 */
@Slf4j
public class ThreadPoolOntime {
	/**
	 * 根据activity 创建 autoCall 线程
	 * 每个根据activity都是单独的一套，就不会出现紊乱了
	 * @param activityInfo
	 */
	public static void addThread(ActivityInfo activityInfo, OutboundPolicyInfo policyInfo) {
		Runnable autoCallTask = new AutoCallThread(activityInfo,policyInfo);
		((AutoCallThread) autoCallTask).setStatus(activityInfo.getStatus());

		String activity = activityInfo.getName() + ":" + activityInfo.getDomain();
		ExecutorService executor = Executors.newSingleThreadExecutor(
				new ThreadFactoryBuilder()
						.setNameFormat(activity + "-pool-%d")
						.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler(activityInfo,policyInfo))
						.build());

		executor.execute(autoCallTask);
		log.info("线程 start---",activity);
		//System.out.println(activity + " start!!!");
		executor.shutdown();
	}
}