package com.moreyou.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ScheduledMessage;
import org.springframework.scheduling.annotation.Scheduled;

import javax.jms.*;

/**
 * @ClassName JmsProduce_DelayAndSchedule
 * @Description TODO
 * @Author zxb
 * @Date 2020/4/7 21:06
 * @Version 1.0
 *
 * 延迟投递案例
 *
 */
public class JmsProduce_DelayAndSchedule {


    public static final String ACTIVE_URL = "tcp://192.168.238.129:61616";
    public static final String QUEUE_NAME = "queue-Delay";

    public static void main(String[] args) throws JMSException {
        //创建连接工厂
        ActiveMQConnectionFactory acf = new ActiveMQConnectionFactory(ACTIVE_URL);
        //获得连接
        Connection connection = acf.createConnection();
        connection.start();
        //两个参数  1、事务  2、签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建目的地(1、对列 2、目的地)
        Queue queue = session.createQueue(QUEUE_NAME);

        //创建消息的生产者
        MessageProducer messageProducer = session.createProducer(queue);
        //延迟3秒
        long delay = 3 * 1000;
        //4秒投递一次 重复投递间隔时间
        long period = 4 * 1000;
        //投递五次
        int repeat = 5;


        for (int i = 1; i <= 3; i++) {
            //创建消息
            TextMessage textMessage = session.createTextMessage("delay msg------>" + i);

            textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY,delay);
            textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_PERIOD,period);
            textMessage.setIntProperty(ScheduledMessage.AMQ_SCHEDULED_REPEAT,repeat);

            messageProducer.send(textMessage);

        }

        messageProducer.close();
        session.close();
        connection.close();

        System.out.println("消息发送到MQ完成------>");
    }
}
