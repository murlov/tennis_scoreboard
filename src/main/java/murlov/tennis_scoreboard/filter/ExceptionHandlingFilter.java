package murlov.tennis_scoreboard.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import murlov.tennis_scoreboard.dto.ErrorResponse;
import murlov.tennis_scoreboard.exception.MethodNotAllowedException;
import murlov.tennis_scoreboard.exception.NotFoundException;

import java.io.IOException;

@WebFilter("/*")
public class ExceptionHandlingFilter extends HttpFilter {

    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        super.init();

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
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } catch (MethodNotAllowedException e) {
            sendError(response, HttpServletResponse.SC_METHOD_NOT_ALLOWED, e.getMessage());
        } catch (NotFoundException e) {
            sendError(response, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }
    }

    private void sendError(HttpServletResponse response, int status, String message) throws IOException {
        ErrorResponse error = new ErrorResponse(message);
        response.setStatus(status);
        objectMapper.writeValue(response.getWriter(), error);
    }
}
