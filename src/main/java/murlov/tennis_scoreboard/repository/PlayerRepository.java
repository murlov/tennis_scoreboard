package murlov.tennis_scoreboard.repository;

import murlov.tennis_scoreboard.exception.DuplicateException;
import murlov.tennis_scoreboard.model.entity.PlayerEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public final class PlayerRepository {

    private final SessionFactory sessionFactory;

    public PlayerRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Optional<PlayerEntity> getByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            "FROM PlayerEntity p WHERE p.name = :name",
                            PlayerEntity.class
                    )
                    .setParameter("name", name)
                    .uniqueResultOptional();
        }
    }

    public PlayerEntity save(String name) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            PlayerEntity playerEntity = new PlayerEntity(name);
            session.persist(playerEntity);

            transaction.commit();

            return playerEntity;
        } catch (ConstraintViolationException e) {
            throw new DuplicateException(
                    "Player with name '" + name + "' already exists"
            );
        }
    }
}
