package com.sky.service.impl;

import com.sky.constant.StatusConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class ShopServiceImpl implements  ShopService{

    public  final static  String SHOP_STATUS="SHOP_STATUS";
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public int getShopStatus() {
        //默认店铺都是关闭的
        int status=0;
        try {
            Object obj =  redisTemplate.opsForValue().get(SHOP_STATUS);
            if (obj!=null) {
                status = (int) obj;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            status = 0;
        }
        return  status;
    }

    @Override
    public Boolean updateShopStatus(int status) {
        Boolean result;
        try {
            redisTemplate.opsForValue().set(SHOP_STATUS,status);
            result = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            result = false;
        }
        return  result;
    }

}
