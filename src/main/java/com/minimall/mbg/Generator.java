package com.minimall.mbg;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: MBG生成mapper，xml入口
 * @author: Bran.Zuo
 * @create: 2019-07-22 23:11
 **/
public class Generator {

    //当生成的代码重复时，覆盖原代码
    public static final Boolean overwrite = true;

    public static void main(String[] args) throws Exception{

        //1. 读取配置文件
        InputStream is = Generator.class.getResourceAsStream("/generatorConfig.xml");

        //2. 创建ConfigurationParser，生成Configuration
        List<String> warnings = new ArrayList<>();
        ConfigurationParser parser = new ConfigurationParser(warnings);
        Configuration config = parser.parseConfiguration(is);

        //3. 创建MyBatisGenerator
        MyBatisGenerator mbg = new MyBatisGenerator(config, new DefaultShellCallback(overwrite), warnings);
        mbg.generate(null);

        //4. 输出日志
        for (String warning : warnings) {
            System.out.println(warning);
        }
    }
}
