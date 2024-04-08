package com.sky.mapper;

import com.sky.entity.OrderDetail;
import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderDetailMapper {

    /**
     * 根据分类id查询菜品数量
     * @return
     */


    void save(OrderDetail orderDetail);

    void saveBatch(List<OrderDetail> orderDetailList);


}
