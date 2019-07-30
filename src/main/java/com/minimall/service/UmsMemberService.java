package com.minimall.service;

import com.minimall.common.api.CommonResult;

/**
 * @description: 会员管理
 * @author: Bran.Zuo
 * @create: 2019-07-30 16:14
 **/
public interface UmsMemberService {

    /**
     * 生成验证码
     * @return
     */
    CommonResult generateAuthCode(String telephone);

    /**
     * 验证验证码
     * @return
     */
    CommonResult verifyAuthCode(String telephone, String authCode);
}
