package com.moreyou.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @ClassName JmsProduce_Topic_Persist
 * @Description TODO
 * @Author zxb
 * @Date 2020/3/18 19:59
 * @Version 1.0
 */
public class JmsProduce_Topic_Persist {

    public static final String ACTIVE_URL = "tcp://192.168.238.129:61616";
    public static final String TOPIC_NAME = "Topic-jdbc-Persist";

    public static void main(String[] args) throws JMSException {

        //创建连接工厂
        ActiveMQConnectionFactory acf = new ActiveMQConnectionFactory(ACTIVE_URL);

        Connection connection = acf.createConnection();
        //两个参数  1、事务  2、签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建目的地(1、对列 2、目的地)
        Topic topic = session.createTopic(TOPIC_NAME);


        //创建消息的生产者
        MessageProducer messageProducer = session.createProducer(topic);
        messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);

        connection.start();
        for (int i = 1; i <= 3; i++) {
            //创建消息
            TextMessage textMessage = session.createTextMessage("msg-persist------>" + i);
            messageProducer.send(textMessage);

        }

        messageProducer.close();
        session.close();
        connection.close();

        System.out.println("msg-persist消息发送到MQ完成------>");

    }
}
