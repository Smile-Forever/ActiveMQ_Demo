package com.moreyou.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @ClassName Topic_Produce
 * @Description TODO
 * @Author zxb
 * @Date 2020/3/16 19:42
 * @Version 1.0
 */
public class Topic_Produce {

    public static final String ACTIVE_URL = "tcp://192.168.238.129:61616";
    public static final String TOPIC_NAME = "topic01";


    public static void main(String[] args) throws Exception {

            //创建连接工厂
            ActiveMQConnectionFactory acf = new ActiveMQConnectionFactory(ACTIVE_URL);

            Connection connection = acf.createConnection();
            connection.start();
            //两个参数  1、事务  2、签收
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            //创建目的地(1、对列 2、目的地)
            Topic topic = session.createTopic(TOPIC_NAME);

        //创建消息的生产者
            MessageProducer messageProducer = session.createProducer(topic);
            for (int i = 1; i <= 3; i++) {
                //创建消息
                TextMessage textMessage = session.createTextMessage("TOPIC_NAME------>" + i);
                messageProducer.send(textMessage);
            }

            messageProducer.close();
            session.close();
            connection.close();

            System.out.println("TOPIC_NAME消息发送到MQ完成------>");
        }
    }
