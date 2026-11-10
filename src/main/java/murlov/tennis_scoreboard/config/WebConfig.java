package murlov.tennis_scoreboard.config;

import org.flywaydb.core.Flyway;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan("murlov.tennis_scoreboard")
public class WebConfig {

    @Bean
    public Flyway flyway() {
        Flyway flyway = Flyway.configure()
                .dataSource(
                        System.getenv("DB_URL"),
                        System.getenv("DB_USERNAME"),
                        System.getenv("DB_PASSWORD")
                )
                .load();

        flyway.migrate();

        return flyway;
    }

    @Bean
    @DependsOn("flyway")
    public SessionFactory sessionFactory() {
        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration()
                .configure()
                .setProperty(
                        "hibernate.connection.url",
                        System.getenv("DB_URL")
                )
                .setProperty(
                        "hibernate.connection.username",
                        System.getenv("DB_USERNAME")
                )
                .setProperty(
                        "hibernate.connection.password",
                        System.getenv("DB_PASSWORD")
                );
        return configuration.buildSessionFactory();
    }
}
