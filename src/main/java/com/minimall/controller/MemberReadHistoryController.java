package com.minimall.controller;

import com.minimall.common.api.CommonResult;
import com.minimall.nosql.mongdb.document.MemberReadHistory;
import com.minimall.service.impl.MemberReadHistoryServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 会员商品浏览记录管理 Controller
 * @author: Bran.Zuo
 * @create: 2019-08-22 14:42
 **/
@Controller
@Api(tags = "会员浏览商品记录管理")
@RequestMapping("/member/readHistory")
public class MemberReadHistoryController {

    @Autowired
    private MemberReadHistoryServiceImpl service;

    @ApiOperation("创建浏览记录")
    @PostMapping("/create")
    @ResponseBody
    public CommonResult create(@RequestBody MemberReadHistory history) {
        int count = service.create(history);
        if (count > 0) {
            return CommonResult.success(count);
        }else {
            return CommonResult.failed();
        }
    }

    @ApiOperation("展示浏览记录")
    @GetMapping("/list")
    @ResponseBody
    public CommonResult<List<MemberReadHistory>> list(@RequestParam("memberId") Long memberId) {
        return CommonResult.success(service.list(memberId));
    }


    @ApiOperation("删除浏览记录")
    @PostMapping("/delete")
    @ResponseBody
    public CommonResult delete(@RequestParam("ids") List<String> ids) {
        int count = service.delete(ids);
        if (count > 0) {
            return CommonResult.success(count);
        }else {
            return CommonResult.failed();
        }
    }

}
