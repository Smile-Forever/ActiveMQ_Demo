package com.moreyou.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @ClassName JmsConsumer_TX
 * @Description TODO
 * @Author zxb
 * @Date 2020/3/18 20:56
 * @Version 1.0
 */
public class JmsConsumer_TX {

    public static final String ACTIVE_URL = "tcp://192.168.238.129:61616";
    public static final String QUEUE_NAME = "queue01";


    public static void main(String[] args) throws Exception {

        System.out.println("我是2号消费者");

        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVE_URL);

        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(true, Session.CLIENT_ACKNOWLEDGE);

        Queue queue = session.createQueue(QUEUE_NAME);

        MessageConsumer messageConsumer = session.createConsumer(queue);

        //监听模式
//        consumer.setMessageListener(new MessageListener() {
//            public void onMessage(Message message) {
//                if ( null != message && message instanceof TextMessage) {
//                    TextMessage textMessage = (TextMessage) message;
//                    try {
//                        System.out.println("textMessage：" + textMessage.getText());
//                    } catch (JMSException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });

        while (true) {
            TextMessage textMessage = (TextMessage) messageConsumer.receive(4000L);
            if (textMessage != null) {
                System.out.println("消费者接受到消息：" + textMessage.getText());
//                textMessage.acknowledge();
            } else {
                break;
            }
        }

        messageConsumer.close();
        session.commit();
        session.close();
        connection.close();

    }

}
