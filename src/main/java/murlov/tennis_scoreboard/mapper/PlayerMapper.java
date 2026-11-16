package murlov.tennis_scoreboard.mapper;

import murlov.tennis_scoreboard.model.domain.Player;
import murlov.tennis_scoreboard.model.entity.PlayerEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlayerMapper {

    Player toDomain(PlayerEntity playerEntity);
}
