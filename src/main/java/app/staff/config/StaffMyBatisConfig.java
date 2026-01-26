package app.staff.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
@MapperScan(basePackages = "app.staff.mapper", sqlSessionFactoryRef = "staffSqlSessionFactory")
public class StaffMyBatisConfig {

    @Autowired
    private DataSource dataSource;

    @Bean(name = "staffSqlSessionFactory")
    public SqlSessionFactory staffSqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/staff/sql-*.xml")
        );
        factoryBean.setTypeAliasesPackage("app.staff.domain");
        return factoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate staffSqlSessionTemplate(
            @Qualifier("staffSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}