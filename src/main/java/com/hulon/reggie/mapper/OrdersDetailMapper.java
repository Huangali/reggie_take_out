package com.hulon.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hulon.reggie.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Hulon
 * @date 2023/6/4
 * @className OrdersDetailMapper
 */
@Mapper
public interface OrdersDetailMapper extends BaseMapper<OrderDetail> {
}
