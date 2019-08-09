package com.minimall.service.impl;

import com.minimall.common.api.CommonResult;
import com.minimall.component.CancelOrderSender;
import com.minimall.dto.OrderParam;
import com.minimall.service.OmsPortalOrderService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 前台订单管理
 */
@Service
@Slf4j
public class OmsPortalOrderServiceImpl implements OmsPortalOrderService {
    @Autowired
    private CancelOrderSender cancelOrderSender;

    @Override
    public CommonResult generateOrder(OrderParam orderParam) {
        //TODO 执行一系类下单操作
        log.info("process generateOrder");
        //下单完成后开启一个延迟消息，用于当用户没有付款时取消订单（orderId应该在下单后生成）
        sendDelayMessageCancelOrder(11L);
        return CommonResult.success("下单成功");
    }

    @Override
    public void cancelOrder(Long orderId) {
        //TODO 执行一系类取消订单操作
        log.info("process cancelOrder orderId:{}",orderId);
    }

    private void sendDelayMessageCancelOrder(Long orderId) {
        //获取订单超时时间，测试30s
        long delayTimes = 30 * 1000;
        //发送延迟消息
        cancelOrderSender.sendMessage(orderId, delayTimes);
    }

}