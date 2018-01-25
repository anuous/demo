package com.example.demo.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class DataSourceConfiguration2 {

    @Value("${slave.datasource.url}")
    private String url;
    @Value("${slave.datasource.password}" )
    private String password;
    @Value("${slave.datasource.username}")
    private String username;

    @Bean
    public ServletRegistrationBean druidServlet() {
        return new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
    }

    @Bean("slaveDataSource")
    public DataSource slaveDataSource() {
        DruidDataSource slaveDataSource=new DruidDataSource();
        slaveDataSource.setUrl(url);
        slaveDataSource.setPassword(password);
        slaveDataSource.setUsername(username);

        // <!-- 配置初始化大小、最小、最大 -->
        slaveDataSource.setInitialSize(5);
        slaveDataSource.setMinIdle(5);
        slaveDataSource.setMaxActive(50);
        slaveDataSource.setMaxWait(60000L);
        slaveDataSource.setTimeBetweenEvictionRunsMillis(60000L);

        slaveDataSource.setMinEvictableIdleTimeMillis(300000L);
        slaveDataSource.setValidationQuery("select 'ok' from dual");
        slaveDataSource.setTestOnBorrow(true);
        slaveDataSource.setTestOnReturn(true);
        slaveDataSource.setTestWhileIdle(true);
        try {
            slaveDataSource.setFilters("stat");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return slaveDataSource;
    }
}
