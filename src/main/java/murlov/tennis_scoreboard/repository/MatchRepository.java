package murlov.tennis_scoreboard.repository;

import jakarta.persistence.PersistenceException;
import jakarta.persistence.RollbackException;
import lombok.RequiredArgsConstructor;
import murlov.tennis_scoreboard.exception.DatabaseException;
import murlov.tennis_scoreboard.mapper.MatchMapper;
import murlov.tennis_scoreboard.model.domain.FinishedMatch;
import murlov.tennis_scoreboard.model.entity.MatchEntity;
import murlov.tennis_scoreboard.model.entity.PlayerEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public final class MatchRepository {

    private static final int PAGE_SIZE = 5;

    private final SessionFactory sessionFactory;
    private final MatchMapper matchMapper;

    public void saveFinishedMatch(int firstPlayerId, int secondPlayerId, int winnerId) {

        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            session.persist(new MatchEntity(
                    session.getReference(PlayerEntity.class, firstPlayerId),
                    session.getReference(PlayerEntity.class, secondPlayerId),
                    session.getReference(PlayerEntity.class, winnerId)
            ));

            transaction.commit();
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

    public List<FinishedMatch> getFinishedMatches(Integer page) {
        try (Session session = sessionFactory.openSession()) {
            List<MatchEntity> matchEntities = session.createQuery(
                    "FROM MatchEntity m " +
                            "ORDER BY m.id DESC",
                    MatchEntity.class
                    )
                    .setFirstResult((page - 1) * PAGE_SIZE)
                    .setMaxResults(PAGE_SIZE)
                    .getResultList();

            return matchMapper.toDomain(matchEntities);
        } catch (PersistenceException e) {
            throw new DatabaseException("Database operation failed", e);
        }
    }

    public List<FinishedMatch> getFinishedMatchesByName(Integer page, String playerName) {
        try (Session session = sessionFactory.openSession()) {
            List<MatchEntity> matchEntities = session.createQuery(
                            "FROM MatchEntity m " +
                                    "JOIN FETCH m.firstPlayerEntity " +
                                    "JOIN FETCH m.secondPlayerEntity " +
                                    "JOIN FETCH m.winnerEntity " +
                                    "WHERE m.firstPlayerEntity.name = :playerName " +
                                    "OR m.secondPlayerEntity.name = :playerName",
                            MatchEntity.class
                    )
                    .setParameter("playerName", playerName)
                    .setFirstResult((page - 1) * PAGE_SIZE)
                    .setMaxResults(PAGE_SIZE)
                    .getResultList();

            return matchMapper.toDomain(matchEntities);
        } catch (PersistenceException e) {
            throw new DatabaseException("Database operation failed", e);
        }
    }
}
