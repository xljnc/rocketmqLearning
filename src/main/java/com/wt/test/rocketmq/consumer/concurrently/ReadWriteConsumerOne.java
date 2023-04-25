package com.wt.test.rocketmq.consumer.concurrently;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.protocol.heartbeat.MessageModel;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author qiyu
 * @date 2021/5/6
 */
@Slf4j
public class ReadWriteConsumerOne {

    private static final String namesrvAddr = "127.0.0.1:30876";

    //    private static final String namesrvAddr= "192.168.54.112:9876";
    public static void main(String[] args) {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("GID-WT-ReadWrite-Consumer");
        consumer.setNamesrvAddr(namesrvAddr);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        try {
            consumer.subscribe("Topic-WT-ReadWrite", "*");
            consumer.setMessageModel(MessageModel.CLUSTERING);
            consumer.registerMessageListener(
                    new MessageListenerConcurrently() {
                        @Override
                        public ConsumeConcurrentlyStatus consumeMessage(final List<MessageExt> msgs,
                                                                        final ConsumeConcurrentlyContext context) {
                            for (MessageExt messageExt : msgs) {
                                try {
                                    System.out.println(Thread.currentThread().getName() + " receive new message:" + new String(messageExt.getBody(), "utf-8"));
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                            }
                            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                        }
                    });
//            consumer.setConsumeMessageBatchMaxSize(30);
//            consumer.setPullBatchSize(20);
            consumer.start();
            log.info("First consumer started.");
//            consumer.fetchSubscribeMessageQueues("firstTopic");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
