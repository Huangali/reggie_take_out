package com.hulon.reggie.config;

/**
 * 自定义业务异常
 * @author Hulon
 * @date 2023/5/21
 * @className CustomException
 */
public class CustomException extends RuntimeException{
    public CustomException(String message){
        super(message);
    }


}
