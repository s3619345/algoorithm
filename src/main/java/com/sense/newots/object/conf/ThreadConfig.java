package com.sense.newots.object.conf;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.sense.newots.object.conf.cluster.StandardThreadExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by yangyuchang on 2019/1/14.
 */

@Configuration
public class ThreadConfig {


    /**
     * 发送ces请求的task，由于数据库的连接数和ces的并发限制，暂定50线程
     *
     * @return
     */
    @Bean(name = "taskExecutorService")
    public ThreadPoolExecutor taskExecutorService() {
        //BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(10000);
       /*return new ThreadPoolExecutor(0, 50,
                60L, TimeUnit.SECONDS, new SynchronousQueue(), new ThreadFactoryBuilder().setNameFormat("task-%d").build(), new MyRejectedExecutionHandler());*/
        return
                new StandardThreadExecutor(0, 20, 60L, TimeUnit.SECONDS,
                        10000, new ThreadFactoryBuilder()
                        .setNameFormat("task-%d")
                        .build(),
                        new MyRejectedExecutionHandler()
                );
    }

    /**
     * 使用两级线程池关联业务线程池
     * @param taskExecutorService
     */
    /*@Bean
    public void Executor2ndService(ThreadPoolExecutor taskExecutorService) {

        new J2Executor(new StandardThreadExecutor(0, 50, 60L, TimeUnit.SECONDS,
                10000, new ThreadFactoryBuilder()
                .setNameFormat("2nd-%d")
                .build(),
                new MyRejectedExecutionHandler()))
                .registrySubThreadPoolExecutors(new ImmutableList.Builder<ThreadPoolExecutor>()
                        .add(taskExecutorService)
                        .build());
    }*/

/*    @Bean
    public ListeningExecutorService dbExecutorService() {
        return MoreExecutors.listeningDecorator(new ThreadPoolExecutor(50, 50,
                60L, TimeUnit.SECONDS, *//*new SynchronousQueue<Runnable>()*//*new LinkedBlockingQueue<Runnable>(10000), new ThreadFactoryBuilder().setNameFormat("db-%d").build(),new ThreadPoolExecutor.CallerRunsPolicy()));
    }*/
}
