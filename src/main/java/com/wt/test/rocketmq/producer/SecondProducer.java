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
public class SecondProducer {
    public static void main(String[] args) {
        DefaultMQProducer producer = new DefaultMQProducer("GID-WT-producer-Test");
        producer.setInstanceName("GID-WT-producer-Test");
        producer.setNamesrvAddr("192.168.54.112:9876");
        try {
            producer.start();
            System.out.println("Second producer start.");
            System.out.println("sending message.");
            for (int i = 0; i < 100; i++) {
                Message message = new Message("Topic-WT-test2", ("Hello World " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
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
