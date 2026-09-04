package murlov.tennis_scoreboard.mapper;

import murlov.tennis_scoreboard.dto.MatchResponseDto;
import murlov.tennis_scoreboard.hibernate.Match;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MatchMapper {

    MatchMapper INSTANCE = Mappers.getMapper(MatchMapper.class);

    MatchResponseDto toDto(Match match);
}
