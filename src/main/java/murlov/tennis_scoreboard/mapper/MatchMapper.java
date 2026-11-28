package murlov.tennis_scoreboard.mapper;

import murlov.tennis_scoreboard.dto.FinishedMatchResponseDto;
import murlov.tennis_scoreboard.dto.UnfinishedMatchResponseDto;
import murlov.tennis_scoreboard.dto.NewMatchResponseDto;
import murlov.tennis_scoreboard.model.domain.FinishedMatch;
import murlov.tennis_scoreboard.model.domain.UnfinishedMatch;
import murlov.tennis_scoreboard.model.domain.snapshot.UnfinishedMatchSnapshot;
import murlov.tennis_scoreboard.model.entity.MatchEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface MatchMapper {

    NewMatchResponseDto toDto(UUID id);

    UnfinishedMatchResponseDto toDto(UnfinishedMatchSnapshot unfinishedMatchSnapshot);

    UnfinishedMatchSnapshot toSnapshot(UnfinishedMatch unfinishedMatch);

    @Mapping(target = "firstPlayerName", source = "firstPlayerEntity.name")
    @Mapping(target = "secondPlayerName", source = "secondPlayerEntity.name")
    @Mapping(target = "winnerName", source = "winnerEntity.name")
    FinishedMatch toDomain(MatchEntity matchEntity);

    List<FinishedMatch> toDomain(List<MatchEntity> matchEntity);

    FinishedMatchResponseDto toDto(FinishedMatch finishedMatch);

    List<FinishedMatchResponseDto> toDto(List<FinishedMatch> finishedMatches);
}
