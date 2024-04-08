package com.sky.service;

import com.sky.entity.Setmeal;

import java.util.List;

public interface SetmealService {

    public List<Setmeal> findSetmealbyCategoryId(Long categoryId) ;
}
