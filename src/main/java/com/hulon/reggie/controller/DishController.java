package com.hulon.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hulon.reggie.common.R;
import com.hulon.reggie.dto.DishDto;
import com.hulon.reggie.entity.Category;
import com.hulon.reggie.entity.Dish;
import com.hulon.reggie.entity.DishFlavor;
import com.hulon.reggie.service.CategoryService;
import com.hulon.reggie.service.DishFlavorService;
import com.hulon.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Hulon
 * @date 2023/5/24
 * @className DishController
 */
@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {
    @Autowired
    private DishService dishService;

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    CategoryService categoryService;

    /**
     * 新增菜品
     * @param dishDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto){
        dishService.saveWithFlavor(dishDto);
        return R.success("添加成功");
    }

    /**
     * 分页信息查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        Page<Dish> pageInfo = new Page(page,pageSize);
        Page<DishDto> dishDtoPageInfo = new Page(page,pageSize);

        //构造条件构造器
        LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper<>();
        //添加过滤条件
        lqw.like(StringUtils.isNotEmpty(name),Dish::getName,name);

        //构造排序条件
        lqw.orderByDesc(Dish::getUpdateTime);
        dishService.page(pageInfo,lqw);

        //对象拷贝
        BeanUtils.copyProperties(pageInfo,dishDtoPageInfo,"records");

        List<Dish> records = pageInfo.getRecords();
        List<DishDto> collect = records.stream().map(item -> {

            DishDto dishDto = new DishDto();

            BeanUtils.copyProperties(item, dishDto);
            //分类id
            Long categoryId = item.getCategoryId();
            //根据id查询分类对象

            Category byId = categoryService.getById(categoryId);

            if (byId != null){
                String name1 = byId.getName();
                dishDto.setCategoryName(name1);
            }


            return dishDto;
        }).collect(Collectors.toList());
        dishDtoPageInfo.setRecords(collect);

        return R.success(dishDtoPageInfo);
    }

    /**
     * 根据id查询菜品信息和口味信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<DishDto> dishInfo(@PathVariable Long id){
        DishDto byIDWithFlavor = dishService.getByIDWithFlavor(id);
        return R.success(byIDWithFlavor);
    }

    /**
     * 修改
     * @param dishDto
     * @return
     */
    @PutMapping
    public R<String> dishUpdate(@RequestBody DishDto dishDto){
        dishService.updateWithFlavor(dishDto);
        return R.success("修改成功");
    }

    /**
     * 根据条件查询对应的菜品数据
     * @param dish
     * @return
     */
    @GetMapping("/list")
    public R<List<DishDto>> list(Dish dish){
        LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper<>();
        lqw.eq(dish.getCategoryId() != null,Dish::getCategoryId,dish.getCategoryId());
        lqw.eq(Dish::getStatus,1);
        lqw.orderByAsc(Dish::getSort).orderByDesc(Dish::getCreateTime);
        List<Dish> list = dishService.list(lqw);

        List<DishDto> listDto = list.stream().map(item -> {
            DishDto dishDto = new DishDto();

            BeanUtils.copyProperties(item,dishDto);

            Long categoryId = item.getCategoryId();
            //根据id查询分类对象

            Category byId = categoryService.getById(categoryId);

            if (byId != null){
                String name1 = byId.getName();
                dishDto.setCategoryName(name1);
            }

            Long dishId = dishDto.getId();

            LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();

            lambdaQueryWrapper.eq(DishFlavor::getDishId,dishId);

            List<DishFlavor> list1 = dishFlavorService.list(lambdaQueryWrapper);

            dishDto.setFlavors(list1);

            return dishDto;


        }).collect(Collectors.toList());



        return R.success(listDto);
    }


}
