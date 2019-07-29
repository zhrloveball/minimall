package com.minimall.service.impl;

import com.minimall.mbg.model.PmsBrand;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PmsBrandServiceImplTest {

    @Autowired
    private PmsBrandServiceImpl pmsBrandService;

    @Test
    public void listAllBrand() {
        List<PmsBrand> brandList = pmsBrandService.listAllBrand();
        System.out.println(brandList);
        Assert.assertNotNull(brandList);
    }

    @Test
    public void createBrand() {
        PmsBrand pmsBrand = new PmsBrand();
        pmsBrand.setName("Test");
        pmsBrand.setFirstLetter("T");
        int brand = pmsBrandService.createBrand(pmsBrand);
        Assert.assertEquals(1, brand);
    }

    @Test
    public void updateBrand() {
        PmsBrand pmsBrand = new PmsBrand();
        pmsBrand.setName("Test_Update");
        pmsBrand.setFirstLetter("T");
        int brand = pmsBrandService.updateBrand(59L, pmsBrand);
        Assert.assertEquals(1, brand);
    }

    @Test
    public void deleteBrand() {
        Assert.assertEquals(1, pmsBrandService.deleteBrand(59L));
    }

    @Test
    public void listBrand() {
        List<PmsBrand> brandList = pmsBrandService.listBrand(2, 2);
        System.out.println(brandList);
        Assert.assertNotNull(brandList);
    }

    @Test
    public void getBrand() {
        Assert.assertNotNull(pmsBrandService.getBrand(1L));
    }
}