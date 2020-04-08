package com.moreyou.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;

import javax.jms.*;

/**
 * @ClassName JmsConsumer_Redelivery
 * @Description TODO
 * @Author zxb
 * @Date 2020/4/8 19:43
 * @Version 1.0
 */
public class JmsConsumer_Redelivery {

    public static final String ACTIVE_URL = "tcp://192.168.238.129:61616";
    public static final String QUEUE_NAME = "queue-Redelivery";


    public static void main(String[] args) throws Exception {

        System.out.println("我是1号消费者");

        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVE_URL);
        //设置重发策略
        RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
        redeliveryPolicy.setMaximumRedeliveries(3);
        activeMQConnectionFactory.setRedeliveryPolicy(redeliveryPolicy);

        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);

        Queue queue = session.createQueue(QUEUE_NAME);

        MessageConsumer consumer = session.createConsumer(queue);

        while (true) {
            TextMessage textMessage = (TextMessage) consumer.receive(4000L);
            if (textMessage != null) {
                System.out.println("消费者接受到消息：" + textMessage.getText());
            } else {
                break;
            }
        }

        //session.commit();
        consumer.close();
        session.close();
        connection.close();

    }
}
