package com.minimall.service.impl;

import com.github.pagehelper.PageHelper;
import com.minimall.mbg.mapper.PmsBrandMapper;
import com.minimall.mbg.model.PmsBrand;
import com.minimall.mbg.model.PmsBrandExample;
import com.minimall.service.PmsBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: 品牌管理Service
 * @author: Bran.Zuo
 * @create: 2019-07-29 14:17
 **/
@Service
public class PmsBrandServiceImpl implements PmsBrandService {

    @Autowired
    private PmsBrandMapper brandMapper;

    @Override
    public List<PmsBrand> listAllBrand() {
        return brandMapper.selectByExample(new PmsBrandExample());
    }

    @Override
    public int createBrand(PmsBrand brand) {
        return brandMapper.insertSelective(brand);
    }

    @Override
    public int updateBrand(Long id, PmsBrand brand) {
        brand.setId(id);
        return brandMapper.updateByPrimaryKeySelective(brand);
    }

    @Override
    public int deleteBrand(Long id) {
        return brandMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<PmsBrand> listBrand(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return brandMapper.selectByExample(new PmsBrandExample());
    }

    @Override
    public PmsBrand getBrand(Long id) {
        return brandMapper.selectByPrimaryKey(id);
    }
}
