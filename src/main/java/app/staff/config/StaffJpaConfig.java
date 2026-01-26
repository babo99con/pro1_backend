package app.staff.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        basePackages = "app.staff.repository.jpa",
        entityManagerFactoryRef = "staffEntityManagerFactory",
        transactionManagerRef = "staffTransactionManager"
)
public class StaffJpaConfig {

    @Autowired
    private DataSource dataSource;

    @Bean(name = "staffEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean staffEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(dataSource)
                .packages("app.staff.domain") // 엔티티 패키지
                .persistenceUnit("staffPU")
                .build();
    }

    @Bean(name = "staffTransactionManager")
    public PlatformTransactionManager staffTransactionManager(
            @Qualifier("staffEntityManagerFactory") LocalContainerEntityManagerFactoryBean staffEmf) {
        return new JpaTransactionManager(staffEmf.getObject());
    }
}