package com.hulon.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hulon.reggie.entity.ShoppingCart;
import com.hulon.reggie.mapper.ShoppingCartMapper;
import com.hulon.reggie.service.ShoppingCartService;
import org.springframework.stereotype.Service;

/**
 * @author Hulon
 * @date 2023/5/28
 * @className ShoppingCartServiceImpl
 */
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
}
