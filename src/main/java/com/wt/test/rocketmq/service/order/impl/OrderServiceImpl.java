package com.wt.test.rocketmq.service.order.impl;

import com.wt.test.rocketmq.dao.order.OrderDao;
import com.wt.test.rocketmq.domain.order.Order;
import com.wt.test.rocketmq.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Xljnc
 * @date 2018/11/26 23:10
 * @description
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;

    @Override
    public void addOrder(Order order) {
         orderDao.insert(order);
    }
}
