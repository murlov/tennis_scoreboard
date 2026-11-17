package murlov.tennis_scoreboard.repository;

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
public final class MatchRepository {

    private static final int PAGE_SIZE = 5;

    private final SessionFactory sessionFactory;
    private final MatchMapper matchMapper;

    public MatchRepository(SessionFactory sessionFactory, MatchMapper matchMapper) {
        this.sessionFactory = sessionFactory;
        this.matchMapper = matchMapper;
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

    public List<FinishedMatch> getFinishedMatches(Integer page) {
        try (Session session = sessionFactory.openSession()) {
            List<MatchEntity> matchEntities = session.createQuery(
                    "FROM MatchEntity m",
                    MatchEntity.class
                    )
                    .setFirstResult((page - 1) * PAGE_SIZE)
                    .setMaxResults(PAGE_SIZE)
                    .getResultList();

            return matchMapper.toDomain(matchEntities);
        }
    }

    public List<FinishedMatch> getFinishedMatchesByName(Integer page, String playerName) {
        try (Session session = sessionFactory.openSession()) {
            List<MatchEntity> matchEntities = session.createQuery(
                            "FROM MatchEntity m " +
                                    "WHERE m.firstPlayerEntity.name = :playerName " +
                                    "OR m.secondPlayerEntity.name = :playerName",
                            MatchEntity.class
                    )
                    .setParameter("playerName", playerName)
                    .setFirstResult((page - 1) * PAGE_SIZE)
                    .setMaxResults(PAGE_SIZE)
                    .getResultList();

            return matchMapper.toDomain(matchEntities);
        }
    }
}
