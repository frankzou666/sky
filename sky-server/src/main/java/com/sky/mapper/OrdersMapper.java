package com.sky.mapper;

import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrdersMapper {

    /**
     * 根据分类id查询菜品数量
     * @return
     */


    void save(Orders orders);



}
