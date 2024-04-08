package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.ShoppingCart;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.mapper.ShoppingCartMapper;
import com.sky.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@Api("tags=添加购物车service方法")
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    ShoppingCartMapper shoppingCartMapper;

    @Autowired
    DishMapper dishMapper;

    @Autowired
    SetmealMapper setmealMapper;

    @Override
    @ApiOperation("增加购物车项目")
    public void addShopCartItem(ShoppingCartDTO shoppingCartDTO) {

        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO,shoppingCart);

        //得到用户id
        Long userId = BaseContext.getCurrentId();
        if (userId==null) { userId = 1L; }
        shoppingCart.setUserId(userId);

        //先查询用户ID是否有
        List<ShoppingCart> shoppingCartList = shoppingCartMapper.findShoppingCartByUserId(shoppingCart);
        //如果之前有了，就更新,数量加1
        if (shoppingCartList!=null && shoppingCartList.size()>0) {
            ShoppingCart shoppingCartObj = shoppingCartList.get(0);
            shoppingCartObj.setNumber(shoppingCartObj.getNumber() + 1);
            shoppingCartMapper.updateShopCartNumberById(shoppingCartObj);
        } else {
            //如果没有，就insert
            Long dishId = shoppingCartDTO.getDishId();
            Long setmealId= shoppingCartDTO.getSetmealId();
            if (dishId!=null) {
                Dish dish = dishMapper.getDishById(dishId);
                shoppingCart.setName(dish.getName());
                shoppingCart.setImage(dish.getImage());
                shoppingCart.setAmount(dish.getPrice());
            } else {

                Setmeal setmeal = setmealMapper.findSetmealBy(setmealId);
                shoppingCart.setName(setmeal.getName());
                shoppingCart.setImage(setmeal.getImage());
                shoppingCart.setAmount(setmeal.getPrice());
            }
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCart.setNumber(1);
            shoppingCartMapper.save(shoppingCart);

        }



    }
}
