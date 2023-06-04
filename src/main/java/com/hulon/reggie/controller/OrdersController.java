package com.hulon.reggie.controller;

import com.hulon.reggie.common.R;
import com.hulon.reggie.entity.Orders;
import com.hulon.reggie.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Hulon
 * @date 2023/6/4
 * @className OrdersController
 */
@RestController
@RequestMapping("/order")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;

    @RequestMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){
        ordersService.submit(orders);
        return R.success("支付成功");
    }


}
