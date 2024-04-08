package com.sky.controller.user;


import com.sky.entity.Setmeal;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "套餐管理")
@RestController
@Slf4j
@RequestMapping("/user/setmeal")
public class SetmealController {

    @Autowired
    SetmealService setmealService;


    @GetMapping("/list")
    //比如11 ,redis的key就是setmeal:11
    @Cacheable(cacheNames ="setmeal",key="#categoryId")
    @ApiOperation("根据categoryId查询套餐")
    public Result<List<Setmeal>> getDishbyCategoryId( Long categoryId) {
        List<Setmeal> setmealList = setmealService.findSetmealbyCategoryId(categoryId);
        return  Result.success(setmealList);
    }


}
