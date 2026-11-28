package murlov.tennis_scoreboard.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import murlov.tennis_scoreboard.dto.PointRequestDto;
import murlov.tennis_scoreboard.dto.PointResponseDto;
import murlov.tennis_scoreboard.mapper.PointMapper;
import murlov.tennis_scoreboard.model.domain.snapshot.UnfinishedMatchSnapshot;
import murlov.tennis_scoreboard.service.MatchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class PointController {

    private final MatchService matchService;
    private final PointMapper pointMapper;

    @PostMapping("/matches/{uuid}/point")
    public ResponseEntity<PointResponseDto> addPoint(@PathVariable("uuid") UUID matchId,
                                     @RequestBody
                                     @Valid
                                     PointRequestDto pointRequestDto) {
        UnfinishedMatchSnapshot unfinishedMatchSnapshot = matchService.addPoint(matchId, pointRequestDto);

        return ResponseEntity.ok(pointMapper.toDto(unfinishedMatchSnapshot));
    }
}
