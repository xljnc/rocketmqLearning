package com.wt.test.rocketmq.listener;

import com.wt.test.rocketmq.domain.order.Order;
import com.wt.test.rocketmq.service.order.OrderService;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Xljnc
 * @date 2018/11/27 22:53
 * @description
 */
@Component
public class TransactionListenerImpl implements TransactionListener {
    private ConcurrentHashMap<String, LocalTransactionState> localTrans = new ConcurrentHashMap<>(16);

    @Autowired
    private OrderService orderService;

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

