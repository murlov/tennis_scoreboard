package murlov.tennis_scoreboard.controller;

import murlov.tennis_scoreboard.dto.MatchRequestDto;
import murlov.tennis_scoreboard.dto.MatchResponseDto;
import murlov.tennis_scoreboard.mapper.MatchMapper;
import murlov.tennis_scoreboard.service.MatchService;
import murlov.tennis_scoreboard.util.validator.MatchValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class MatchController {
    private final MatchService matchService;
    private final MatchMapper matchMapper;

    public MatchController(MatchService matchService, MatchMapper matchMapper) {
        this.matchService = matchService;
        this.matchMapper = matchMapper;
    }

    @PostMapping("/matches")
    public ResponseEntity<MatchResponseDto> createMatch(@RequestBody MatchRequestDto matchRequestDto) {
        MatchValidator.validate(matchRequestDto);

        UUID uuid = matchService.createMatch(matchRequestDto);

        return ResponseEntity.ok(matchMapper.toDto(uuid));
    }
}
