package murlov.tennis_scoreboard.mapper;

import murlov.tennis_scoreboard.dto.PointResponseDto;
import murlov.tennis_scoreboard.model.domain.snapshot.UnfinishedMatchSnapshot;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PointMapper {

    PointResponseDto toDto(UnfinishedMatchSnapshot unfinishedMatchSnapshot);
}
