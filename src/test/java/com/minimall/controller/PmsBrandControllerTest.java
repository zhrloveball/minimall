package com.minimall.controller;

import com.alibaba.fastjson.JSON;
import com.minimall.mbg.model.PmsBrand;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PmsBrandControllerTest {

    @Autowired
    private PmsBrandController pmsBrandController;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(pmsBrandController).build();
    }

    @Test
    public void createBrand() throws Exception{

        PmsBrand pmsBrand = new PmsBrand();
        pmsBrand.setName("Mock_Brand");

        mockMvc.perform(MockMvcRequestBuilders.post("/brand/create")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(JSON.toJSONString(pmsBrand).getBytes())
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name").value("Mock_Brand"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateBrand() {
    }

    @Test
    public void delete() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/brand/delete/59")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getBrandById() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/brand/1").accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }

    @Test
    public void listBrand() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/brand/list")
                        .param("pageNum", "2")
                        .param("pageSize","2")
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void listAllBrand() {
    }
}