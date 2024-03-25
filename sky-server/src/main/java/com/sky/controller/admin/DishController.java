package com.sky.controller.admin;


import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.impl.DishServiceImpl;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/admin/dish")
@Api(tags = "菜品相关")
public class DishController {

    @Autowired
    private DishServiceImpl dishService;

    @PostMapping
    @ApiOperation("新增菜品")
    public Result save(@RequestBody DishDTO dishDTO) {
        dishService.saveWithFlavor(dishDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("分页查询")
    public Result<PageResult> getDish(DishPageQueryDTO dishPageQueryDTO) {
        log.info("菜品分页查询,{}",dishPageQueryDTO);
        PageResult pageResult = dishService.findDishByName(dishPageQueryDTO);
        return  Result.success(pageResult);
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查菜品")
    public Result<DishVO> getDishById(@PathVariable  Long id) {
        log.info("菜品分页查询,{}",id);
        DishVO dishVo = dishService.findDishById(id);
        return  Result.success(dishVo);
    }

    @DeleteMapping()
    @ApiOperation("根据id删除菜品")
    public Result deleteDishById(@RequestParam List<Long> ids) {
        //参数用"," ,比如 : "1,2,3"
        log.info("菜品分页查询,{}",ids);
        Boolean result = dishService.deleteDishById(ids);
        if (result){
            return Result.success();
        } else {
            return Result.error("删除菜品失败");
        }
    }

    @PutMapping
    @ApiOperation("修改菜品")
    public Result updateDish(@RequestBody DishDTO dishDTO) {
        Boolean result =dishService.updateDish(dishDTO);
        if (result) {
            return Result.success();
        } else {
            return  Result.error("菜品修改失改");
        }

    }

    @PostMapping("/status/0")
    @ApiOperation("禁用菜品")
    public Result disableDish(@RequestParam Long id) {
        Boolean result =dishService.updateDishStatusById(id, StatusConstant.DISABLE);
        if (result) {
            return Result.success();
        } else {
            return  Result.error("菜品禁用失败");
        }
    }

    @PostMapping("/status/1")
    @ApiOperation("启用用菜品")
    public Result enableDish(@RequestParam Long id) {
        Boolean result =dishService.updateDishStatusById(id, StatusConstant.ENABLE);
        if (result) {
            return Result.success();
        } else {
            return  Result.error("菜品启用失败");
        }
    }

}
