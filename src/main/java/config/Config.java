package config;


import org.springframework.context.annotation.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = {"dao", "service", "controller", "validation"})
@PropertySource("classpath:application.properties")
//@EnableAspectJAutoProxy
@EnableWebMvc
public class Config {


    @Bean
    public JdbcClient jdbcClient(DataSource dataSource) {

        var populate = new ResourceDatabasePopulator(
                new ClassPathResource("schema.sql"),
                new ClassPathResource("data.sql")
        );

        DatabasePopulatorUtils.execute(populate, dataSource);

        return JdbcClient.create(dataSource);
    }
}
