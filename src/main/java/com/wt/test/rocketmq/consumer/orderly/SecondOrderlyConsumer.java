package com.wt.test.rocketmq.consumer.orderly;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class SecondOrderlyConsumer {
    public static void main(String[] args) {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("GID-WT-consumer-Test");
        consumer.setNamesrvAddr("192.168.54.112:9876");
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        try {
            consumer.subscribe("Topic-WT-test", "*");
            consumer.setMessageModel(MessageModel.CLUSTERING);
            consumer.registerMessageListener(
                    new MessageListenerConcurrently() {
                        @Override
                        public ConsumeConcurrentlyStatus consumeMessage(final List<MessageExt> msgs,
                                                                        final ConsumeConcurrentlyContext context) {
                            for (MessageExt messageExt : msgs) {
                                try {
                                    System.out.println(Thread.currentThread().getName() + " receive new message:" + new String(messageExt.getBody(), "utf-8"));
                                    System.out.println(Thread.currentThread().getName() + " sleep 3000ms.");
                                    Thread.sleep(3000L);
                                    System.out.println(Thread.currentThread().getName() + " awake");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                        }
                    });
            consumer.setConsumeMessageBatchMaxSize(10);
            consumer.start();
            log.info("Second consumer started.");
//            consumer.fetchSubscribeMessageQueues("firstTopic");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
