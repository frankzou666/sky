package com.sky.mapper;


import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {
    public List<ShoppingCart> findShoppingCartByUserId(ShoppingCart shoppingCart);
    public  void updateShopCartNumberById(ShoppingCart shoppingCart);

    public  void save(ShoppingCart shoppingCart);
}
