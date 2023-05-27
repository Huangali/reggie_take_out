package com.hulon.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hulon.reggie.dto.SetmealDto;
import com.hulon.reggie.entity.Setmeal;

/**
 * @author Hulon
 * @date 2023/5/21
 * @className SetmealService
 */
public interface SetmealService extends IService<Setmeal> {
    //新增套餐，同时插入2张表
    void saveWithDish(SetmealDto setmealDto);

    //获取套餐信息
    SetmealDto getByIdDish(Long id);

    //修改套餐信息
    void updateByIdDish(SetmealDto setmealDto);

    void deleteByIdDish(Long id);
}
