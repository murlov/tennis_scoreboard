package murlov.tennis_scoreboard.mapper;

import murlov.tennis_scoreboard.dto.UnfinishedMatchResponseDto;
import murlov.tennis_scoreboard.dto.NewMatchResponseDto;
import murlov.tennis_scoreboard.model.domain.UnfinishedMatch;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface MatchMapper {

    NewMatchResponseDto toDto(UUID id);

    UnfinishedMatchResponseDto toDto(UnfinishedMatch unfinishedMatch);
}
