package com.wt.test.rocketmq.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @author WuTian
 * @date 2018-11-06 14:50
 * @description
 */
public class FirstConsumer {
    public static void main(String[] args) {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("firstGroup");
        consumer.setNamesrvAddr("192.168.197.128:9876");
        try {
            consumer.subscribe("firstTopic", "*");
            consumer.registerMessageListener(
                    new MessageListenerConcurrently() {
                        @Override
                        public ConsumeConcurrentlyStatus consumeMessage(final List<MessageExt> msgs,
                                                                        final ConsumeConcurrentlyContext context) {
                            System.out.println(Thread.currentThread().getName() + " receive new message:" + msgs);
                            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                        }
                    });
            consumer.start();
            consumer.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
