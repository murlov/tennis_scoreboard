package murlov.tennis_scoreboard.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import murlov.tennis_scoreboard.dto.FinishedMatchResponseDto;
import murlov.tennis_scoreboard.dto.UnfinishedMatchResponseDto;
import murlov.tennis_scoreboard.dto.NewMatchRequestDto;
import murlov.tennis_scoreboard.dto.NewMatchResponseDto;
import murlov.tennis_scoreboard.mapper.MatchMapper;
import murlov.tennis_scoreboard.model.domain.FinishedMatch;
import murlov.tennis_scoreboard.model.domain.UnfinishedMatch;
import murlov.tennis_scoreboard.service.MatchService;
import murlov.tennis_scoreboard.util.validator.MatchValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class MatchController {
    private final MatchService matchService;
    private final MatchMapper matchMapper;

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
    public ResponseEntity<UnfinishedMatchResponseDto> getMatch(@PathVariable("uuid") UUID matchId) {
        UnfinishedMatch unfinishedMatch = matchService.getUnfinishedMatch(matchId);

        return ResponseEntity.ok(matchMapper.toDto(unfinishedMatch));
    }

    @GetMapping("/matches")
    public ResponseEntity<List<FinishedMatchResponseDto>> getMatches(
            @RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "playerName", required = false) String playerName
    ) {
        List<FinishedMatch> finishedMatches = matchService.getFinishedMatches(page, playerName);

        return ResponseEntity.ok(matchMapper.toDto(finishedMatches));
    }
}
