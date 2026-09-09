package murlov.tennis_scoreboard.mapper;

import murlov.tennis_scoreboard.dto.PointResponseDto;
import murlov.tennis_scoreboard.model.UnfinishedMatch;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PointMapper {

    PointMapper INSTANCE = Mappers.getMapper(PointMapper.class);

    PointResponseDto toDto(UnfinishedMatch unfinishedMatch);
}
