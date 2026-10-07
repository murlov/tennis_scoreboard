package murlov.tennis_scoreboard.config;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan("murlov.tennis_scoreboard")
public class WebConfig {

    @Bean
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
