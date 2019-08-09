package com.minimall.controller;

import com.minimall.dto.OrderParam;
import com.minimall.service.impl.OmsPortalOrderServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 订单管理 Controller
 * @author: Bran.Zuo
 * @create: 2019-08-08 16:04
 **/
@Api(tags = "订单管理")
@RestController
@RequestMapping("/order/")
public class OmsPortalOrderController {

    @Autowired
    private OmsPortalOrderServiceImpl portalOrderService;

    @ApiOperation("根据购物车信息生成订单")
    @PostMapping("generateOrder")
    public Object generateOrder(@RequestBody OrderParam orderParam) {
        return portalOrderService.generateOrder(orderParam);
    }
}
