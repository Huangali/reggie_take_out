package com.hulon.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hulon.reggie.entity.OrderDetail;
import com.hulon.reggie.mapper.OrdersDetailMapper;
import org.springframework.stereotype.Service;

/**
 * @author Hulon
 * @date 2023/6/4
 * @className OrdersDetailService
 */
@Service
public class OrdersDetailService extends ServiceImpl<OrdersDetailMapper, OrderDetail> implements com.hulon.reggie.service.OrdersDetailService {
}
