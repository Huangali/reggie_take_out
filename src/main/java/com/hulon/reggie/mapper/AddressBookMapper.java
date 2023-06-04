package com.hulon.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hulon.reggie.entity.AddressBook;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Hulon
 * @date 2023/5/27
 * @className AddressBookMapper
 */
@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {
}
