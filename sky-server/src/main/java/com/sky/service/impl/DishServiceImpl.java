package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.BaseException;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.exception.SetmealEnableFailedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Autowired
    SetmealDishMapper setmealDishMapper;

    @Override
    @Transactional
    public void saveWithFlavor(DishDTO dishDTO) {

        //写入dish
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        dishMapper.save(dish);

        Long dishId = dish.getId();

        //写入favior
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors.size()>0){
            flavors.forEach(dishFlavor->dishFlavor.setDishId(dishId));
            dishFlavorMapper.insertBatch(flavors);
        }

    }

    @Override
    public PageResult findDishByName(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());
        Page<DishVO> page = dishMapper.findDishByName(dishPageQueryDTO);

        List<DishVO> dishVOlList = page.getResult();
        Long total = page.getTotal();

        return  new PageResult(total,dishVOlList);

    }

    @Override
    public DishVO findDishById(Long id) {
        DishVO dishVo = dishMapper.findDishById(id);
        return  dishVo;
    }

    @Transactional
    @Override
    public Boolean deleteDishById(List<Long> ids) {
        //如果是启用的菜品，就不能删除
        for(Long id:ids) {
            DishVO dishVo = dishMapper.findDishById(id);
            if (dishVo.getStatus()==1) {
                throw  new DeletionNotAllowedException("启用的状态菜品不能删除,"+dishVo.getName());
            }
        }

        //当前有关联套餐，不能删除
        List<Long> setmealIds = setmealDishMapper.findSetmealDishByDishIds(ids);
        if (setmealIds.size()>0) {
            throw  new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }

        try{
            dishMapper.deleteDishByIds(ids);
            dishFlavorMapper.deleteByDishIds(ids);
            return true;
        } catch ( Exception e){
            e.printStackTrace();
            return false;
        }
    }


    @Transactional
    @Override
    public Boolean updateDish(DishDTO dishDTO) {

        DishVO dishVO = dishMapper.findDishById(dishDTO.getId());
        if (dishVO==null) {
            throw  new BaseException("菜品不存在");
        }
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        try{
            dishMapper.updateDishById(dish);
            //删除原有口数据
            dishFlavorMapper.deleteByDishId(dishDTO.getId());

            //批处理insert口味数据
            dishFlavorMapper.insertBatch(dishDTO.getFlavors());
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;

        }


    }

    @Override
    @Transactional
    public Boolean updateDishStatusById(Long id,Integer status) {
        try{
            dishMapper.updateDishStatusById(id, status);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public List<DishVO> findDishByCategoryId(Long categoryId) {
        return null;
    }

}
