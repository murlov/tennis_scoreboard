package murlov.tennis_scoreboard.repository;

import jakarta.persistence.PersistenceException;
import jakarta.persistence.RollbackException;
import lombok.RequiredArgsConstructor;
import murlov.tennis_scoreboard.exception.DatabaseException;
import murlov.tennis_scoreboard.exception.DuplicateException;
import murlov.tennis_scoreboard.mapper.PlayerMapper;
import murlov.tennis_scoreboard.model.domain.Player;
import murlov.tennis_scoreboard.model.entity.PlayerEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public final class PlayerRepository {

    private final SessionFactory sessionFactory;
    private final PlayerMapper playerMapper;

    public Optional<Player> getByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            Optional<PlayerEntity> playerEntity = session.createQuery(
                            "FROM PlayerEntity p WHERE p.name = :name",
                            PlayerEntity.class
                    )
                    .setParameter("name", name)
                    .uniqueResultOptional();

            return playerEntity.map(playerMapper::toDomain);
        } catch (PersistenceException e) {
            throw new DatabaseException("Database operation failed", e);
        }
    }

    public Player save(String name) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            PlayerEntity playerEntity = new PlayerEntity(name);
            session.persist(playerEntity);

            transaction.commit();

            return playerMapper.toDomain(playerEntity);
        } catch (ConstraintViolationException e) {
            throw new DuplicateException(
                    "Player with name '" + name + "' already exists"
            );
        } catch (PersistenceException e) {
            if (transaction != null && transaction.isActive()) {
                try {
                    transaction.rollback();
                } catch (RollbackException rollbackException) {
                    e.addSuppressed(rollbackException);
                }
            }

            throw new DatabaseException("Database operation failed", e);
        }
    }
}
