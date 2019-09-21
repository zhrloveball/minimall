package com.minimall.component;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description:
 * @author: Bran.Zuo
 * @create: 2019-09-21 10:02
 **/
@Component
public class SampleTask {

    /**
     * 每隔60s执行一次
     */
    @Scheduled(fixedDelay = 60 * 1000)
    @Transactional(rollbackFor = Exception.class)
    public void task() {

    }
}
