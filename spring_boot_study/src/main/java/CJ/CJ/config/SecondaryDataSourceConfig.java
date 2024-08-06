package CJ.CJ.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SecondaryDataSourceConfig {
    @Bean(name = "secondaryDataSource")
    @ConfigurationProperties(prefix = "boost.beast.datasource")
    public DataSource secondaryDataSource() {
        return DataSourceBuilder.create().build();
    }
}
