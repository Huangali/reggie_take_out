package com.hulon.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hulon.reggie.entity.Category;

/**
 * @author Hulon
 * @date 2023/5/21
 * @className CategoryService
 */
public interface CategoryService extends IService<Category> {
    void remove(Long id);
}
