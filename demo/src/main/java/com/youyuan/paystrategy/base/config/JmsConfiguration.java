package com.youyuan.paystrategy.base.config;

import javax.jms.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;


@Configuration
@EnableJms
public class JmsConfiguration {  
    // topic模式的ListenerContainer  
    @Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerTopic(ConnectionFactory activeMQConnectionFactory) {
        DefaultJmsListenerContainerFactory bean = new DefaultJmsListenerContainerFactory();
        bean.setPubSubDomain(true);  
        bean.setConnectionFactory(activeMQConnectionFactory);
        /**
         * 使用消息转换器
         */
//        bean.setMessageConverter(jacksonJmsMessageConverter());
        return bean;  
    }  
    // queue模式的ListenerContainer  
    @Bean  
    public JmsListenerContainerFactory<?> jmsListenerContainerQueue(ConnectionFactory activeMQConnectionFactory) {  
        DefaultJmsListenerContainerFactory bean = new DefaultJmsListenerContainerFactory();  
        bean.setConnectionFactory(activeMQConnectionFactory);
        /**
         * 使用消息转换器
         */
//        bean.setMessageConverter(jacksonJmsMessageConverter());
        return bean;
    }

    /**
     * 消息转换器
     * @return
     */
    @Bean
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }

}  