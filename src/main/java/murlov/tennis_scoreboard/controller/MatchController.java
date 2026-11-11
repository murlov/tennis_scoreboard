package murlov.tennis_scoreboard.controller;

import jakarta.validation.Valid;
import murlov.tennis_scoreboard.dto.NewMatchRequestDto;
import murlov.tennis_scoreboard.dto.NewMatchResponseDto;
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
    public ResponseEntity<NewMatchResponseDto> createMatch(
            @RequestBody
            @Valid
            NewMatchRequestDto newMatchRequestDto) {
        MatchValidator.validate(newMatchRequestDto);

        UUID uuid = matchService.createMatch(newMatchRequestDto);

        return ResponseEntity.ok(matchMapper.toDto(uuid));
    }
}
