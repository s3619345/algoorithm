package com.sense.newots.amq.consumer.queue;

import com.sense.newots.vo.UploadInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.*;

@Slf4j
@Service("consumerServiceImpl")
public class ConsumerServiceImpl implements ConsumerService {

    /**
     * 注入JmsTemplate
     */
    @Resource(name="jmsTemplate")
    private JmsTemplate jTemplate;

    /**
     * attention:
     * Details：接收消息，同时回复消息
     * @author yangyuchang
     * 创建时间：2018-10-25
     * @param destination
     * @return
     */
    @Override
    public String receiveMessage(Destination destination, Destination replyDestination) {
        /**
         * 接收消息队列中的消息
         */
        Message message = jTemplate.receive(destination);
        try {
            /**
             * 此处为了更好的容错性，可以使用instanceof来判断下消息类型
             */
            if(message instanceof TextMessage){
                String receiveMessage = ((TextMessage) message).getText();
                log.info("receive:"+receiveMessage);
                /**
                 * 收到消息之后，将回复报文放到回复队列里面去
                 */
                jTemplate.send(replyDestination, new MessageCreator() {

                    @Override
                    public Message createMessage(Session session) throws JMSException {
                        return session.createTextMessage("消费者已经收到生产者的消息了，这是一条确认报文!");
                    }
                });
                return receiveMessage;
            }
        } catch (JMSException e) {
            log.error("ConsumerServiceImpl receive 文件失败",e);
        }
        return "";
    }

    @Override
    public String receiveMessage(Destination destination) {
        /**
         * 接收消息队列中的消息
         */
        Object receiveMessage = null;
        try {
            while (true) {
                //接收者接收消息
                Message message = jTemplate.receive(destination);
                /**
                 * 此处为了更好的容错性，可以使用instanceof来判断下消息类型
                 */
                if(message instanceof ObjectMessage){
                    receiveMessage  = ((ObjectMessage) message).getObject();
                    log.info(Thread.currentThread().getName()+"receive:"+receiveMessage);
                    if (receiveMessage instanceof UploadInfo){
                    }
                }else  if(message instanceof TextMessage){
                    receiveMessage = ((TextMessage) message).getText();
                    log.info(Thread.currentThread().getName()+"receive:"+receiveMessage);
                }else{
                    log.info(Thread.currentThread().getName()+"receive:msg error!");
                }
            }
        } catch (JMSException e) {
            log.error("ConsumerAnalysis receive 文件失败",e);
        }
        return "";
    }

    @Override
    public void receiveMessages(Destination destination) {
        {
            /**
             * 接收消息队列中的消息
             */
            Object receiveMessage = null;

            while (true) {
                try {
                    //接收者接收消息
                    Message message = jTemplate.receive(destination);
                    /**
                     * 此处为了更好的容错性，可以使用instanceof来判断下消息类型
                     */
                    if (message instanceof ObjectMessage) {
                        receiveMessage = ((ObjectMessage) message).getObject();
                        log.info(Thread.currentThread().getName() + "receive:" + receiveMessage);
                        if (receiveMessage instanceof UploadInfo) {
                        }
                    } else if (message instanceof TextMessage) {
                        receiveMessage = ((TextMessage) message).getText();
                        log.info(Thread.currentThread().getName() + "receive:" + receiveMessage);
                    } else {
                        log.info(Thread.currentThread().getName() + "receive:msg error!");
                    }
                } catch (JMSException e) {
                    log.error("ConsumerAnalysis receive 文件失败", e);
                }
            }
        }
    }
}