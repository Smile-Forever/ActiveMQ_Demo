package com.moreyou.activemq;

import org.apache.activemq.broker.BrokerService;

/**
 * @ClassName EmbeBroker
 * @Description TODO
 * @Author zxb
 * @Date 2020/3/24 20:14
 * @Version 1.0
 */
public class EmbeBroker {

    public static void main(String[] args) throws Exception {

        BrokerService brokerService = new BrokerService();
        brokerService.setUseJmx(true);
        brokerService.addConnector("tcp://localhost:61616");
        brokerService.start();
    }
}
