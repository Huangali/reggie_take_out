package com.hulon.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hulon.reggie.entity.User;
import com.hulon.reggie.mapper.UserMapper;
import com.hulon.reggie.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author Hulon
 * @date 2023/5/27
 * @className UserServiceImpl
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
