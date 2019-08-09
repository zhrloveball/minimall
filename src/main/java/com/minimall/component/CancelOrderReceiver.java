package com.minimall.component;

import com.minimall.service.OmsPortalOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 从取消订单的消息队列（mall.order.cancel）里接收消息
 */
@Slf4j
@Component
@RabbitListener(queues = "mall.order.cancel")
public class CancelOrderReceiver {

    @Autowired
    private OmsPortalOrderService portalOrderService;

    @RabbitHandler
    public void handle(Long orderId) {
        log.info("receive delay message orderId:{}", orderId);
        portalOrderService.cancelOrder(orderId);
    }
}