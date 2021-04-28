package com.wt.test.rocketmq.consumer.pull;

import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.common.message.MessageQueue;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

/**
 * https://my.oschina.net/u/4863972/blog/4990866
 *
 * @author 一贫
 * @date 2021/4/28
 */
public class TestPullConsumer {

    private static final String namesrvAddr = "127.0.0.1:30876";

    //    private static final String namesrvAddr= "192.168.54.112:9876";

    public static void main(String[] args) throws Exception {
        DefaultLitePullConsumer consumer = new DefaultLitePullConsumer("GID-WT-consumer-Test");
        consumer.setNamesrvAddr(namesrvAddr);
//        consumer.subscribe("Topic-WT-test2", "*");
        consumer.start();
        Collection<MessageQueue> messageQueues = consumer.fetchMessageQueues("Topic-WT-test2");
        if (!CollectionUtils.isEmpty(messageQueues)) {
            while (true) {
                for (MessageQueue queue : messageQueues) {
                    System.out.printf("Consume from the queue: %s%n", queue);
                    consumer.poll();
                    System.out.printf("%s%n", pullResult);
                }
            }
        }

    }
}
