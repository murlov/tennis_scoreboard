package murlov.tennis_scoreboard.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import murlov.tennis_scoreboard.dao.MatchDao;
import murlov.tennis_scoreboard.dao.PlayerDao;
import murlov.tennis_scoreboard.service.MatchService;
import murlov.tennis_scoreboard.storage.UnfinishedMatchesStorage;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@WebListener
public class AppContextListener implements ServletContextListener {

    private SessionFactory sessionFactory;

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        UnfinishedMatchesStorage unfinishedMatchesStorage = new UnfinishedMatchesStorage();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        Configuration configuration = new Configuration()
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

        sessionFactory = configuration
                .buildSessionFactory();

        PlayerDao playerDao = new PlayerDao(sessionFactory);
        MatchDao matchDao = new MatchDao(sessionFactory);

        MatchService matchService = new MatchService(
                playerDao,
                matchDao,
                unfinishedMatchesStorage
        );

        ServletContext context = sce.getServletContext();

        context.setAttribute("objectMapper", objectMapper);

        context.setAttribute("matchService", matchService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        sessionFactory.close();
    }
}
