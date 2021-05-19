package com.wt.test.rocketmq.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;

/**
 * @author 一贫
 * @date 2021/5/19
 */
@Slf4j
public class StartMultiProducerInOneAppTest {
    //    private static final String namesrvAddr = "127.0.0.1:30876";

    private static final String namesrvAddr = "192.168.54.112:9876";

    public static void main(String[] args) {
        DefaultMQProducer producer1 = new DefaultMQProducer("GID-WT-producer-Test");
        producer1.setInstanceName("GID-WT-producer-Test");
        producer1.setNamesrvAddr(namesrvAddr);
        DefaultMQProducer producer2 = new DefaultMQProducer("GID-WT-producer-Test");
        producer2.setInstanceName("GID-WT-producer-Test");
        producer2.setNamesrvAddr(namesrvAddr);
        try {
            producer1.start();
            log.info("producer1启动成功");
            producer2.start();
            log.info("producer2启动成功");
            producer1.shutdown();
            producer2.shutdown();
        } catch (Exception e){
            log.error("启动失败",e);
        }
    }
}
