package murlov.tennis_scoreboard.mapper;

import murlov.tennis_scoreboard.dto.MatchResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper
public interface MatchMapper {

    MatchMapper INSTANCE = Mappers.getMapper(MatchMapper.class);

    MatchResponseDto toDto(UUID id);
}
