package com.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageInterceptor;
import com.intercept.SqlStatementInterceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.yandex.clickhouse.BalancedClickhouseDataSource;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@MapperScan(basePackages ={"com.dao.db2"}, sqlSessionFactoryRef = "db2SqlSessionFactory")
public class Db2DataSourceConfig {
    private static final String TYPE_ALIASES_PACKAGE = "com.dao.db2.*.entity";
    private static final String MAPPER_LOCATION = "classpath:mapper/db2/*/*.xml";


    @Bean(name = "db2DataSource")
    @Qualifier("db2DataSource")
    @ConfigurationProperties(prefix = "spring.datasource.db2")
//    @Primary
    public DataSource db2DataSource() {
//        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    //    BalancedClickhouseDataSource balancedClickhouseDataSource=new BalancedClickhouseDataSource("jdbc:clickhouse://192.168.1.119:8123/lwaddr?useUnicode=true&characterEncoding=utf-8");
     DruidDataSource druidDataSource=new DruidDataSource();

        return druidDataSource;
/*
        return new BalancedClickhouseDataSource("");
*/
    }


    @Bean(name = "db2SqlSessionFactory")
//    @Primary
    public SqlSessionFactory db2SqlSessionFactory(@Qualifier("db2DataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        // 加入sql语句拦截器
        //分页插件
        Properties properties = new Properties();
        properties.setProperty("offsetAsPageNum", "true");
        properties.setProperty("rowBoundsWithCount", "true");
        properties.setProperty("reasonable", "true");
        PageInterceptor interceptor = new SqlStatementInterceptor();
        interceptor.setProperties(properties);
        bean.setPlugins(new PageInterceptor[]{interceptor});
        bean.setTypeAliasesPackage(TYPE_ALIASES_PACKAGE);
        bean.setMapperLocations(
                new PathMatchingResourcePatternResolver()
                        .getResources(MAPPER_LOCATION));
        return bean.getObject();
    }

    @Bean(name = "db2TransactionManager")
//    @Primary
    public DataSourceTransactionManager db2TransactionManager(@Qualifier("db2DataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "db2SqlSessionTemplate")
//    @Primary
    public SqlSessionTemplate db2SqlSessionTemplate(@Qualifier("db2SqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
