package murlov.tennis_scoreboard.controller;

import murlov.tennis_scoreboard.dto.PointRequestDto;
import murlov.tennis_scoreboard.dto.PointResponseDto;
import murlov.tennis_scoreboard.mapper.PointMapper;
import murlov.tennis_scoreboard.model.UnfinishedMatch;
import murlov.tennis_scoreboard.service.MatchService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class PointController {

    private final MatchService matchService;
    private final PointMapper pointMapper;

    public PointController(MatchService matchService, PointMapper pointMapper) {
        this.matchService = matchService;
        this.pointMapper = pointMapper;
    }

    @PostMapping("/matches/{uuid}/point")
    public PointResponseDto addPoint(@PathVariable("uuid") UUID matchId,
                                     @RequestBody PointRequestDto pointRequestDto) {
        UnfinishedMatch unfinishedMatch = matchService.addPoint(matchId, pointRequestDto);

        return pointMapper.toDto(unfinishedMatch);
    }
}
