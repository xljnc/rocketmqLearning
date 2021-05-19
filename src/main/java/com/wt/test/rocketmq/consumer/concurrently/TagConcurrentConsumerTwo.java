package com.wt.test.rocketmq.consumer.concurrently;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.io.UnsupportedEncodingException;

/**
 * @author 一贫
 * @date 2021/5/8
 */
@Slf4j
public class TagConcurrentConsumerTwo {

//    private static final String namesrvAddr = "127.0.0.1:30876";

    private static final String namesrvAddr = "192.168.54.112:9876";

    public static void main(String[] args) {

        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("GID-WT-Tag-Consumer-2");
        consumer.setNamesrvAddr(namesrvAddr);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        try {
            consumer.subscribe("Topic-WT-test", "tag3");
            consumer.setMessageModel(MessageModel.CLUSTERING);
            consumer.registerMessageListener(
                    (MessageListenerConcurrently) (x, y) -> {
                        for (MessageExt messageExt : x) {
                            try {
                                System.out.println(Thread.currentThread().getName() + " receive new message:" + new String(messageExt.getBody(), "utf-8") + ",tag:" + messageExt.getTags());
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    });
            consumer.setConsumeMessageBatchMaxSize(10);
            consumer.start();
            log.info("Tag consumer two started.");
//            consumer.fetchSubscribeMessageQueues("firstTopic");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}