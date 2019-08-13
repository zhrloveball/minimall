package com.minimall.controller;

import com.minimall.common.api.CommonPage;
import com.minimall.common.api.CommonResult;
import com.minimall.nosql.elasticsearch.document.EsProduct;
import com.minimall.service.impl.EsProductServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @description: 搜索商品管理Controller
 * @author: Bran.Zuo
 * @create: 2019-08-13 14:24
 **/
@Api(tags = "搜索商品信息")
@RestController
@RequestMapping("/esProduct/")
public class EsProductController {

    @Autowired
    private EsProductServiceImpl esProductService;

    @ApiOperation("导入所有数据库中商品信息到ES")
    @PostMapping("importAll")
    @ResponseBody
    public CommonResult<Integer> importAll() {
        return CommonResult.success(esProductService.importAll());
    }

    @ApiOperation("简单搜索")
    @GetMapping("search/simple")
    @ResponseBody
    public CommonResult<CommonPage<EsProduct>> search(@RequestParam(required = false) String keyword,
                                                      @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                                      @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        Page<EsProduct> esProductPage = esProductService.search(keyword, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(esProductPage));
    }
}

