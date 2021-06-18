package com.sense.newots.object.conf;

import com.sense.newots.object.ThreadPoolOntime;
import com.sense.newots.object.entity.ActivityInfo;
import com.sense.newots.object.entity.OutboundPolicyInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.lang.Thread.UncaughtExceptionHandler;

@Slf4j
@Configuration
public class MyUncaughtExceptionHandler implements UncaughtExceptionHandler {

	private ActivityInfo activityInfo;
	private OutboundPolicyInfo policyInfo;

	public MyUncaughtExceptionHandler(ActivityInfo activityInfo, OutboundPolicyInfo policyInfo ) {
		super();
		this.activityInfo = activityInfo;
		this.policyInfo = policyInfo;

	}

	@Override
	public void uncaughtException(Thread t, Throwable e) {

		//日志 log
		log.info("Thread name:{}", t.getName());
		log.info("Thread id: {}", t.getId());
		log.info("Exception: {}: {}", e.getClass().getName(),e.getStackTrace());
		log.info("Thread status: {}", t.getState());

		ThreadPoolOntime.addThread(activityInfo,policyInfo);
		log.info("Thread reStarted: {}",  t.getName());
	}

}
