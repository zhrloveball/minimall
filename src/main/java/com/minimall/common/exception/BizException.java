package com.minimall.common.exception;

/**
 * @description: 自定义业务异常
 * @author: Bran.Zuo
 * @create: 2019-08-30 17:00
 **/
public class BizException extends RuntimeException{

    public BizException(String message) {
        super(message);
    }
}
