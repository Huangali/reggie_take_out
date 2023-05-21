package com.hulon.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hulon.reggie.entity.Setmeal;
import com.hulon.reggie.mapper.SetmealMapper;
import com.hulon.reggie.service.SetmealService;
import org.springframework.stereotype.Service;

/**
 * @author Hulon
 * @date 2023/5/21
 * @className SetmealServiceImpl
 */
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
}
