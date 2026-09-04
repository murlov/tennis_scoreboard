package murlov.tennis_scoreboard.dao;

import murlov.tennis_scoreboard.hibernate.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Optional;

public final class PlayerDao {

    private final SessionFactory sessionFactory;

    public PlayerDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Optional<Player> getByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            "FROM Player p WHERE p.name = :name",
                            Player.class
                    )
                    .setParameter("name", name)
                    .uniqueResultOptional();
        }
    }
}
