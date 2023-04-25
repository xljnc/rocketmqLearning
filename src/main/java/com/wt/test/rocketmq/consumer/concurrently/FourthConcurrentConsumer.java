package com.wt.test.rocketmq.consumer.concurrently;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.protocol.heartbeat.MessageModel;

import java.util.List;

/**
 * @author WuTian
 * @date 2018-11-06 14:50
 * @description
 */
@Slf4j
public class FourthConcurrentConsumer {

//    private static final String namesrvAddr = "127.0.0.1:30876";

    private static final String namesrvAddr= "192.168.54.112:9876";

    public static void main(String[] args) {

        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("GID-WT-consumer-Test");
        consumer.setNamesrvAddr(namesrvAddr);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        try {
            consumer.subscribe("Topic-WT-test", "*");
            consumer.setMessageModel(MessageModel.CLUSTERING);
            consumer.registerMessageListener(
                    new MessageListenerConcurrently() {
                        @Override
                        public ConsumeConcurrentlyStatus consumeMessage(final List<MessageExt> msgs,
                                                                        final ConsumeConcurrentlyContext context) {
                            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                        }
                    });
            consumer.setConsumeMessageBatchMaxSize(10);
            consumer.start();
            log.info("Fourth consumer started.");
//            consumer.fetchSubscribeMessageQueues("firstTopic");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
