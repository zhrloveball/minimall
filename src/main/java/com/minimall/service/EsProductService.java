package com.minimall.service;

import com.minimall.nosql.elasticsearch.document.EsProduct;
import org.springframework.data.domain.Page;

/**
 * 产品搜索管理Service
 */
public interface EsProductService {

    /**
     * 从数据库中导入所有商品到ES
     */
    int importAll();

    /**
     * 根据关键字搜索名称或者副标题
     */
    Page<EsProduct> search(String keyword, Integer pageNum, Integer pageSize);

}