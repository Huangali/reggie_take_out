package com.hulon.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hulon.reggie.config.CustomException;
import com.hulon.reggie.entity.Category;
import com.hulon.reggie.entity.Dish;
import com.hulon.reggie.entity.Setmeal;
import com.hulon.reggie.mapper.CategoryMapper;
import com.hulon.reggie.service.CategoryService;
import com.hulon.reggie.service.DishService;
import com.hulon.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author Hulon
 * @date 2023/5/21
 * @className CategoryServiceImpl
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;
    /**
     * 根据id删除分类
     * @param id
     */
    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishLqw = new LambdaQueryWrapper<>();
        //添加查询条件，根据分类id进行查询
        dishLqw.eq(Dish::getCategoryId,id);
        int count = dishService.count(dishLqw);

        //查询当前分类是否关联了菜品，如果关联来，抛出一个业务异常
        if (count > 0){
            //已经关联了菜品，抛出一个业务异常
            throw new CustomException("当前分类下关联了菜品，不能删除");
        }


        //查询当前分类是否关联了套餐，如果关联来，抛出一个业务异常
        LambdaQueryWrapper<Setmeal> setLqw = new LambdaQueryWrapper<>();
        setLqw.eq(Setmeal::getCategoryId,id);
        int count1 = setmealService.count(setLqw);
        if (count1 > 0){
            //已经关联了套餐，抛出一个业务异常
            throw new CustomException("当前分类下关联了套餐，不能删除");
        }
        //正常删除分类
        super.removeById(id);
    }
}
