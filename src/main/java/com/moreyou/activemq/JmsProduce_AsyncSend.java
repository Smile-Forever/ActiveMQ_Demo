package com.moreyou.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageProducer;
import org.apache.activemq.AsyncCallback;

import javax.jms.*;
import java.util.UUID;

/**
 * @ClassName JmsProduce_AsyncSend
 * @Description TODO
 * @Author zxb
 * @Date 2020/4/7 20:43
 * @Version 1.0
 * 异步发送回调案例
 */
public class JmsProduce_AsyncSend {


    public static final String ACTIVE_URL = "tcp://192.168.238.129:61616";
    public static final String QUEUE_NAME = "queue-AsyncSend";

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
//        MessageProducer messageProducer = session.createProducer(queue);

        ActiveMQMessageProducer activeMQMessageProducer = (ActiveMQMessageProducer) session.createProducer(queue);

        activeMQMessageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);

        TextMessage textMessage = null;

        for (int i = 1; i <= 3; i++) {
            //创建消息
            textMessage = session.createTextMessage("AsyncSend msg------>" + i);

            textMessage.setJMSCorrelationID(UUID.randomUUID().toString() + "---orderMoreyou");

            String msgId = textMessage.getJMSCorrelationID();

            activeMQMessageProducer.send(textMessage, new AsyncCallback() {
                @Override
                public void onSuccess() {
                    System.out.println(msgId + "has been success");
                }

                @Override
                public void onException(JMSException exception) {
                    System.out.println(msgId + "has been fail");
                }
            });

        }

        activeMQMessageProducer.close();
        session.close();
        connection.close();

        System.out.println("消息发送到MQ完成------>");
    }
}
