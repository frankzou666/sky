package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface DishMapper {

    /**
     * 根据分类id查询菜品数量
     * @param categoryId
     * @return
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);


    @AutoFill(OperationType.INSERT)
    Integer save(Dish dish);

    Page<DishVO> findDishByName(DishPageQueryDTO dishPageQueryDTO);

    DishVO findDishById(Long id);


    @Select("select * from dish where id = #{id}")
    Dish getDishById(Long id);

    void deleteDishById(Long id);

    void deleteDishByIds(List<Long> ids);

    @AutoFill(value = OperationType.UPDATE)
    void updateDishById(Dish dish);

    void updateDishStatusById(@Param("id") Long id, @Param("status") Integer status);

    Integer countByMap(Map map);



}
