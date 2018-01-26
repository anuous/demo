package com.example.demo.config;

import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
//@MapperScan(value = "com.example.demo.daoImpl")
@MapperScan(value = "com.example.demo.daoImpl",sqlSessionFactoryRef = "sqlSessionFactory")
public class MybatisConfiguration2 {

//    @Autowired
//    private DataSource slaveDataSource;
    @Bean("sqlSessionFactory")
    public SqlSessionFactoryBean sqlSlaveSessionFactoryBean(@Autowired DataSource slaveDataSource) throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(slaveDataSource);
       //xml配置的sql配置文件 引入  等价@Mapper 注解
//        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver()
//                .getResources("classpath*:sqlmap/**/*.xml"));
       //mybatis 其他属性配置
//        sqlSessionFactoryBean
//                .setConfigLocation(new PathMatchingResourcePatternResolver().getResource("classpath:SqlMapConfig.xml"));
        /*PageInterceptor pageInterceptor = new PageInterceptor();
        Properties prop = new Properties();
        prop.put("databaseType", "mysql");
        //prop.setProperty("params","pageNum=pageNumKey;pageSize=pageSizeKey;");
        pageInterceptor.setProperties(prop);
        sqlSessionFactoryBean.setPlugins(new Interceptor[] {
                pageInterceptor
        });*/
        return sqlSessionFactoryBean;
    }

   /* @Bean("mapperScan2")
    public MapperScannerConfigurer mapperScannerConfigurer(@Autowired SqlSessionFactory sqlSessionFactory) {
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        //dao层引入 扫描 等价 @MapperScan(value = "com.example.demo.daoImpl")
        configurer.setBasePackage("com.example.demo.daoImpl");
        //configurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        configurer.setSqlSessionFactory(sqlSessionFactory);
        return configurer;
    }*/

    @Bean("transactionManager")
    public PlatformTransactionManager loadTransactionSupportManager(@Autowired DataSource slaveDataSource,@Autowired DataSource masterDataSource) {
        DataSourceTransactionManager dtm1 = new DataSourceTransactionManager(slaveDataSource);
        DataSourceTransactionManager dtm2 = new DataSourceTransactionManager(masterDataSource);
        ChainedTransactionManager ctm = new ChainedTransactionManager(dtm1, dtm2);
        return ctm;
    }

}
