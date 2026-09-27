package murlov.tennis_scoreboard.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import murlov.tennis_scoreboard.dto.ErrorResponse;
import murlov.tennis_scoreboard.exception.DuplicateException;
import murlov.tennis_scoreboard.exception.MethodNotAllowedException;
import murlov.tennis_scoreboard.exception.NotFoundException;
import murlov.tennis_scoreboard.exception.ValidationException;

import java.io.IOException;

@Slf4j
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
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException {
        try {
            chain.doFilter(request, response);
        } catch (MethodNotAllowedException e) {
            sendError(response, HttpServletResponse.SC_METHOD_NOT_ALLOWED, e.getMessage());
        } catch (NotFoundException e) {
            sendError(response, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        } catch (DuplicateException e) {
            sendError(response, HttpServletResponse.SC_CONFLICT, e.getMessage());
        } catch (ValidationException e) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error", e);
            sendError(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unexpected error");
        }
    }

    private void sendError(HttpServletResponse response, int status, String message) throws IOException {
        ErrorResponse error = new ErrorResponse(message);
        response.setStatus(status);
        objectMapper.writeValue(response.getWriter(), error);
    }
}
