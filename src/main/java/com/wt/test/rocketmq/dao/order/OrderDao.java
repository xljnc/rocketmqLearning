package com.wt.test.rocketmq.dao.order;

import com.wt.test.rocketmq.domain.order.Order;

/**
 * @author Xljnc
 * @date 2018/11/26 22:28
 * @description 订单类
 */
public interface OrderDao {
    void insert(Order order);
}
