package com.wt.test.rocketmq.producer;

import com.wt.test.rocketmq.listener.TransactionListenerImpl;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author WuTian
 * @date 2018-11-27 13:03
 * @description
 */
@Component
public class OrderTransactionProducer {

    @Autowired
    private  TransactionListener transactionListener;

    @Value("${rocketmq.nameserver.url}")
    private String nameserverUrl;

    public void sendHalfMessage() {
        TransactionMQProducer producer = new TransactionMQProducer("secondGroup");
        producer.setTransactionListener(transactionListener);
        producer.setInstanceName("secondProducer");
        producer.setNamesrvAddr(nameserverUrl);
        try {
            producer.start();
            Message message = new Message("secondTopic", ("Create Order").getBytes(RemotingHelper.DEFAULT_CHARSET));
            TransactionSendResult sendResult = producer.sendMessageInTransaction(message, "Create Order");
            System.out.printf("%s%n", sendResult);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
