package com.minimall.controller;

import com.minimall.common.api.CommonResult;
import com.minimall.service.impl.UmsMemberServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description: 会员登录注册管理
 * @author: Bran.Zuo
 * @create: 2019-07-30 16:20
 **/

@Api(tags = "会员注册登录管理")
@RequestMapping("/sso/")
@RestController
public class UmsMemberController {

    @Autowired
    private UmsMemberServiceImpl umsMemberService;


    @ApiOperation("获取验证码")
    @GetMapping("getAuthCode")
    @ResponseBody
    public CommonResult getAuthCode(@RequestParam String telephone) {
        return umsMemberService.generateAuthCode(telephone);
    }

    @ApiOperation("校验验证码")
    @PostMapping("verifyAuthCode")
    @ResponseBody
    public CommonResult verifyAuthCode(@RequestParam String telephone, @RequestParam String authCode) {
        return umsMemberService.verifyAuthCode(telephone, authCode);
    }
}
