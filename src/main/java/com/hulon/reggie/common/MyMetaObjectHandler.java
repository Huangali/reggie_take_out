package com.hulon.reggie.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 自定义元数据对象处理器
 * @author Hulon
 * @date 2023/5/21
 * @className MyMetaObjectHandler
 */
@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {
    /**
     * 进行数据库插入时，公共对象的操作
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
      metaObject.setValue("createTime", LocalDateTime.now());
      metaObject.setValue("updateTime",LocalDateTime.now());
      metaObject.setValue("createUser", BaseContext.getCurrentId());
      metaObject.setValue("updateUser", BaseContext.getCurrentId());
    }

    /**
     * 进行数据库更新时，公共对象的操作
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        metaObject.setValue("updateTime",LocalDateTime.now());
        metaObject.setValue("updateUser", BaseContext.getCurrentId());
    }
}
