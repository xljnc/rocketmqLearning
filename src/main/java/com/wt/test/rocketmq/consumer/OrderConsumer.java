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

    public void finishPayment() {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer();
        consumer.setNamesrvAddr("192.168.197.128:9876");
        consumer.setInstanceName("secondConsumer");
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        try {
            consumer.subscribe("secondTopic", "*");
            consumer.setMessageModel(MessageModel.CLUSTERING);
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                    Account account = new Account();
                    account.setId(11);
                    account.setMount(BigDecimal.valueOf(100.00));
                    accountService.updateAccount(account);
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
