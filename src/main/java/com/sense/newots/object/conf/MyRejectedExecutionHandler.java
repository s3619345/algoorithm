package com.sense.newots.object.conf;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by yangyuchang on 2019/09/03.
 * 自定义阻塞型线程池,线程池会阻塞，只有到有空闲线程时才会运行
 */
@Slf4j
class MyRejectedExecutionHandler implements RejectedExecutionHandler {
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        if (!executor.isShutdown()) {
            try {
                //一般的线程池的队列阻塞方式
                //executor.getQueue().put(r);
                //自定义的线程池使用的是LinkedTransferQueue 队列
                //put 和 transfer 方法的区别是，put 是立即返回的， transfer 是阻塞等待消费者拿到数据才返回。transfer 方法和 SynchronousQueue 的 put 方法类似。
                LinkedTransferQueue transferQueue = (LinkedTransferQueue) executor.getQueue();
                transferQueue.transfer(r);
            } catch (InterruptedException e) {
                log.error(e.toString(), e);
                Thread.currentThread().interrupt();
            }
        }
    }
}