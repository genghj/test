package com.youyuan.paystrategy.client;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 消息消费者.
 */
@Component
public class Consumer3 {


    @JmsListener(destination = "sample.topic",containerFactory = "jmsListenerContainerTopic") // 监听指定消息主题
    public void receiveTopic(String text) {
        System.out.println("Consumer3 topic="+text);
    }

    @JmsListener(destination = "sample.queue",containerFactory = "jmsListenerContainerQueue") // 监听指定消息主题
    public void receiveQueue(String text) {

        System.out.println("Consumer3 queue="+text);
    }

    //接收到消息处理.

    @RabbitListener(queues="foo")//启用Rabbit队列监听foo key.
    @RabbitHandler
    public void onMessage(@Payload String foo){
        System.out.println("Consumer3= >>> "+new Date() + ": " + foo);
    }

}