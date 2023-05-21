package com.hulon.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hulon.reggie.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Hulon
 * @date 2023/5/21
 * @className DishMapper
 */
@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
