package com.example.demo.config;

import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
@MapperScan(value = "com.example.demo.mapper",sqlSessionFactoryRef = "sqlMasterSessionFactory")
public class MybatisConfiguration {

    @Bean("sqlMasterSessionFactory")
    public SqlSessionFactoryBean sqlMasterSessionFactoryBean(@Autowired DataSource masterDataSource) throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(masterDataSource);
        //xml配置的sql配置文件 引入  等价@Mapper 注解
//        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
//                .getResources("classpath*:sqlmap/**/*.xml"));
        //mybatis 其他属性配置
//        sqlSessionFactoryBean
//                .setConfigLocation(new PathMatchingResourcePatternResolver().getResource("classpath:SqlMapConfig.xml"));
        /*PageInterceptor pageInterceptor = new PageInterceptor();
        Properties prop = new Properties();
        prop.put("databaseType", "mysql");
        pageInterceptor.setProperties(prop);
        sqlSessionFactoryBean.setPlugins(new Interceptor[] {
                pageInterceptor
        });*/
        return sqlSessionFactoryBean;
    }

    /*@Bean("mapperScan")
    public MapperScannerConfigurer mapperScannerConfigurer(@Autowired SqlSessionFactory sqlMasterSessionFactory ) {
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        //dao层引入 扫描 等价 @MapperScan(value = "com.example.demo.daoImpl")
        configurer.setBasePackage("com.example.demo.mapper");
        configurer.setSqlSessionFactoryBeanName("sqlMasterSessionFactory");
        configurer.setSqlSessionFactory(sqlMasterSessionFactory);
        return configurer;
    }*/

    /*@Bean("transactionManager1")
    public DataSourceTransactionManager loadTransactionSupportManager(@Autowired DataSource masterDataSource) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(masterDataSource);

        return transactionManager;
    }*/
}
