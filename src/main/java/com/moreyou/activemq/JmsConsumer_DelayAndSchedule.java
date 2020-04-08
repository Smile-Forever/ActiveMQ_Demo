package com.moreyou.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @ClassName JmsConsumer_DelayAndSchedule
 * @Description TODO
 * @Author zxb
 * @Date 2020/4/7 21:07
 * @Version 1.0
 */
public class JmsConsumer_DelayAndSchedule {


    public static final String ACTIVE_URL = "tcp://192.168.238.129:61616";
    public static final String QUEUE_NAME = "queue-Delay";


    public static void main(String[] args) throws Exception {

        System.out.println("我是1号消费者");

        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVE_URL);



        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Queue queue = session.createQueue(QUEUE_NAME);

        MessageConsumer consumer = session.createConsumer(queue);

//        while (true) {
//            TextMessage textMessage = (TextMessage) consumer.receive(4000L);
//            if (textMessage != null) {
//                System.out.println("消费者接受到消息：" + textMessage.getText());
//            } else {
//                break;
//            }
//        }

        //监听模式
        consumer.setMessageListener(message -> {
            if ( null != message && message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println("textMessage：" + textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

        System.in.read();
        consumer.close();
        session.close();
        connection.close();

    }
}
