package com.sense.newots.amq.producer.queue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.*;
import java.io.Serializable;

@Slf4j
@Service("producerServiceImpl")
public class ProducerServiceImpl implements ProducerService {

    /**
     * 注入JmsTemplate
     */
    @Resource(name = "jmsTemplate")
    private JmsTemplate jTemplate;


    public void sendMessage(Destination receivedestination, final String message) {

        //log.info("---ProducerServiceImpl Producer---{}-",message);
     /*   jTemplate.send(receivedestination, new MessageCreator() {

            @Override
            public Message createMessage(Session session) throws JMSException {
                //TextMessage textMessage = session.createTextMessage("hello acticeMQ:" + message);
                TextMessage textMessage = session.createTextMessage(message);
                //textMessage.setStringProperty("JMSXGroupID", "constact-2000000002");
                //textMessage.setIntProperty("JMSXGroupSeq", -1);
                return textMessage;
            }
        });*/
        //使用MessageConverter的情况
        jTemplate.convertAndSend(receivedestination, message);
    }

    @Override
    public void sendMessage(Destination destination,final Serializable message) {
        jTemplate.send(destination, new MessageCreator() {

            @Override
            public Message createMessage(Session session) throws JMSException {
                ObjectMessage objectMessage = session.createObjectMessage( message);

                return objectMessage;
            }
        });
    }
    @Override
    public void sendMessage(Destination destination, Serializable message,String JMSXGroupID,Boolean endFlag) {

    }

    @Override
    public void sendMapMessage(Destination destination, QMessage qMessage, String JMSXGroupID, Boolean endFlag) throws JMSException  {
        log.info("---ProducerTransfers sendMapMessage---{}-",qMessage);
        jTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                MapMessage message = session.createMapMessage();
                message.setString("messageId", qMessage.getMessageId());
                message.setString("data",  qMessage.getMessageContent());
                message.setString("timeStamp", String.valueOf(qMessage.getTimeStamp()));
                message.setStringProperty("JMSXGroupID", JMSXGroupID);
                if (endFlag){
                    message.setIntProperty("JMSXGroupSeq", -1);
                }
                return message;
            }
        });
    }
}