package com.youyuan.paystrategy.job;

import javax.jms.Queue;
import javax.jms.Topic;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 消息生产者.
 */
@Component
@EnableScheduling
public class Producer {
   
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
   
    @Autowired
    private Queue queue;
   
    @Autowired
    private Topic topic;
    @Autowired
    private RabbitTemplate rabbitTemplate;
//    @Scheduled(fixedDelay=4000)//每3s执行1次
    public void sendRabit(){
        rabbitTemplate.convertAndSend("foo","sendRabit-zhang-"+new Date());
    }
//    @Scheduled(fixedDelay=3000)//每3s执行1次
    public void jmsSend() {
       //send queue.
       this.jmsMessagingTemplate.convertAndSend(this.queue, "jmsSend-hi,activeMQ-"+new Date());
       //send topic.
       this.jmsMessagingTemplate.convertAndSend(this.topic, "jmsSend-hi,activeMQ(topic)-"+new Date());
    }
   
}