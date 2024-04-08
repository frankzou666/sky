package com.sky.controller.user;


import com.sky.dto.ShoppingCartDTO;
import com.sky.result.Result;
import com.sky.service.ShoppingCartService;
import com.sky.service.impl.ShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

}
