package com.wt.test.rocketmq.consumer.orderly;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.protocol.heartbeat.MessageModel;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author WuTian
 * @date 2018-11-06 14:50
 * @description
 */
@Slf4j
public class FirstOrderlyConsumer {
    public static void main(String[] args) {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("GID-WT-consumer-Test");
        consumer.setNamesrvAddr("192.168.54.112:9876");
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        try {
            consumer.subscribe("Topic-WT-test", "*");
            consumer.setMessageModel(MessageModel.CLUSTERING);
            consumer.registerMessageListener(
                    new MessageListenerOrderly() {
                        @Override
                        public ConsumeOrderlyStatus consumeMessage(final List<MessageExt> msgs,
                                                                   final ConsumeOrderlyContext context) {
                            for (MessageExt messageExt : msgs) {
                                try {
                                    System.out.println(Thread.currentThread().getName() + " receive new message:" + new String(messageExt.getBody(), "utf-8"));
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                            }
                            return ConsumeOrderlyStatus.SUCCESS;
                        }
                    });
            consumer.setConsumeMessageBatchMaxSize(10);
            consumer.start();
            log.info("First consumer started.");
//            consumer.fetchSubscribeMessageQueues("firstTopic");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
