package com.minimall.service;

/**
 * 常用Redis操作
 */
public interface RedisService {

    /**
     * 存储数据
     */
    void set(String key, String value);

    /**
     * 查询数据
     */
    String get(String key);

    /**
     * 设置超时时间
     */
    Boolean expire(String key, long expire);

    /**
     * 删除数据
     */
    void remove(String key);

    /**
     * 自增操作
     * @param delta 自增步长
     */
    Long increment(String key, long delta);

}
