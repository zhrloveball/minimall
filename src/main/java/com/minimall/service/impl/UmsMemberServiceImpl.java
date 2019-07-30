package com.minimall.service.impl;

import com.minimall.common.api.CommonResult;
import com.minimall.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Random;

/**
 * @description: 会员管理
 * @author: Bran.Zuo
 * @create: 2019-07-30 16:16
 **/

@Service
public class UmsMemberServiceImpl implements UmsMemberService {

    @Autowired
    private RedisServiceImpl redisService;

    @Value("${redis.key.prefix.authCode}")
    private String authCodeKeyPrefix;
    @Value("${redis.key.expire.authCode}")
    private Long authCodeKeyExpire;

    @Override
    public CommonResult generateAuthCode(String telephone) {

        //1. 生成随机验证码
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }

        //2. 手机号、验证码存储到Redis
        redisService.set(authCodeKeyPrefix + telephone, sb.toString());
        redisService.expire(authCodeKeyPrefix + telephone, authCodeKeyExpire);
        return CommonResult.success(sb.toString(), "获取验证码成功");
    }

    @Override
    public CommonResult verifyAuthCode(String telephone, String authCode) {

        if (StringUtils.isEmpty(authCode)) {
            return CommonResult.failed("请输入验证码");
        }

        String realAuthCode = redisService.get(authCodeKeyPrefix + telephone);
        if (authCode.equals(realAuthCode)) {
            return CommonResult.success("验证码校验成功");
        } else {
            return CommonResult.failed("验证码不正确");
        }

    }
}
