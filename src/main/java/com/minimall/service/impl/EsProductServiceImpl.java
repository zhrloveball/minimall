package com.minimall.service.impl;

import com.minimall.dao.EsProductDao;
import com.minimall.nosql.elasticsearch.document.EsProduct;
import com.minimall.nosql.elasticsearch.repository.EsProductRepository;
import com.minimall.service.EsProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

/**
 * @description: 商品搜索管理Service实现类
 * @author: Bran.Zuo
 * @create: 2019-08-13 14:08
 **/
@Service
public class EsProductServiceImpl implements EsProductService {

    @Autowired
    private EsProductRepository productRepository;
    @Autowired
    private EsProductDao productDao;

    @Override
    public int importAll() {
        List<EsProduct> productList = productDao.getAllEsProductList(null);
        Iterable<EsProduct> esProductIterable = productRepository.saveAll(productList);
        Iterator<EsProduct> iterator = esProductIterable.iterator();
        int result = 0;
        while (iterator.hasNext()) {
            result++;
            iterator.next();
        }
        return result;
    }

    @Override
    public Page<EsProduct> search(String keyword, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return productRepository.findByNameOrSubTitleOrKeywords(keyword, keyword, keyword, pageable);
    }
}
