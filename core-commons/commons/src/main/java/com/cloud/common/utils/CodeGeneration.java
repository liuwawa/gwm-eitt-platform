package com.cloud.common.utils;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

/**
 * User: WangPan
 * Date: 2019/4/2
 * Time: 19:41
 * 自动生成实体
 */
@PropertySource("classpath:application.properties")
public class CodeGeneration {
    @Value("${spring.datasource.url}")
    private String host;
    /**
     * @param args
     * @Title: main
     * @Description: 生成
     */
    public static void main(String[] args) {
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir("D://CodeGeneration"); //代码生成文件目录
        gc.setFileOverride(true);
        gc.setActiveRecord(false);// 不需要ActiveRecord特性的请改为false
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(false);// XML columList
        gc.setAuthor("liuek");// 作者

        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setControllerName("%sController");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setMapperName("%sDao");
        gc.setXmlName("%sMapper");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setDriverName("com.mysql.jdbc.Driver"); //只有更换数据库时才需要修改
        dsc.setUsername("root");    //数据库账号
        dsc.setPassword("123@456.Com");    // 数据库密码
        dsc.setUrl("jdbc:mysql://10.255.30.142:3306/cloud_user?useSSL=false");  //数据库链接地址
        mpg.setDataSource(dsc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setTablePrefix(new String[]{"sys_"});// 此处可以修改为您的表前缀
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        strategy.setInclude(new String[]{"sys_group_expand"}); // 需要生成的表

        strategy.setSuperServiceClass(null);
        strategy.setSuperServiceImplClass(null);
        strategy.setSuperMapperClass(null);
        strategy.setEntityLombokModel(true);
        strategy.setSuperEntityClass("com.baomidou.mybatisplus.extension.activerecord.Model");

        mpg.setStrategy(strategy);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.cloud.model.group"); //包名生成配置 这里只有指定目录才需要更改
        pc.setController("controller");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setMapper("mapper");
        pc.setEntity("model");
        pc.setXml("mapper");
        mpg.setPackageInfo(pc);

        // 执行生成
        mpg.execute();
    }

}
