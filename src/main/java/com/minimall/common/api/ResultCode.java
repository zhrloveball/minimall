package com.minimall.common.api;

/**
 * @description: 常用状态码枚举
 * @author: Bran.Zuo
 * @create: 2019-07-29 13:46
 **/
public enum ResultCode implements IErrorCode{

    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),
    VALIDATE_FAILED(404, "参数校验失败"),
    UNAUTHORIZED(401, "暂未登录或token已过期"),
    FORBIDDEN(403, "没有相关权限")
    ;


    private long code;
    private String message;

    ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public long getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
