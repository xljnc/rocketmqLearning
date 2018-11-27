package com.wt.test.rocketmq.producer;

import com.wt.test.rocketmq.domain.order.Order;
import com.wt.test.rocketmq.service.order.OrderService;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author WuTian
 * @date 2018-11-27 13:03
 * @description
 */
@Component
public class OrderTransactionProducer {

    @Autowired
    private OrderService orderService;

    public void sendHalfMessage() {
        TransactionListener transactionListener = new TransactionListenerImpl();
        TransactionMQProducer producer = new TransactionMQProducer("secondGroup");
        producer.setTransactionListener(transactionListener);
        producer.setInstanceName("secondProducer");
        producer.setNamesrvAddr("192.168.197.128:9876");
        try {
            producer.start();
            Message message = new Message("secondTopic", ("Create Order").getBytes(RemotingHelper.DEFAULT_CHARSET));
            TransactionSendResult result = producer.sendMessageInTransaction(message, "Create Order");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    class TransactionListenerImpl implements TransactionListener {

        ConcurrentHashMap<String, LocalTransactionState> localTrans = new ConcurrentHashMap<>(16);

        @Override
        public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
            try {
                System.out.println("arg is:" + arg);
                Order order = new Order();
                order.setInfo("test order");
                orderService.addOrder(order);
                localTrans.put(msg.getTransactionId(), LocalTransactionState.COMMIT_MESSAGE);
                return LocalTransactionState.COMMIT_MESSAGE;
            } catch (Exception e) {
                e.printStackTrace();
                localTrans.put(msg.getTransactionId(), LocalTransactionState.ROLLBACK_MESSAGE);
                return LocalTransactionState.ROLLBACK_MESSAGE;
            }
        }

        @Override
        public LocalTransactionState checkLocalTransaction(MessageExt msg) {
            if (!localTrans.containsKey(msg.getTransactionId())) {
                return LocalTransactionState.UNKNOW;
            }
            return localTrans.get(msg.getTransactionId());
        }
    }


}
