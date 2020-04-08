package com.moreyou.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @ClassName JmsProduce
 * @Description TODO
 * @Author zxb
 * @Date 2020/3/15 14:04
 * @Version 1.0
 */
public class JmsProduce {

      public static final String ACTIVE_URL = "tcp://192.168.238.129:61616";
//    public static final String ACTIVE_URL = "nio://192.168.238.129:61608";
//    public static final String ACTIVE_URL = "tcp://localhost:61616";
    public static final String QUEUE_NAME = "jdbc01";

    public static void main(String[] args) throws JMSException {
        //创建连接工厂
        ActiveMQConnectionFactory acf = new ActiveMQConnectionFactory(ACTIVE_URL);
        //设置异步投放消息
        acf.setUseAsyncSend(true);
        Connection connection = acf.createConnection();
        connection.start();
        //两个参数  1、事务  2、签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建目的地(1、对列 2、目的地)
        Queue queue = session.createQueue(QUEUE_NAME);

        //创建消息的生产者
        MessageProducer messageProducer = session.createProducer(queue);
        messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);
        for (int i = 1; i <= 3; i++) {
            //创建消息
            TextMessage textMessage = session.createTextMessage("jdbc msg------>" + i);
            messageProducer.send(textMessage);

        }

        messageProducer.close();
        session.close();
        connection.close();

        System.out.println("消息发送到MQ完成------>");
    }

}




