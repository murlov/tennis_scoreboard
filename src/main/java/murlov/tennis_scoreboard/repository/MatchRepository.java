package murlov.tennis_scoreboard.repository;

import murlov.tennis_scoreboard.model.entity.MatchEntity;
import murlov.tennis_scoreboard.model.entity.PlayerEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public final class MatchRepository {

    private final SessionFactory sessionFactory;

    public MatchRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveFinishedMatch(int firstPlayerId, int secondPlayerId, int winnerId) {

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            session.persist(new MatchEntity(
                    session.getReference(PlayerEntity.class, firstPlayerId),
                    session.getReference(PlayerEntity.class, secondPlayerId),
                    session.getReference(PlayerEntity.class, winnerId)
            ));

            transaction.commit();
        }
    }
}
