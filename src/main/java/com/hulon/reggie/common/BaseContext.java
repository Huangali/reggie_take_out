package com.hulon.reggie.common;

/**
 * 基于ThreadLocal封装工具类，用户保存和获取当前登录用户的id
 * @author Hulon
 * @date 2023/5/21
 * @className BaseContext
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static Long getCurrentId() {
        return threadLocal.get();
    }

    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }
}
