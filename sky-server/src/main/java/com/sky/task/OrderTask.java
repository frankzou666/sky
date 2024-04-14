package com.sky.task;


import com.sky.entity.Orders;
import com.sky.mapper.OrdersMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
public class OrderTask {

    @Autowired
    OrdersMapper ordersMapper;
    //每分钟处理一次
    @Scheduled(cron ="0 0/30 * * * ?")
    public void processTimeoutOrder() {
        //定时处理超时订单，每分钟一次
        log.info("今天的日期:"+ LocalDateTime.now());
        List<Orders> ordersList = ordersMapper.findOrdersByStatusAndOrderTime(Orders.PENDING_PAYMENT,LocalDateTime.now());

        if(ordersList!=null && ordersList.size()>0) {
            for(Orders order:ordersList) {
                order.setStatus(Orders.CANCELLED);
                order.setCancelTime(LocalDateTime.now());
                order.setCancelReason("超时订单，系统自取消");
                ordersMapper.update(order);
            }
        }

    }
}

