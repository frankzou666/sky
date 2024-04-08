package com.sky.service;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {
    public  void addShopCartItem(ShoppingCartDTO shoppingCartDTO);

    public List<ShoppingCart> findShopCartItem(Long userId);

    public void deleteShopCartItemByUserId(Long userId);

}
