package murlov.tennis_scoreboard.dao;

import murlov.tennis_scoreboard.model.Match;
import murlov.tennis_scoreboard.model.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public final class MatchDao {

    private final SessionFactory sessionFactory;

    public MatchDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveFinishedMatch(int firstPlayerId, int secondPlayerId, int winnerId) {

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            session.persist(new Match(
                    session.getReference(Player.class, firstPlayerId),
                    session.getReference(Player.class, secondPlayerId),
                    session.getReference(Player.class, winnerId)
            ));

            transaction.commit();
        }
    }
}
