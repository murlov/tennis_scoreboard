package murlov.tennis_scoreboard.sevlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import murlov.tennis_scoreboard.exception.NotFoundException;

@WebServlet("/*")
public class NotFoundServlet extends BaseServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        throw new NotFoundException(
                "Path not found"
        );
    }
}
