package murlov.tennis_scoreboard.controller;

import jakarta.validation.Valid;
import murlov.tennis_scoreboard.dto.MatchResponseDto;
import murlov.tennis_scoreboard.dto.NewMatchRequestDto;
import murlov.tennis_scoreboard.dto.NewMatchResponseDto;
import murlov.tennis_scoreboard.mapper.MatchMapper;
import murlov.tennis_scoreboard.model.domain.UnfinishedMatch;
import murlov.tennis_scoreboard.service.MatchService;
import murlov.tennis_scoreboard.util.validator.MatchValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/matches/{uuid}")
    public ResponseEntity<MatchResponseDto> getMatch(@PathVariable("uuid") UUID matchId) {
        UnfinishedMatch unfinishedMatch = matchService.getMatch(matchId);

        return ResponseEntity.ok(matchMapper.toDto(unfinishedMatch));
    }
}
