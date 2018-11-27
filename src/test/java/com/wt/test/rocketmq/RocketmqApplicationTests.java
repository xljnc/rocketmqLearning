package com.wt.test.rocketmq;

import com.wt.test.rocketmq.consumer.OrderConsumer;
import com.wt.test.rocketmq.domain.account.Account;
import com.wt.test.rocketmq.domain.order.Order;
import com.wt.test.rocketmq.producer.OrderTransactionProducer;
import com.wt.test.rocketmq.service.account.AccountService;
import com.wt.test.rocketmq.service.order.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RocketmqApplicationTests {

    @Autowired
    private AccountService accountService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderConsumer orderConsumer;

    @Autowired
    private OrderTransactionProducer orderTransactionProducer;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testAccountAdd() {
        Account account = new Account();
        account.setMount(new BigDecimal(1000.00));
        accountService.addAccount(account);
        System.out.println("Id:" + account.getId());
    }

    @Test
    public void testOrderAdd() {
        Order order = new Order();
        order.setInfo("test Order");
        orderService.addOrder(order);
        System.out.println("Id:" + order.getId());
    }

    @Test
    public void testOrderTransactionMessage() {
        orderTransactionProducer.sendHalfMessage();
    }

    @Test
    public void testAccountTransactionMessage() {
        orderConsumer.finishPayment();
    }


}
