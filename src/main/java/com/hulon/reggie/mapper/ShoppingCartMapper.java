package com.hulon.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hulon.reggie.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Hulon
 * @date 2023/5/28
 * @className ShoppingCartMapper
 */
@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {
}
