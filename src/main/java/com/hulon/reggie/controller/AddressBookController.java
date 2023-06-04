package com.hulon.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.hulon.reggie.common.BaseContext;
import com.hulon.reggie.common.R;
import com.hulon.reggie.entity.AddressBook;
import com.hulon.reggie.service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Hulon
 * @date 2023/5/28
 * @className AddressBookController
 */
@RestController
@RequestMapping("/addressBook")
@Slf4j
public class AddressBookController {
    @Autowired
    private AddressBookService addressBookService;

    /**
     * 新增地址
     * @param addressBook
     * @return
     */
    @PostMapping
    public R<AddressBook> save(@RequestBody AddressBook addressBook){
      log.info(addressBook.toString());
      addressBook.setUserId(BaseContext.getCurrentId());
      addressBookService.save(addressBook);
      return R.success(addressBook);
    }

    /**
     * 设置默认地址
     * @param addressBook
     * @return
     */
    @PutMapping("/default")
    public R<AddressBook> setDefault(@RequestBody AddressBook addressBook){
        LambdaUpdateWrapper<AddressBook> luw = new LambdaUpdateWrapper<>();

        luw.eq(AddressBook::getUserId,BaseContext.getCurrentId());
        luw.set(AddressBook::getIsDefault,0);
        addressBookService.update(luw);

        addressBook.setIsDefault(1);

        addressBookService.updateById(addressBook);

        return R.success(addressBook);

    }

    /**
     * 根据id获取地址
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<AddressBook> getAddressBook(@PathVariable Long id){

        AddressBook byId = addressBookService.getById(id);

        if (byId != null){
            return R.success(byId);
        }else {
            return R.error("没有找到该对象");
        }


    }

    /**
     * 查询默认地址
     * @return
     */
    @GetMapping("/default")
    public R<AddressBook> getDefault(){
        LambdaQueryWrapper<AddressBook> lqw = new LambdaQueryWrapper<>();

        lqw.eq(AddressBook::getUserId,BaseContext.getCurrentId());
        lqw.eq(AddressBook::getIsDefault,1);

        AddressBook one = addressBookService.getOne(lqw);

        if (one == null){
            return R.error("没有找到该对象");
        }else {
            return R.success(one);
        }

    }

    /**
     * 查询用户的全部地址
     * @param addressBook
     * @return
     */
    @GetMapping("/list")
    public R<List<AddressBook>> list(AddressBook addressBook){

        LambdaQueryWrapper<AddressBook> lqw = new LambdaQueryWrapper<>();

        lqw.eq(AddressBook::getUserId,BaseContext.getCurrentId());
        lqw.orderByAsc(AddressBook::getUpdateTime);

        return R.success(addressBookService.list(lqw));



    }

    /**
     * 根据id修改用户id
     * @param addressBook
     * @return
     */
    @PutMapping
    public R<AddressBook> updataAddress(@RequestBody AddressBook addressBook){

        addressBookService.updateById(addressBook);
        return R.success(addressBook);

    }

}
