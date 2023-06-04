package com.hulon.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hulon.reggie.entity.AddressBook;
import com.hulon.reggie.mapper.AddressBookMapper;
import com.hulon.reggie.service.AddressBookService;
import org.springframework.stereotype.Service;

/**
 * @author Hulon
 * @date 2023/5/27
 * @className AddressBookServiceImpl
 */
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
}
