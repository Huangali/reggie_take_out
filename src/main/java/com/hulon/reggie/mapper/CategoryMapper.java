package com.hulon.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hulon.reggie.entity.Category;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Hulon
 * @date 2023/5/21
 * @className CategoryMapper
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
