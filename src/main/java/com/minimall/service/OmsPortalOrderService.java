package com.minimall.service;

import com.minimall.common.api.CommonResult;
import com.minimall.dto.OrderParam;
import org.springframework.transaction.annotation.Transactional;

/**
 * 前台订单管理Service
 */
public interface OmsPortalOrderService {

    /**
     * 根据提交信息生成订单
     */
    @Transactional(rollbackFor = Exception.class)
    CommonResult generateOrder(OrderParam orderParam);

    /**
     * 取消单个超时订单
     */
    @Transactional(rollbackFor = Exception.class)
    void cancelOrder(Long orderId);
}
