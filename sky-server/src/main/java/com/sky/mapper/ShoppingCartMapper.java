package com.sky.mapper;


import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {
    public List<ShoppingCart> findShoppingCartByUserId(ShoppingCart shoppingCart);
    public  void updateShopCartNumberById(ShoppingCart shoppingCart);

    public  void save(ShoppingCart shoppingCart);

    public List<ShoppingCart> findShopCartItem(@Param("userId") Long userId);

    public void deleteShopCartItemByUserId(@Param("userId") Long userId);


}
