package murlov.tennis_scoreboard.storage;

import murlov.tennis_scoreboard.model.UnfinishedMatch;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


public class UnfinishedMatchesStorage {

    private final Map<UUID, UnfinishedMatch> matches;

    public UnfinishedMatchesStorage() {
        matches = new ConcurrentHashMap<>();
    }

    public void save(UnfinishedMatch match) {
        matches.put(match.getUuid(), match);
    }

    public Optional<UnfinishedMatch> get(UUID uuid) {
        return Optional.ofNullable(matches.get(uuid));
    }

    public void remove(UUID uuid) {
        matches.remove(uuid);
    }
}
