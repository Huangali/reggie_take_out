package com.hulon.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hulon.reggie.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Hulon
 * @date 2023/5/27
 * @className UserMapper
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
