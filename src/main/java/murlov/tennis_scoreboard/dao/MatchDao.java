package murlov.tennis_scoreboard.dao;

import murlov.tennis_scoreboard.model.Match;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public final class MatchDao {

    private final SessionFactory sessionFactory;

    public MatchDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Match save(Match match) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            session.persist(match);

            transaction.commit();
        }

        return match;
    }
}
