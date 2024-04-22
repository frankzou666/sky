package com.sky.mapper;

import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OrdersMapper {

    /**
     * 根据分类id查询菜品数量
     * @return
     */


    void save(Orders orders);

    List<Orders> findOrdersByStatusAndOrderTime(@Param("status") Integer status, @Param("orderTime")LocalDateTime orderTime);

    void update(Orders orders);


    double sumByDateAndStatus(@Param("startDate") LocalDate startDate,@Param("status") int status);



}
