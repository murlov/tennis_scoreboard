package murlov.tennis_scoreboard.sevlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import murlov.tennis_scoreboard.dto.MatchRequestDto;
import murlov.tennis_scoreboard.mapper.MatchMapper;
import murlov.tennis_scoreboard.service.MatchService;
import murlov.tennis_scoreboard.util.validator.MatchValidator;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/matches")
public class MatchServlet extends BaseServlet {

    private MatchService matchService;

    @Override
    public void init() throws ServletException {
        super.init();

        this.matchService =
                (MatchService) getServletContext()
                        .getAttribute("matchService");

        if (matchService == null) {
            throw new IllegalStateException(
                    "MatchService is not initialized"
            );
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        MatchRequestDto matchRequestDto = readFromRequest(
                request, MatchRequestDto.class
        );

        MatchValidator.validate(matchRequestDto);

        UUID uuid = matchService.createMatch(matchRequestDto);

        sendResponse(response, HttpServletResponse.SC_CREATED, MatchMapper.INSTANCE.toDto(uuid));
    }
}