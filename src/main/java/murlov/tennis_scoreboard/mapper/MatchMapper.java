package murlov.tennis_scoreboard.mapper;

import murlov.tennis_scoreboard.dto.MatchResponseDto;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface MatchMapper {

    MatchResponseDto toDto(UUID id);
}
