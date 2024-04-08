package com.sky.controller.user;


import com.fasterxml.jackson.databind.ser.Serializers;
import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.ShoppingCartService;
import com.sky.service.impl.ShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@Api(tags = "用户端购物车")
@RequestMapping("/user/shoppingcart")
public class ShopCartController {

    @Autowired
    ShoppingCartService shoppingCartService;

    @PostMapping("/add")
    @ApiOperation("购物车增加")
    public  Result addShopCartItem(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        log.info("添加购物车，信息为{}",shoppingCartDTO);
        shoppingCartService.addShopCartItem(shoppingCartDTO);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("购物车查看")
    public  Result<List<ShoppingCart>> getShopCartItem() {
        Long userId = BaseContext.getCurrentId();
        log.info("查看购物车，信息为{}",userId);
        List<ShoppingCart> shoppingCartList= shoppingCartService.findShopCartItem(userId);
        return Result.success(shoppingCartList);
    }

    @DeleteMapping("/clean")
    @ApiOperation("购物车查看")
    public  Result deleteShopCartItem() {
        Long userId = BaseContext.getCurrentId();
        userId=1L;
        log.info("查看购物车，信息为{}",userId);
        shoppingCartService.deleteShopCartItemByUserId(userId);
        return Result.success();
    }


}
