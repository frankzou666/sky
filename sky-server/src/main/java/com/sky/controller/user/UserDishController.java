package com.sky.controller.user;

import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user/dish/")
@Api(tags = "菜品相关类")
public class UserDishController {

    @Autowired
    DishService dishService;

    @GetMapping("/list/{categoryId}")
    @CachePut(cacheNames ="userDish",key="#categoryId")
    @ApiOperation("根据categoryId查询菜品")
    public Result<List<DishVO>> getDishbyCategoryId(Long categoryId) {
        List<DishVO> dishVOList = dishService.findDishByCategoryId(categoryId);
        return  Result.success(dishVOList);


    }
}
