package es.udc.fireproject.backend.config.postgresql;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@Profile("postgresql")
public class PostgreSQLConfiguration {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource driver = new DriverManagerDataSource();
        driver.setDriverClassName("org.postgresql.Driver");
        driver.setUrl("jdbc:postgresql:postgis_database");
        driver.setUsername("fireuser");
        driver.setPassword("fireuser");
        return driver;
    }
}
