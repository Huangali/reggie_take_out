package com.hulon.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hulon.reggie.common.BaseContext;
import com.hulon.reggie.config.CustomException;
import com.hulon.reggie.entity.*;
import com.hulon.reggie.mapper.OrdersMapper;
import com.hulon.reggie.service.AddressBookService;
import com.hulon.reggie.service.OrdersDetailService;
import com.hulon.reggie.service.OrdersService;
import com.hulon.reggie.service.ShoppingCartService;
import com.hulon.reggie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author Hulon
 * @date 2023/6/4
 * @className OredersServiceImpl
 */
@Service
public class OredersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressBookService addressBookService;

    @Autowired
    private OrdersDetailService ordersDetailService;


    /**
     * 用户下单
     * @param orders
     */
    @Transactional
    @Override
    public void submit(Orders orders) {
        //获取当前用户id
        Long currentId = BaseContext.getCurrentId();
        //查询当前用户的购物数据
        LambdaQueryWrapper<ShoppingCart> shoppingCartLQW = new LambdaQueryWrapper<>();
        shoppingCartLQW.eq(ShoppingCart::getUserId,currentId);

        List<ShoppingCart> shoppingCarts = shoppingCartService.list(shoppingCartLQW);


        if (shoppingCarts == null || shoppingCarts.size() == 0) {
            throw new CustomException("购物车为空，不能下单");
        }

        //获取用户数据
        User user = userService.getById(currentId);

        //查询地址信息
        Long addressBookId = orders.getAddressBookId();

        AddressBook addressBook = addressBookService.getById(addressBookId);

        if (addressBook == null){
            throw new CustomException("用户地址信息有误，不能下单");
        }

        //向订单表插入数据，一条数据

        //订单号
        long orderId = IdWorker.getId();


        //原子操作，保证线程安全
        AtomicInteger amount = new AtomicInteger(0);
        List<OrderDetail> orderDetails = shoppingCarts.stream().map(iteam ->{
            OrderDetail orderDetail1 = new OrderDetail();
            orderDetail1.setOrderId(orderId);
            orderDetail1.setNumber(iteam.getNumber());
            orderDetail1.setDishFlavor(iteam.getDishFlavor());
            orderDetail1.setDishId(iteam.getDishId());
            orderDetail1.setSetmealId(iteam.getSetmealId());
            orderDetail1.setName(iteam.getName());
            orderDetail1.setImage(iteam.getImage());
            orderDetail1.setAmount(iteam.getAmount());
            amount.addAndGet(iteam.getAmount().multiply(new BigDecimal(iteam.getNumber())).intValue());
            return orderDetail1;
        }).collect(Collectors.toList());


        orders.setId(orderId);
        orders.setOrderTime(LocalDateTime.now());
        orders.setCheckoutTime(LocalDateTime.now());
        orders.setStatus(2);
        orders.setAmount(new BigDecimal(amount.get()));
        orders.setUserId(currentId);
        orders.setNumber(String.valueOf(orderId));
        orders.setUserName(user.getName());
        orders.setConsignee(addressBook.getConsignee());
        orders.setPhone(user.getPhone());
        orders.setAddress(addressBook.getProvinceName() == null ? "" : addressBook.getProvinceName()
                    + (addressBook.getCityName() == null ? "" : addressBook.getCityName())
                    + (addressBook.getDistrictName() == null ? "" : addressBook.getDistrictCode())
                    + (addressBook.getDetail() == null ? "" : addressBook.getDetail())
        );


        this.save(orders);
        //向订单明细表插入数据，多条数据
        ordersDetailService.saveBatch(orderDetails);
        //清空购物车数据
        shoppingCartService.remove(shoppingCartLQW);
    }
}
