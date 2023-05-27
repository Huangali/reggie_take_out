package com.hulon.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hulon.reggie.dto.DishDto;
import com.hulon.reggie.entity.Dish;
import com.hulon.reggie.entity.DishFlavor;
import com.hulon.reggie.mapper.DishMapper;
import com.hulon.reggie.service.DishFlavorService;
import com.hulon.reggie.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Hulon
 * @date 2023/5/21
 * @className DishServiceImpl
 */
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;

    /**
     * 新增菜品
     * @param dishDto
     */
    @Override
    @Transactional
    public void saveWithFlavor(DishDto dishDto) {
        //将菜品的基本信息保存到菜品表dish
        this.save(dishDto);

        Long id = dishDto.getId();
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors.stream().map(item -> {
            item.setDishId(id);
            return item;
        }).collect(Collectors.toList());
        //将菜品口味数据到保存到菜品表dish
        dishFlavorService.saveBatch(flavors);



    }

    @Override
    public DishDto getByIDWithFlavor(Long id) {
        //查询菜品基本信息，从dish表查询
        Dish dish = this.getById(id);

        DishDto dishDto  = new DishDto();
        BeanUtils.copyProperties(dish,dishDto);
        //查询当前菜品对应的口味信息，从dish_flavor表查询
        LambdaQueryWrapper<DishFlavor> flavor = new LambdaQueryWrapper<>();
        flavor.eq(DishFlavor::getDishId,dish.getId());
        List<DishFlavor> list = dishFlavorService.list(flavor);

        dishDto.setFlavors(list);

        return dishDto;

    }

    @Transactional
    @Override
    public void updateWithFlavor(DishDto dishDto) {
        //更新dish表基本信息
        this.updateById(dishDto);

        //清理当前菜品对应口味数据 --dish_flavor delete操作
        LambdaQueryWrapper<DishFlavor> lqw = new LambdaQueryWrapper<>();
        lqw.eq(DishFlavor::getDishId,dishDto.getId());
        dishFlavorService.remove(lqw);

        //添加当前提交过来的口味数据 --dish_flavor insert操作
        List<DishFlavor> flavors = dishDto.getFlavors();

        flavors.stream().map(item -> {
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());
        //将菜品口味数据到保存到菜品表dish
        dishFlavorService.saveBatch(flavors);
    }
}
