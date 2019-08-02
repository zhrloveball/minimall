package com.minimall.controller;

import com.alibaba.fastjson.JSON;
import com.minimall.common.api.CommonPage;
import com.minimall.common.api.CommonResult;
import com.minimall.mbg.model.PmsBrand;
import com.minimall.service.impl.PmsBrandServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 品牌管理
 * @author: Bran.Zuo
 * @create: 2019-07-29 14:29
 **/

@Api(tags = "商城品牌管理")
@Slf4j
@RestController
@RequestMapping("/brand/")
public class PmsBrandController {

    @Autowired
    private PmsBrandServiceImpl pmsBrandService;

    @ApiOperation("添加品牌")
    @PostMapping("create")
    @ResponseBody
    @PreAuthorize("hasAuthority('pms:brand:create')")
    public CommonResult createBrand(@RequestBody PmsBrand pmsBrand) {
        CommonResult commonResult ;

        int count = pmsBrandService.createBrand(pmsBrand);
        if (count == 1) {
            commonResult = CommonResult.success(pmsBrand);
            log.debug("createBrand success:{}", JSON.toJSONString(pmsBrand));
        }else {
            commonResult = CommonResult.failed();
            log.error("createBrand failed:{}", JSON.toJSONString(pmsBrand));
        }
        return commonResult;
    }

    @ApiOperation("更新指定id品牌信息")
    @PutMapping("update/{id}")
    @ResponseBody
    @PreAuthorize("hasAuthority('pms:brand:update')")
    public CommonResult updateBrand(@PathVariable("id") Long id, @RequestBody PmsBrand pmsBrand) {
        CommonResult commonResult;
        int count = pmsBrandService.updateBrand(id, pmsBrand);
        if (count == 1) {
            commonResult = CommonResult.success(pmsBrand);
            log.debug("updateBrand success:{}", JSON.toJSONString(pmsBrand));
        }else {
            commonResult = CommonResult.failed();
            log.error("updateBrand failed:{}", JSON.toJSONString(pmsBrand));
        }
        return commonResult;
    }

    @ApiOperation("删除指定id的品牌")
    @DeleteMapping("delete/{id}")
    @ResponseBody
    @PreAuthorize("hasAuthority('pms:brand:delete')")
    public CommonResult delete(@PathVariable("id") Long id) {
        int count = pmsBrandService.deleteBrand(id);
        if (count == 1) {
            log.debug("deleteBrand success, id:{}", id);
            return CommonResult.success();
        }else {
            log.error("deleteBrand failed, id:{}", id);
            return CommonResult.failed();
        }
    }

    @ApiOperation("获取指定id的品牌详情")
    @GetMapping("{id}")
    @ResponseBody
    @PreAuthorize("hasAuthority('pms:brand:read')")
    public CommonResult<PmsBrand> getBrandById(@PathVariable("id") Long id) {
        return CommonResult.success(pmsBrandService.getBrand(id));
    }

    @ApiOperation("分页查询品牌列表")
    @GetMapping("list")
    @ResponseBody
    @PreAuthorize("hasAuthority('pms:brand:read')")
    public CommonResult<CommonPage<PmsBrand>> listBrand(@RequestParam(value = "pageNum", defaultValue = "1") @ApiParam("页码") Integer pageNum,
                                                        @RequestParam(value = "pageSize", defaultValue = "3") @ApiParam("每页个数") Integer pageSize) {
        List<PmsBrand> brandList = pmsBrandService.listBrand(pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(brandList));
    }

    @ApiOperation("获取所有品牌列表")
    @GetMapping("listAll")
    @ResponseBody
    @PreAuthorize("hasAuthority('pms:brand:read')")
    public CommonResult<CommonPage<PmsBrand>> listAllBrand() {
        return CommonResult.success(CommonPage.restPage(pmsBrandService.listAllBrand()));
    }
}
