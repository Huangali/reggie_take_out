package com.hulon.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hulon.reggie.common.R;
import com.hulon.reggie.dto.SetmealDto;
import com.hulon.reggie.entity.Category;
import com.hulon.reggie.entity.Setmeal;
import com.hulon.reggie.service.CategoryService;
import com.hulon.reggie.service.DishService;
import com.hulon.reggie.service.SetmealDishService;
import com.hulon.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Hulon
 * @date 2023/5/25
 * @className SetmealController
 */
@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private DishService dishService;

    /**
     * 新增套餐
     * @param setmealDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto){
        setmealService.saveWithDish(setmealDto);
        return R.success("套餐添加成功");
    }

    /**
     * 套餐分页信息
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){

        Page<Setmeal> setmealPage = new Page<>();
        Page<SetmealDto> pageInfo = new Page<>();

        LambdaQueryWrapper<Setmeal> lqw = new LambdaQueryWrapper<>();
        lqw.like(name != null,Setmeal::getName,name);

        setmealService.page(setmealPage,lqw);

        BeanUtils.copyProperties(setmealPage,pageInfo,"records");

        List<Setmeal> records = setmealPage.getRecords();

        List<SetmealDto> collect = records.stream().map(setmeal -> {
            SetmealDto setmealDto = new SetmealDto();
            Long categoryId = setmeal.getCategoryId();

            BeanUtils.copyProperties(setmeal, setmealDto);

            Category byId = categoryService.getById(categoryId);
            if (categoryId != null) {
                setmealDto.setCategoryName(byId.getName());
            }
            return setmealDto;
        }).collect(Collectors.toList());
        pageInfo.setRecords(collect);

        return R.success(pageInfo);
    }

    /**
     * 根据id获取信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<SetmealDto> setmealDtoInfo(@PathVariable Long id){
        SetmealDto byIdDish = setmealService.getByIdDish(id);
        return R.success(byIdDish);
    }

    /**
     * 修改套餐
     * @param setmeal
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody SetmealDto setmeal){
        setmealService.updateByIdDish(setmeal);
        return R.success("修改成功");
    }

    /**
     * 删除套餐
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(Long[] ids){
        for (Long i:
             ids) {
            setmealService.deleteByIdDish(i);
        }

        return R.success("删除成功");
    }

    @GetMapping("/list")
    public R<List<Setmeal>> list(Setmeal setmeal){
        LambdaQueryWrapper<Setmeal> lqw = new LambdaQueryWrapper<>();
        lqw.eq(setmeal != null,Setmeal::getCategoryId,setmeal.getCategoryId());
        lqw.eq(setmeal.getStatus() != null,Setmeal::getStatus,1);
        lqw.orderByAsc(Setmeal::getUpdateTime);

        List<Setmeal> list = setmealService.list(lqw);

        return R.success(list);

    }
}
