package com.minimall.controller;

import com.alibaba.fastjson.JSON;
import com.minimall.common.api.CommonPage;
import com.minimall.common.api.CommonResult;
import com.minimall.mbg.model.PmsBrand;
import com.minimall.service.impl.PmsBrandServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @description: 品牌管理
 * @author: Bran.Zuo
 * @create: 2019-07-29 14:29
 **/

@Slf4j
@RestController
@RequestMapping("/brand/")
public class PmsBrandController {

    @Autowired
    private PmsBrandServiceImpl pmsBrandService;

    @PostMapping("create")
    @ResponseBody
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

    @PutMapping("update/{id}")
    @ResponseBody
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

    @DeleteMapping("delete/{id}")
    @ResponseBody
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

    @GetMapping("{id}")
    @ResponseBody
    public CommonResult<PmsBrand> getBrandById(@PathVariable("id") Long id) {
        return CommonResult.success(pmsBrandService.getBrand(id));
    }

    @GetMapping("list")
    @ResponseBody
    public CommonResult<CommonPage<PmsBrand>> listBrand(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                        @RequestParam(value = "pageSize", defaultValue = "3") Integer pageSize) {
        List<PmsBrand> brandList = pmsBrandService.listBrand(pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(brandList));
    }

    @GetMapping("listAll")
    @ResponseBody
    public CommonResult<CommonPage<PmsBrand>> listAllBrand() {
        return CommonResult.success(CommonPage.restPage(pmsBrandService.listAllBrand()));
    }
}
