package com.hulon.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hulon.reggie.dto.DishDto;
import com.hulon.reggie.entity.Dish;

/**
 * @author Hulon
 * @date 2023/5/21
 * @className DishService
 */
public interface DishService extends IService<Dish> {
    //新增菜品，同时插入2张表
    void saveWithFlavor(DishDto dishDto);

    //根据id查询菜品信息和对应的口味信息
    DishDto getByIDWithFlavor(Long id);

    void updateWithFlavor(DishDto dishDto);
}
