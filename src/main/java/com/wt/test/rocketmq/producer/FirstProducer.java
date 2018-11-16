package com.wt.test.rocketmq.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * @author WuTian
 * @date 2018-11-06 11:22
 * @description
 */
public class FirstProducer {
    public static void main(String[] args) {
        DefaultMQProducer producer = new DefaultMQProducer("firstGroup");
        producer.setInstanceName("firstProducer");
        producer.setNamesrvAddr("192.168.197.128:9876");
        try {
            producer.start();
            System.out.println("producer start.");
            System.out.println("sending message.");
            for (int i = 0; i < 100; i++) {
                Message message = new Message("firstTopic", ("Hello World " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
                SendResult sendResult = producer.send(message);
                System.out.printf("%s%n", sendResult);
            }
            producer.shutdown();
            System.out.println("producer shutdown.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
