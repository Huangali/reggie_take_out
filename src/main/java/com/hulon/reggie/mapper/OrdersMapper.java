package com.hulon.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hulon.reggie.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Hulon
 * @date 2023/6/4
 * @className OrdersMapper
 */
@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {
}
