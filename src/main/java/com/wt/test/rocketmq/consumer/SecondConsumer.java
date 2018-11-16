package com.wt.test.rocketmq.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

/**
 * @author WuTian
 * @date 2018-11-06 14:50
 * @description
 */
public class SecondConsumer {
    public static void main(String[] args) {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("firstGroup");
        consumer.setNamesrvAddr("192.168.197.128:9876");
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        try {
            consumer.subscribe("firstTopic", "*");
            consumer.setMessageModel(MessageModel.BROADCASTING);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
