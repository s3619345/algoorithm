package com.sense.newots.amq.consumer.queue;


import javax.jms.Destination;

public interface ConsumerService {
  String receiveMessage(Destination destination, Destination replyDestination);

  String receiveMessage(Destination destination);

  void receiveMessages(Destination destination);

} 