package com.minimall.component;

import com.minimall.common.api.CommonResult;
import com.minimall.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description: 统一异常处理类
 * @author: Bran.Zuo
 * @create: 2019-08-30 17:04
 **/
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = BizException.class)
    @ResponseBody
    public CommonResult bizExceptionHandler(BizException e) {
        log.error(e.getMessage());
        return CommonResult.failed(e.getMessage());
    }
}
