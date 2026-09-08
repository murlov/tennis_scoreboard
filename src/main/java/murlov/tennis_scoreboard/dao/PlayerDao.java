package murlov.tennis_scoreboard.dao;

import murlov.tennis_scoreboard.exception.DuplicateException;
import murlov.tennis_scoreboard.model.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

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

    public Player save(String name) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            Player player = new Player(name);
            session.persist(player);

            transaction.commit();

            return player;
        } catch (ConstraintViolationException e) {
            throw new DuplicateException(
                    "Player with name '" + name + "' already exists"
            );
        }
    }
}
