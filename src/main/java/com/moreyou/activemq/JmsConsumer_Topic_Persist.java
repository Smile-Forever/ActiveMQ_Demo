package com.moreyou.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * @ClassName JmsConsumer_Topic_Persist
 * @Description TODO
 * @Author zxb
 * @Date 2020/3/18 19:59
 * @Version 1.0
 */
public class JmsConsumer_Topic_Persist {

    public static final String ACTIVE_URL = "tcp://192.168.238.129:61616";
    public static final String TOPIC_NAME = "Topic-jdbc-Persist";

    public static void main(String[] args) throws JMSException, IOException {

        System.out.println("*********z3");

        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVE_URL);

        Connection connection = activeMQConnectionFactory.createConnection();

        connection.setClientID("moreyou01");

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Topic topic = session.createTopic(TOPIC_NAME);
        TopicSubscriber topicSubscriber = session.createDurableSubscriber(topic, "mq-jdbc");

        connection.start();

        Message message = topicSubscriber.receive(4000L);

        while (null != message) {
            TextMessage textMessage = (TextMessage) message;
            System.out.println("****收到的持久化Topic" + textMessage.getText());
            message = topicSubscriber.receive();
        }

        session.close();
        connection.close();

    }

}
