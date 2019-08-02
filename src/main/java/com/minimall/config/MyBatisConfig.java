package com.minimall.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: Bran.Zuo
 * @create: 2019-07-21 23:38
 **/

@Configuration
@MapperScan({"com.minimall.mbg.mapper","com.minimall.dao"})
public class MyBatisConfig {
}
