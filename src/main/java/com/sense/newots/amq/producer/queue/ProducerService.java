package com.sense.newots.amq.producer.queue;

import javax.jms.Destination;
import javax.jms.JMSException;
import java.io.Serializable;

public interface ProducerService {

    void sendMessage(Destination destination, final Serializable message);

    void sendMessage(Destination destination, final Serializable message, final String JMSXGroupID, Boolean endFlag);

    void sendMapMessage(Destination destination, final QMessage message, final String JMSXGroupID, Boolean endFlag) throws JMSException;

} 