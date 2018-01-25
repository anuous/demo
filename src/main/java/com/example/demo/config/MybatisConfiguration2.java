package com.example.demo.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.github.pagehelper.PageInterceptor;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionDefinition;

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
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties prop = new Properties();
        prop.put("databaseType", "mysql");
        pageInterceptor.setProperties(prop);
        sqlSessionFactoryBean.setPlugins(new Interceptor[] {
                pageInterceptor
        });
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
    public DataSourceTransactionManager loadTransactionSupportManager(@Autowired DataSource slaveDataSource) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(slaveDataSource);

        return transactionManager;
    }

}
