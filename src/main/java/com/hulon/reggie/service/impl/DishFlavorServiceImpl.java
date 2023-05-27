package com.hulon.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hulon.reggie.entity.DishFlavor;
import com.hulon.reggie.mapper.DishFlavorMapper;
import com.hulon.reggie.service.DishFlavorService;
import org.springframework.stereotype.Service;

/**
 * @author Hulon
 * @date 2023/5/24
 * @className DishFlavorServiceImpl
 */
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
