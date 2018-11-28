package com.wt.test.rocketmq.consumer;

import com.wt.test.rocketmq.domain.account.Account;
import com.wt.test.rocketmq.service.account.AccountService;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author WuTian
 * @date 2018-11-27 15:36
 * @description
 */
@Service
public class OrderConsumer {

    @Autowired
    private AccountService accountService;

    @Value("${rocketmq.nameserver.url}")
    private String nameserverUrl;

    public void finishPayment() {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("secondGroup");
        consumer.setNamesrvAddr(nameserverUrl);
        consumer.setInstanceName("secondConsumer");
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        try {
            consumer.subscribe("secondTopic", "*");
            consumer.setMessageModel(MessageModel.CLUSTERING);
            final boolean[] result = {false};
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                    System.out.println("start account deal.");
                    Account account = new Account();
                    account.setId(1);
                    account.setMount(BigDecimal.valueOf(100.00));
                    accountService.updateAccount(account);
                    result[0] = true;
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
            consumer.start();
            System.out.println("consumer start.");
            while(!result[0]){
                System.out.println("no order income");
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
