package murlov.tennis_scoreboard.sevlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import murlov.tennis_scoreboard.dto.PointRequestDto;
import murlov.tennis_scoreboard.mapper.PointMapper;
import murlov.tennis_scoreboard.model.UnfinishedMatch;
import murlov.tennis_scoreboard.service.MatchService;
import murlov.tennis_scoreboard.util.UuidParser;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/matches/*")
public class PointServlet extends BaseServlet{

    private MatchService matchService;

    @Override
    public void init() throws ServletException {
        super.init();

        matchService =
                (MatchService) getServletContext()
                        .getAttribute("matchService");

        if (matchService == null) {
            throw new IllegalStateException(
                    "MatchService is not initialized"
            );
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        UUID matchUuid = UuidParser.parse(request);

        PointRequestDto pointRequestDto = readFromRequest(
                request, PointRequestDto.class
        );

        UnfinishedMatch unfinishedMatch = matchService.addPoint(matchUuid, pointRequestDto);

        sendResponse(response, HttpServletResponse.SC_OK, PointMapper.INSTANCE.toDto(unfinishedMatch));
    }
}
