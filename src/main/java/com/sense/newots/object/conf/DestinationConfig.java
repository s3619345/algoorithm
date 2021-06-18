package com.sense.newots.object.conf;

import com.sense.newots.amq.consumer.queue.MyMessageListener;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQPrefetchPolicy;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.support.converter.SimpleMessageConverter;

import javax.jms.Destination;
import javax.jms.MessageListener;

/**
 @desc ...
 @date 2021-06-08 10:45:59
 @author szz
 */
@Configuration
@ComponentScan("com.sense.newots.amq")
public class DestinationConfig {
    @Value("OTS_API")
    private String apiQueueName;
    @Value("${jms.uploadQueue.name}")
    private String uploadQueueName;
    @Value("${jms.transferQueue.name}")
    private String transferQueueName;
    @Value("${spring.activemq.user}")
    private String user;
    @Value("${spring.activemq.password")
    private String password;
    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;

    //数据传输队列，点对点的
    @Bean(name = "apiQueue")
    public Destination getActiveMQQueue() {
        return new ActiveMQQueue(apiQueueName);
    }

    @Bean("queueDestination")
    public Destination getQueueDestionation() {
        return new ActiveMQQueue(uploadQueueName);
    }
    //数据传输队列，点对点的
    @Bean("transferQueue")
    public Destination getTransferQueue() {
        return new ActiveMQQueue(transferQueueName);
    }

    @Bean("amqConnectionFactory")
    public ActiveMQConnectionFactory getActiveMQConnection() {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        factory.setBrokerURL(brokerUrl);
        factory.setPassword(password);
        factory.setUserName(user);
        factory.setPrefetchPolicy(getActiveMQPrefetchPolicy());
        factory.setRedeliveryPolicy(getRedeliveryPolicy());
        factory.setTrustAllPackages(true);
        return factory;
    }

    @Bean("prefetchPolicy")
    public ActiveMQPrefetchPolicy getActiveMQPrefetchPolicy() {
        ActiveMQPrefetchPolicy policy = new ActiveMQPrefetchPolicy();
        policy.setQueuePrefetch(1);
        return policy;
    }

    //Spring Caching连接工厂
    //Spring用于管理真正的ConnectionFactory的ConnectionFactory
    @Bean("connectionFactory")
    public CachingConnectionFactory getCachingConnectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setTargetConnectionFactory(getActiveMQConnection());
        factory.setSessionCacheSize(100);
        return factory;
    }

    @Bean("redeliveryPolicy")
    public RedeliveryPolicy getRedeliveryPolicy() {
        RedeliveryPolicy policy = new RedeliveryPolicy();
        policy.setUseExponentialBackOff(true);
        policy.setMaximumRedeliveries(5);
        policy.setInitialRedeliveryDelay(1000);
        policy.setBackOffMultiplier(2);
        policy.setMaximumRedeliveryDelay(1000);
        return policy;
    }

    @Bean("jmsTemplate")
    public JmsTemplate getJmsTemplate() {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(getActiveMQConnection());
        jmsTemplate.setDefaultDestination(getQueueDestionation());
        //true是topic,false是queue,默认是false,此处显式写出false
        jmsTemplate.setPubSubDomain(false);
        jmsTemplate.setMessageConverter(new SimpleMessageConverter());
        return jmsTemplate;
    }

    @Bean("myMessageListener")
    public MessageListener getMessageListener() {
        return new MyMessageListener();
    }
    //监听容器
    @Bean("myListenerContainer")
    public DefaultMessageListenerContainer getDefaultMessageListenerContainer(){
        DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
        container.setConnectionFactory(getCachingConnectionFactory());
        container.setDestination(getTransferQueue());
        container.setMessageListener(getMessageListener());
        container.setConcurrency("2-10");
        container.setSessionTransacted(true);
        return container;
    }
}

