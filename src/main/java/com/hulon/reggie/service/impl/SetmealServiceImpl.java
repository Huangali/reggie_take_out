package com.hulon.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hulon.reggie.config.CustomException;
import com.hulon.reggie.dto.SetmealDto;
import com.hulon.reggie.entity.Setmeal;
import com.hulon.reggie.entity.SetmealDish;
import com.hulon.reggie.mapper.SetmealMapper;
import com.hulon.reggie.service.SetmealDishService;
import com.hulon.reggie.service.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Hulon
 * @date 2023/5/21
 * @className SetmealServiceImpl
 */
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
    @Autowired
    private SetmealDishService setmealDishService;

    @Transactional
    @Override
    public void saveWithDish(SetmealDto setmealDto) {

        //将套餐添加在setmeal表
        this.save(setmealDto);

        Long id = setmealDto.getId();
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();

        setmealDishes.stream().map(item ->{
            item.setSetmealId(id);
            return item;
        }).collect(Collectors.toList());
        //在setmeal_dish表中添加数据
        setmealDishService.saveBatch(setmealDishes);

    }

    @Transactional
    @Override
    public SetmealDto getByIdDish(Long id) {
        SetmealDto setmealDto = new SetmealDto();

        Setmeal byId = this.getById(id);

        BeanUtils.copyProperties(byId,setmealDto);

        LambdaQueryWrapper<SetmealDish> lqw = new LambdaQueryWrapper<>();
        lqw.eq(byId.getId() != null,SetmealDish::getSetmealId,byId.getId());
        List<SetmealDish> list = setmealDishService.list(lqw);

        setmealDto.setSetmealDishes(list);
        return setmealDto;
    }

    @Transactional
    @Override
    public void updateByIdDish(SetmealDto setmealDto) {
        //更新setmeal
        this.updateById(setmealDto);

        //查询
        LambdaQueryWrapper<SetmealDish> lqw = new LambdaQueryWrapper<>();
        lqw.eq(SetmealDish::getSetmealId,setmealDto.getId());
        //删除查询到的
        setmealDishService.remove(lqw);

        //将修改的重新添加进去
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map(item -> {
            //设置setmeal_dish表的setmealId
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());
        //添加操作
        setmealDishService.saveBatch(setmealDishes);


    }

    @Transactional
    @Override
    public void deleteByIdDish(Long id) {

        //查询套餐状态，是否可以删除
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getId,id)
                .eq(Setmeal::getStatus,1);
        int count = this.count(setmealLambdaQueryWrapper);
        Setmeal byId = this.getById(id);
        if (count > 0){
            throw new CustomException(byId.getName()+"套餐正在售卖，不能删除");
        }

        //先删除setmeal_dish的信息，通过id

        LambdaQueryWrapper<SetmealDish> lqw = new LambdaQueryWrapper<>();
        lqw.eq(id != null,SetmealDish::getSetmealId,id);
        setmealDishService.remove(lqw);

        //在删除setmeal表的数据
        this.removeById(id);
    }
}
