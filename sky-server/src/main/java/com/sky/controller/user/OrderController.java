package com.sky.controller.user;


import com.sky.dto.OrdersSubmitDTO;
import com.sky.result.Result;
import com.sky.service.OrderService;
import com.sky.vo.OrderSubmitVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userOrderController")
@Slf4j
@RequestMapping("/user/order")
@Api(tags="C端用户下单")
public class OrderController {

    @Autowired
    OrderService orderService;
    @PostMapping("/submit")
    @ApiOperation("C端提交用户订单")
    public Result<OrderSubmitVO> submit(@RequestBody  OrdersSubmitDTO ordersSubmitDTO){
        log.info("提交订单{}",ordersSubmitDTO);
        OrderSubmitVO orderSubmitVO = orderService.submitOrder(ordersSubmitDTO);
        return Result.success(orderSubmitVO);

    }


}
