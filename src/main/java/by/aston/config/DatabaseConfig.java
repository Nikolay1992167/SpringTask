package by.aston.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan("by.aston")
@RequiredArgsConstructor
@EnableTransactionManagement
public class DatabaseConfig {

    @Value("${spring.datasource.driver}")
    private String driver;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.poolSize}")
    private int poolSize;

    /**
     * Creates a new instance of the {@link HikariDataSource} class, which provides a connection pool for the database.
     * The method retrieves the database connection properties from the .yml file using the
     * {@Value}. Then creates a new {@link HikariConfig} class and sets these properties
     * on it before passing it to the constructor of a new {@link HikariDataSource} object.
     *
     * @return the new {@link HikariDataSource} object is returned to be used as a data source for the Hibernate ORM.
     */
    @Bean
    public HikariDataSource hikariDataSource() {

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(driver);
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        hikariConfig.setMaximumPoolSize(poolSize);
        return new HikariDataSource(hikariConfig);
    }
}
