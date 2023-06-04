package com.hulon.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hulon.reggie.entity.Orders;

/**
 * @author Hulon
 * @date 2023/6/4
 * @className OrdersService
 */
public interface OrdersService extends IService<Orders> {
    /**
     * 用户下单
     * @param orders
     */
    void submit(Orders orders);
}
