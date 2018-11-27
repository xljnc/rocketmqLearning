package com.wt.test.rocketmq.service.order;

import com.wt.test.rocketmq.domain.order.Order;

/**
 * @author Xljnc
 * @date 2018/11/26 23:09
 * @description
 */
public interface OrderService {
    void addOrder(Order order);
}
