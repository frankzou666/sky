package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {

    public  void saveWithFlavor (DishDTO dishDTO);

    public PageResult findDishByName(DishPageQueryDTO dishPageQueryDTO);

    public DishVO findDishById(Long id);

    public Boolean deleteDishById(List<Long> ids);

    public  Boolean updateDish (DishDTO dishDTO);

    public Boolean updateDishStatusById(Long id,Integer status);

    public List<DishVO> findDishByCategoryId(Long categoryId);
}
