package murlov.tennis_scoreboard.serlvet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import murlov.tennis_scoreboard.dto.MatchRequestDto;
import murlov.tennis_scoreboard.service.MatchService;

import java.io.IOException;

@WebServlet("/matches")
public class MatchServlet extends BaseServlet {

    private MatchService matchService;
    private ObjectMapper objectMapper;

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

        this.objectMapper =
                (ObjectMapper) getServletContext()
                        .getAttribute("objectMapper");

        if (objectMapper == null) {
            throw new IllegalStateException(
                    "ObjectMapper is not initialized"
            );
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        MatchRequestDto matchRequestDto = objectMapper
                .readValue(request.getReader(), MatchRequestDto.class);

        sendResponse(response, HttpServletResponse.SC_CREATED, matchService.createMatch(matchRequestDto));
    }
}