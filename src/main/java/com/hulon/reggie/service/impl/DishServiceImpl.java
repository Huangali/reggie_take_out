package com.hulon.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hulon.reggie.entity.Dish;
import com.hulon.reggie.mapper.DishMapper;
import com.hulon.reggie.service.DishService;
import org.springframework.stereotype.Service;

/**
 * @author Hulon
 * @date 2023/5/21
 * @className DishServiceImpl
 */
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
}
